package io.github.carlosthe19916.repository;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.cdi.ContextName;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.util.CastUtils;
import org.apache.cxf.binding.soap.SoapHeader;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.message.MessageContentsList;
import org.w3c.dom.Document;
import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import pe.gob.sunat.service.StatusResponse;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@ContextName("cdi-camel-context")
public class SunatRepository extends RouteBuilder {

    private static final String URI_TEMPLATE = "cxf:${header.CamelSunatEndpoint}?serviceClass=" + pe.gob.sunat.service.BillService.class.getName() + "&defaultOperationName=${header.CamelSunatOperation}";

    @Inject
    @ConfigurationValue("io.github.carlosthe19916.defaultSunatEndpoint")
    private String defaultCamelSunatEndpoint;

    @Resource(mappedName = "java:jboss/DefaultJMSConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Override
    public void configure() throws Exception {
        JmsComponent jmsComponent = new JmsComponent();
        jmsComponent.setConnectionFactory(connectionFactory);

        getContext().addComponent("jms", jmsComponent);

        from("jms:queue:SunatQueue")
                .choice()
                    .when(header("CamelSunatEndpoint").isNull())
                        .log("CamelSunatEndpoint is not present, setting default:" + defaultCamelSunatEndpoint)
                        .setHeader("CamelSunatEndpoint").simple(defaultCamelSunatEndpoint)
                .end()
                .choice()
                    .when(body().isInstanceOf(String.class))
                        .setHeader("CamelSunatOperation").simple("getStatus")
                        .log("Checking ticket: ${body} on ruc: ${header.CamelSunatRuc}")
                        .process(exchange -> {
                            String ruc = (String) exchange.getIn().getHeader("CamelSunatRuc");
                            this.addWSSESecurityHeader(exchange, ruc);
                        })
                        .toD(URI_TEMPLATE)
                        .process(exchange -> {
                            Message message = exchange.getIn();
                            MessageContentsList messageContentsList = (MessageContentsList) message.getBody();
                            pe.gob.sunat.service.StatusResponse statusResponse = (StatusResponse) messageContentsList.get(0);
                            message.setBody(statusResponse);
                        })
                    .when(body().isInstanceOf(byte[].class))
                        .convertBodyTo(Document.class)
                        .process(exchange -> {


//                            Message message = exchange.getIn();
//
//                            String ruc = (String) message.getHeader("CamelSunatRuc");
//                            String tipoComprobante = (String) message.getHeader("CamelSunatTipoComprobante");
//
//                            String fileName = (String) message.getHeader("CamelFileName");
//                            byte[] bytes = (byte[]) message.getBody();
//                            String partyType = (String) Optional.ofNullable(message.getHeader("SunatPartyType")).orElse("");
//
//                            DataSource dataSource = new ByteArrayDataSource(bytes, "application/xml");
//                            DataHandler dataHandler = new DataHandler(dataSource);
//
//                            Object[] serviceParams = new Object[]{ruc + "-" + tipoComprobante + "-" + fileName.replaceAll(".xml", ".zip"), dataHandler, partyType};
//
//                            message.setBody(serviceParams);
                        })
//                        .marshal()
//                        .zip()
                        .toD(URI_TEMPLATE)
                    .otherwise()
                        .log("SunatQueue received invalid message")
                    .end()
        ;
    }

    public void addWSSESecurityHeader(Exchange exchange, String ruc) {
        Message message = exchange.getIn();

        String username = (String) message.getHeader("CamelSunatUsername");
        String password = (String) message.getHeader("CamelSunatPassword");

        String soapHeader = "" +
                "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\"\n" +
                "               xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">\n" +
                "    <wsse:UsernameToken wsu:Id=\"UsernameToken-50\">\n" +
                "        <wsse:Username>" + ruc + username + "</wsse:Username>\n" +
                "        <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">" + password + "</wsse:Password>\n" +
                "    </wsse:UsernameToken>\n" +
                "</wsse:Security>";

        //Add wsse security header to the exchange
        addSoapHeader(exchange, soapHeader);
    }

    public void addSoapHeader(Exchange exchange, String soapHeader) {
        List<SoapHeader> soapHeaders = CastUtils.cast((List<?>) exchange.getIn().getHeader(Header.HEADER_LIST));
        if (soapHeaders == null) {
            soapHeaders = new ArrayList<>();
        }

        SoapHeader newHeader;
        try {
            newHeader = new SoapHeader(new QName("soapHeader"), readXml(new StringReader(soapHeader)).getDocumentElement());
            newHeader.setDirection(Header.Direction.DIRECTION_OUT);

            soapHeaders.add(newHeader);

            exchange.getIn().setHeader(Header.HEADER_LIST, soapHeaders);
        } catch (Exception e) {
            //log error
        }
    }

    /**
     * This is an exact replica of DOMUtils.createXml()
     */
    private static Document readXml(Reader is) throws SAXException, IOException, ParserConfigurationException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        dbf.setValidating(false);
        dbf.setIgnoringComments(false);
        dbf.setIgnoringElementContentWhitespace(true);
        dbf.setNamespaceAware(true);
        // dbf.setCoalescing(true);
        // dbf.setExpandEntityReferences(true);

        DocumentBuilder db = null;
        db = dbf.newDocumentBuilder();
        db.setEntityResolver(new DOMUtils.NullResolver());

        // db.setErrorHandler( new MyErrorHandler());
        InputSource ips = new InputSource(is);
        return db.parse(ips);
    }

}

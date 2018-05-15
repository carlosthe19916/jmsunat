package io.github.carlosthe19916.repository;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.cdi.ContextName;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.util.CastUtils;
import org.apache.cxf.headers.Header;
import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.mail.util.ByteArrayDataSource;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPHeader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@ContextName("cdi-camel-context")
public class JmsRouter extends RouteBuilder {

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
                .setHeader("CamelSunatEndpoint").simple(defaultCamelSunatEndpoint)
                .end()
                .process((Processor) this::addWSSESecurityHeader)
                .choice()
                .when(body().isInstanceOf(String.class))
                .setHeader("CamelSunatOperation").simple("getStatus")
                .toD(URI_TEMPLATE)
                .when(body().isInstanceOf(byte[].class))
                .marshal()
                .zip()
                .process(exchange -> {
                    Message message = exchange.getIn();

                    String ruc = (String) message.getHeader("CamelSunatRuc");
                    String tipoComprobante = (String) message.getHeader("CamelSunatTipoComprobante");

                    String fileName = (String) message.getHeader("CamelFileName");
                    byte[] bytes = (byte[]) message.getBody();
                    String partyType = (String) Optional.ofNullable(message.getHeader("SunatPartyType")).orElse("");

                    DataSource dataSource = new ByteArrayDataSource(bytes, "application/xml");
                    DataHandler dataHandler = new DataHandler(dataSource);

                    Object[] serviceParams = new Object[]{ruc + "-" + tipoComprobante + "-" + fileName.replaceAll(".xml", ".zip"), dataHandler, partyType};

                    message.setBody(serviceParams);
                })
                .toD(URI_TEMPLATE)
                .otherwise()
                .log("SunatQueue received invalid message")
                .end()
        ;
    }

    public void addWSSESecurityHeader(Exchange exchange) {
        Message message = exchange.getIn();

        String username = (String) message.getHeader("CamelSunatUsername");
        String password = (String) message.getHeader("CamelSunatPassword");

        String soapHeader = "" +
                "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\"\n" +
                "               xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">\n" +
                "    <wsse:UsernameToken wsu:Id=\"UsernameToken-50\">\n" +
                "        <wsse:Username>" +
                username +
                "        </wsse:Username>\n" +
                "        <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">\n" +
                password +
                "        </wsse:Password>\n" +
                "    </wsse:UsernameToken>\n" +
                "</wsse:Security>";

        //Add wsse security header to the exchange
        addSoapHeader(exchange, soapHeader);
    }

    public void addSoapHeader(Exchange exchange, String soapHeader) {
//        List<Object> soapHeaders = CastUtils.cast((List<?>) exchange.getIn().getHeader(Header.HEADER_LIST));
//        if (soapHeaders == null) {
//            soapHeaders = new ArrayList<>();
//        }
//
//        SOAPHeader newHeader;
//        try {
//            new SoapHeader
//            newHeader = new SOAPHeader(new QName("soapHeader"), DOMUtils.readXml(new StringReader(soapHeader)).getDocumentElement());
//            newHeader.setDirection(Header.Direction.DIRECTION_OUT);
//
//            soapHeaders.add(newHeader);
//
//            exchange.getIn().setHeader(Header.HEADER_LIST, soapHeaders);
//        } catch (Exception e) {
//            //log error
//        }
    }

}

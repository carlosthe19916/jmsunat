package io.github.carlosthe19916.wildflyswarm.consumer.service;

import io.github.carlosthe19916.wildflyswarm.consumer.model.ConsumerConstants;
import io.github.carlosthe19916.wildflyswarm.consumer.utils.Utils;
import io.github.carlosthe19916.model.Constants;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.cdi.ContextName;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.util.CastUtils;
//import org.apache.cxf.binding.soap.SoapHeader;
//import org.apache.cxf.headers.Header;
//import org.apache.cxf.message.MessageContentsList;
import pe.gob.sunat.service.StatusResponse;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.xml.namespace.QName;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@ContextName("cdi-camel-context")
public class ConsumerService extends RouteBuilder {
    @Override
    public void configure() throws Exception {

    }

//    private static final String URI_TEMPLATE = "cxf:${header.CamelSunatEndpoint}?serviceClass=" +
//            pe.gob.sunat.service.BillService.class.getName() + "&defaultOperationName=${header." +
//            ConsumerConstants.WS_OPERATION + "}";

//    @Inject
//    @BillServiceCpeUrl
//    private String defaultServiceUrl;
//
//    @Resource(mappedName = "java:jboss/DefaultJMSConnectionFactory")
//    private ConnectionFactory connectionFactory;
//
//    @Override
//    public void configure() {
//        JmsComponent jmsComponent = new JmsComponent();
//        jmsComponent.setConnectionFactory(connectionFactory);
//
//        getContext().addComponent("jms", jmsComponent);
//
//        from("jms:queue:sunat-queue")
//                .process(this::addWSSESecurityHeader)
//                .choice()
//                    .when(header(Constants.OPERATION_NAME).isEqualTo(Constants.OPERATION_GET_STATUS))
//                        .setHeader(ConsumerConstants.WS_OPERATION).simple(ConsumerConstants.WS_GET_STATUS)
//                        .toD(URI_TEMPLATE)
//                        .process(exchange -> {
//                            Message message = exchange.getIn();
//                            MessageContentsList messageContentsList = (MessageContentsList) message.getBody();
//                            StatusResponse statusResponse = (StatusResponse) messageContentsList.get(0);
//                            message.setBody(statusResponse);
//                        })
////                    .when(header(Constants.OPERATION_NAME).isEqualTo(Constants.OPERATION_SEND_BILL))
////                        .setHeader(ConsumerConstants.WS_OPERATION).simple(ConsumerConstants.SUNAT_WS_SEND_BILL_OPERATION)
////                        .toD(URI_TEMPLATE)
////                    .when(header(Constants.OPERATION_NAME).isEqualTo(Constants.OPERATION_SEND_SUMMARY))
////                        .setHeader(ConsumerConstants.WS_OPERATION).simple(ConsumerConstants.SUNAT_WS_SEND_SUMMARY_OPERATION)
////                        .toD(URI_TEMPLATE)
////                    .when(header(Constants.OPERATION_NAME).isEqualTo(Constants.OPERATION_SEND_PACK))
////                        .setHeader(ConsumerConstants.WS_OPERATION).simple(ConsumerConstants.SUNAT_WS_SEND_PACK_OPERATION)
////                        .toD(URI_TEMPLATE)
////
////                    .when(body().isInstanceOf(String.class))
////                        .setHeader(ConsumerConstants.WS_OPERATION).simple(ConsumerConstants.WS_GET_STATUS)
////                        .toD(URI_TEMPLATE)
////                        .process(exchange -> {
////                            Message message = exchange.getIn();
////                            MessageContentsList messageContentsList = (MessageContentsList) message.getBody();
////                            StatusResponse statusResponse = (StatusResponse) messageContentsList.get(0);
////                            message.setBody(statusResponse);
////                        })
////                    .when(body().isInstanceOf(byte[].class))
////                        .setHeader("CamelSunatOperation").simple("sendBill")
////                        .convertBodyTo(Document.class)
////                        .choice()
////                            .when(exchange -> {
////                                Message message = exchange.getIn();
////                                Document document = (Document) message.getBody();
////                                UBLDocument ublDocument = UBLDocumentFactory.getUBLDocument(document);
////                                return ublDocument != null;
////                            })
////                                .process(exchange -> {
////                                    Message message = exchange.getIn();
////                                    Document document = (Document) message.getBody();
////                                    UBLDocument ublDocument = UBLDocumentFactory.getUBLDocument(document);
////
////                                    message.setHeader("CamelSunatRuc", ublDocument.getNumeroRucEmisor());
////                                    message.setHeader("CamelSunatTipoComprobante", ublDocument.getCodigoTipoDocumento());
////                                    message.setHeader("CamelSunatIdAsignado", ublDocument.getIDAsignado());
////
////                                    String fileName = ublDocument.getNumeroRucEmisor() + "-" +
////                                            ublDocument.getCodigoTipoDocumento() + "-" +
////                                            ublDocument.getIDAsignado() + ".xml";
////                                    message.setHeader(Exchange.FILE_NAME, fileName);
////                                })
////                                .marshal()
////                                .zipFile()
////                                .process(exchange -> {
////                                    Message message = exchange.getIn();
////
////                                    byte[] bytes = (byte[]) message.getBody();
////                                    String partyType = (String) Optional.ofNullable(message.getHeader("SunatPartyType")).orElse("");
////
////                                    DataSource dataSource = new ByteArrayDataSource(bytes, "application/xml");
////
////                                    String camelFinalName = (String) message.getHeader(Exchange.FILE_NAME);
////                                    camelFinalName = camelFinalName.replaceAll(".xml", "");
////
////                                    DataHandler dataHandler = new DataHandler(dataSource);
////                                    Object[] serviceParams = new Object[]{camelFinalName, dataHandler, partyType};
////                                    message.setBody(serviceParams);
////                                })
////                                .toD(URI_TEMPLATE)
////                        .otherwise()
////                            .log("sunat-queue received invalid message")
//                    .otherwise()
//                        .log("sunat-queue received invalid message")
//                    .end();
//    }
//
//    private void addWSSESecurityHeader(Exchange exchange) {
//        Message message = exchange.getIn();
//
//        String username = (String) message.getHeader(Constants.SUNAT_USERNAME);
//        String password = (String) message.getHeader(Constants.SUNAT_PASSWORD);
//
//        String soapHeader = "" +
//                "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
//                "<wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\"\n" +
//                "               xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">\n" +
//                "    <wsse:UsernameToken wsu:Id=\"UsernameToken-50\">\n" +
//                "        <wsse:Username>" + username + "</wsse:Username>\n" +
//                "        <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">" + password + "</wsse:Password>\n" +
//                "    </wsse:UsernameToken>\n" +
//                "</wsse:Security>";
//
//        //Add wsse security header to the exchange
//        addSoapHeader(exchange, soapHeader);
//    }
//
//    private void addSoapHeader(Exchange exchange, String soapHeader) {
//        List<SoapHeader> soapHeaders = CastUtils.cast((List<?>) exchange.getIn().getHeader(Header.HEADER_LIST));
//        if (soapHeaders == null) {
//            soapHeaders = new ArrayList<>();
//        }
//
//        SoapHeader newHeader;
//        try {
//            newHeader = new SoapHeader(new QName("soapHeader"), Utils.readXml(new StringReader(soapHeader)).getDocumentElement());
//            newHeader.setDirection(Header.Direction.DIRECTION_OUT);
//
//            soapHeaders.add(newHeader);
//
//            exchange.getIn().setHeader(Header.HEADER_LIST, soapHeaders);
//        } catch (Exception e) {
//            //log error
//        }
//    }

}

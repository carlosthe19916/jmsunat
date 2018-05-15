package io.github.carlosthe19916.repository;

import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.cdi.ContextName;
import org.apache.camel.component.jms.JmsComponent;
import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.mail.util.ByteArrayDataSource;
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

}

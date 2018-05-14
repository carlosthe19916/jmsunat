package io.github.carlosthe19916.controller;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.cdi.ContextName;
import org.apache.camel.component.jms.JmsComponent;
import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import java.text.MessageFormat;

@ApplicationScoped
@ContextName("cdi-camel-context")
public class JmsRouter extends RouteBuilder {

    private static final String URI_TEMPLATE = "cxf:${header.SunatEndpoint}?serviceClass=" + pe.gob.sunat.service.BillService.class.getName() + "&defaultOperationName=${header.SunatUrn}";

    @Inject
    @ConfigurationValue("io.github.carlosthe19916.defaultSunatEndpoint")
    private String defaultSunatEndpoint;

    @Resource(mappedName = "java:jboss/DefaultJMSConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Override
    public void configure() throws Exception {
        JmsComponent jmsComponent = new JmsComponent();
        jmsComponent.setConnectionFactory(connectionFactory);

        getContext().addComponent("jms", jmsComponent);

        from("jms:queue:SunatQueue")
                .log("SunatQueue received message")

                .process(exchange -> {
                    Message message = exchange.getIn();

                    if (message.getHeader("SunatEndpoint") == null) {
                        message.setHeader("SunatEndpoint", defaultSunatEndpoint);
                    }

                    if (message.getHeader("SunatUrn") == null) {
                        Object body = message.getBody();
                        if (body instanceof String) {
                            message.setHeader("SunatUrn", "getStatus");
                        } else if (body instanceof byte[]) {
                            message.setHeader("SunatUrn", "sendBill");
                        }
                    }
                })

                .choice()
                    .when(header("SunatUrn").isEqualTo("sendBill"))
                        .log("Sending to sendBill...")
                        .toD(URI_TEMPLATE)
                    .when(header("SunatUrn").isEqualTo("getStatus"))
                        .log("Sending to getStatus...")
                        .toD(URI_TEMPLATE)
                    .when(header("SunatUrn").isEqualTo("sendSummary"))
                        .log("Sending to sendSummary...")
                        .toD(URI_TEMPLATE)
                    .when(header("SunatUrn").isEqualTo("sendPack"))
                        .log("Sending to sendPack...")
                        .toD(URI_TEMPLATE)
                    .otherwise()
                        .log("SunatQueue received invalid message")
                .end()
        ;
    }

}

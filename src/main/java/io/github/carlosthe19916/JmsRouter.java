package io.github.carlosthe19916;

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
import java.util.Optional;

@ApplicationScoped
@ContextName("cdi-camel-context")
public class JmsRouter extends RouteBuilder {

    private static final String URI_TEMPLATE = "cxf:{0}?serviceClass={1}&serviceName=urn:{2}";

    @Inject
    @ConfigurationValue("io.github.carlosthe19916.defaultSunatEndpoint1")
    private String defaultSunatEndpoint1;

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

                    if (message.getHeader("SunatEndpoint1") == null) {
                        message.setHeader("SunatEndpoint1", defaultSunatEndpoint1);
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
                    .when(header("${header.SunatUrn}").isEqualTo("sendBill"))
                        .toD(generateCfx())
                    .when(header("${header.SunatUrn}").isEqualTo("getStatus"))
                        .toD(generateCfx())
                    .when(header("${header.SunatUrn}").isEqualTo("sendSummary"))
                        .toD(generateCfx())
                    .when(header("${header.SunatUrn}").isEqualTo("sendPack"))
                        .toD(generateCfx())
                    .otherwise()
                        .log("SunatQueue received invalid message")
                .end()
        ;
    }

    private String generateCfx() {
        return MessageFormat.format(URI_TEMPLATE, "${header.SunatEndpoint1}", pe.gob.sunat.service.BillService.class.getName(), "${header.SunatUrn}");
    }

}

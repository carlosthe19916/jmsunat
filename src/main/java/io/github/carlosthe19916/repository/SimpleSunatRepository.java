package io.github.carlosthe19916.repository;

import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.cdi.ContextName;
import org.apache.camel.component.jms.JmsComponent;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.jms.ConnectionFactory;

@ApplicationScoped
@ContextName("cdi-camel-context")
public class SimpleSunatRepository extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("jms:queue:SimpleSunatQueue")
                .process(exchange -> {
                    Message message = exchange.getIn();
                    message.getHeaders().forEach((key, value) -> System.out.println("header:" + key + " value:" + value));
                });
    }

}

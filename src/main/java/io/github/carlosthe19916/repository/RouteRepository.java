package io.github.carlosthe19916.repository;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.language.HeaderExpression;
import org.springframework.stereotype.Component;

@Component
public class RouteRepository extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("timer://foo?delay=0&repeatCount=1")
                .setBody(new HeaderExpression(Exchange.TIMER_COUNTER))
                .transform(body().prepend("Hello "))
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        System.out.println("Writing " + exchange + " to " + System.getProperty("jboss.server.data.dir"));
                    }
                })
                .to("file://{{jboss.server.data.dir}}/" + RouteRepository.class.getName() + "?fileName=fileA&doneFileName=fileA.done");
    }

}

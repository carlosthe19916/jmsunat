package io.github.carlosthe19916.controller.jsf;

import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;

@Model
public class TicketController {

    @Inject
    private JMSContext context;

    @Resource(lookup = "/jms/queue/SunatQueue")
    private Queue queue;

    @Inject
    @ConfigurationValue("io.github.carlosthe19916.defaultSunatEndpoint")
    private String endpoint;

    private String ticket;

    public void consultarTicket() {
        context.createProducer().send(queue, this.ticket);
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

}

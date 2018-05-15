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
    private ConfigController configController;

    private String ticket;

    public void consultarTicket() {
        context.createProducer().send(queue, this.ticket);
    }

    // Getters and Setters

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

}

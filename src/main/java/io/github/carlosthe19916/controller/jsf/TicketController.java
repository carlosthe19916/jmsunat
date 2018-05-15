package io.github.carlosthe19916.controller.jsf;

import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;

import javax.annotation.Resource;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.TextMessage;

@Model
public class TicketController {

    @Inject
    private JMSContext context;

    @Resource(lookup = "/jms/queue/SunatQueue")
    private Queue queue;

    @Inject
    @ConfigurationValue("io.github.carlosthe19916.defaultSunatEndpoint")
    private String endpoint;

    private String ruc = "10467793549";
    private String username = "MODDATOS";
    private String password = "MODDATOS";

    private String ticket;

    public void consultarTicket() throws JMSException {
        TextMessage textMessage = context.createTextMessage(this.ticket);
        textMessage.setStringProperty("CamelSunatEndpoint", endpoint);
        textMessage.setStringProperty("CamelSunatRuc", ruc);
        textMessage.setStringProperty("CamelSunatUsername", username);
        textMessage.setStringProperty("CamelSunatPassword", password);
        context.createProducer().send(queue, textMessage);

        FacesMessage message = new FacesMessage("Succesful", "Ticket" + ticket + " is checked.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    // Getters and Setters

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

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

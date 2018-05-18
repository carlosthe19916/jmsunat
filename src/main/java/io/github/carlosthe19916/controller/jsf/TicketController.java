package io.github.carlosthe19916.controller.jsf;

import io.github.carlosthe19916.model.SendConfig;
import io.github.carlosthe19916.service.SunatService;
import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.jms.JMSException;

@Model
public class TicketController {

    @Inject
    private SunatService sunatService;

    @Inject
    @ConfigurationValue("io.github.carlosthe19916.defaultSunatEndpoint")
    private String endpoint;

    private String ruc = "10467793549";
    private String username = "MODDATOS";
    private String password = "MODDATOS";

    private String ticket;

    public void consultarTicket() throws JMSException {
        SendConfig sendConfig = new SendConfig.Builder()
                .endpoint(endpoint)
                .username(username)
                .password(password)
                .build();
        sunatService.checkTicket(sendConfig, ruc, ticket);

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

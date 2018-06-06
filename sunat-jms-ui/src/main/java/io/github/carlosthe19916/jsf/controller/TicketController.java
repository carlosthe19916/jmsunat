package io.github.carlosthe19916.jsf.controller;

import io.github.carlosthe19916.config.BillServiceCpeUrl;
import io.github.carlosthe19916.model.SendConfig;
import service.SunatService;

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
    @BillServiceCpeUrl
    private String serviceUrl;
    private String username;
    private String password;

    private String ruc;
    private String ticket;

    public void consultarTicket() throws JMSException {
        SendConfig sendConfig = new SendConfig.Builder()
                .endpoint(serviceUrl)
                .username(username)
                .password(password)
                .build();

        sunatService.checkTicket(sendConfig, ruc, ticket);

        FacesMessage message = new FacesMessage("Succesful", "Ticket was sended");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    // Getters and Setters

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
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

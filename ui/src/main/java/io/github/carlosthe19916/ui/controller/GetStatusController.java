package io.github.carlosthe19916.ui.controller;

import io.github.carlosthe19916.sender.model.SenderConfig;
import io.github.carlosthe19916.sender.service.BillService;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.jms.JMSException;

@Model
public class GetStatusController {

    @Inject
    private BillService billService;

    private String serviceUrl;
    private String username;
    private String password;

    private String ticket;

    public void consultarTicket() throws JMSException {
        SenderConfig sendConfig = new SenderConfig.Builder()
                .endpoint(serviceUrl)
                .username(username)
                .password(password)
                .build();

        billService.getStatus(sendConfig, ticket);

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

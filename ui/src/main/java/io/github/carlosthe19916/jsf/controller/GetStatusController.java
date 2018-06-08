package io.github.carlosthe19916.jsf.controller;

import io.github.carlosthe19916.config.BillServiceCpeUrl;
import io.github.carlosthe19916.jms.sender.model.SendConfig;
import io.github.carlosthe19916.jms.sender.service.BillService;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.jms.JMSException;

@Model
public class GetStatusController {

    public String say() {
        return "Hello from JSF";
    }

    @Inject
    private BillService billService;

    @Inject
    @BillServiceCpeUrl
    private String serviceUrl;
    private String username;
    private String password;

    private String ticket;

    public String consultarTicket() {
        SendConfig sendConfig = new SendConfig.Builder()
                .endpoint(serviceUrl)
                .username(username)
                .password(password)
                .build();

//        try {
//            billService.getStatus(sendConfig, ticket);
//        } catch (JMSException e) {
//            e.printStackTrace();
//        }

        FacesMessage message = new FacesMessage("Succesful", "Ticket was sended");
        FacesContext.getCurrentInstance().addMessage(null, message);
        return "CARLOS ";
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

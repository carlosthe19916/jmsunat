package io.github.carlosthe19916.sunatjms.producer.client.controller;

import io.github.carlosthe19916.sunatjms.producer.model.JMSSenderConfig;
import io.github.carlosthe19916.sunatjms.producer.service.JMSBillService;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.jms.JMSException;

@Model
public class GetStatusController {

    @Inject
    private JMSBillService jmsBillService;

    private String url;
    private String username;
    private String password;
    private String ticket;

    public void consultarTicket() throws JMSException {
        JMSSenderConfig config = new JMSSenderConfig.Builder()
                .endpoint(url)
                .username(username)
                .password(password)
                .build();
        jmsBillService.getStatus(config, ticket);

        FacesMessage message = new FacesMessage("Successful", "Ticket was sent");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    // Getters and Setters

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

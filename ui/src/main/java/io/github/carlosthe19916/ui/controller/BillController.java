package io.github.carlosthe19916.ui.controller;

import io.github.carlosthe19916.sender.model.BillBean;
import io.github.carlosthe19916.sender.model.SenderConfig;
import io.github.carlosthe19916.sender.service.BillService;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.jms.JMSException;

@Model
public class BillController {

    @Inject
    private BillService billService;

    private String serviceUrl;

    private String username;
    private String password;

    public void upload(FileUploadEvent fileUploadEvent) throws JMSException {
        UploadedFile uploadedFile = fileUploadEvent.getFile();

        byte[] bytes = uploadedFile.getContents();
        String fileName = uploadedFile.getFileName();

        SenderConfig sendConfig = new SenderConfig.Builder()
                .endpoint(serviceUrl)
                .username(username)
                .password(password)
                .build();
        BillBean bean = new BillBean.Builder()
                .fileName(fileName)
                .contentFile(bytes)
                .build();
        billService.sendBill(sendConfig, bean);

        FacesMessage message = new FacesMessage("Succesful", "File was sended");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    // Getter and Setters

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

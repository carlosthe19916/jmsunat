package io.github.carlosthe19916.sender.client.controller;

import io.github.carlosthe19916.sender.model.JMSBillMessageBean;
import io.github.carlosthe19916.sender.model.JMSSenderConfig;
import io.github.carlosthe19916.sender.service.JMSBillService;
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
    private JMSBillService jmsBillService;

    private String url;
    private String username;
    private String password;

    public void upload(FileUploadEvent fileUploadEvent) throws JMSException {
        UploadedFile uploadedFile = fileUploadEvent.getFile();

        byte[] bytes = uploadedFile.getContents();
        String fileName = uploadedFile.getFileName();

        JMSSenderConfig sendConfig = new JMSSenderConfig.Builder()
                .endpoint(url)
                .username(username)
                .password(password)
                .build();

        JMSBillMessageBean bean = new JMSBillMessageBean.Builder()
                .fileName(fileName)
                .contentFile(bytes)
                .build();

        jmsBillService.sendBill(sendConfig, bean);

        FacesMessage message = new FacesMessage("Successful", "File was sent");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    // Getter and Setters

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

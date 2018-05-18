package io.github.carlosthe19916.controller.jsf;

import io.github.carlosthe19916.model.SendConfig;
import io.github.carlosthe19916.service.SunatService;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.jms.JMSException;

@Model
public class BillController {

    @Inject
    private SunatService sunatService;

    @Inject
    @ConfigurationValue("io.github.carlosthe19916.defaultSunatEndpoint")
    private String endpoint;

    private String username = "MODDATOS";
    private String password = "MODDATOS";

    public void upload(FileUploadEvent fileUploadEvent) throws JMSException {
        UploadedFile uploadedFile = fileUploadEvent.getFile();

        byte[] bytes = uploadedFile.getContents();
        String fileName = uploadedFile.getFileName();

        SendConfig sendConfig = new SendConfig.Builder()
                .endpoint(endpoint)
                .username(username)
                .password(password)
                .build();
        sunatService.sendFile(sendConfig, bytes);

        FacesMessage message = new FacesMessage("Succesful", fileName + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    // Getter and Setters

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
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

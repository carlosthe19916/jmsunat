package io.github.carlosthe19916.controller.jsf;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;

import javax.annotation.Resource;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.jms.*;

@Model
public class BillController {

    @Inject
    private JMSContext context;

    @Resource(lookup = "/jms/queue/SunatQueue")
    private Queue queue;

    @Inject
    @ConfigurationValue("io.github.carlosthe19916.defaultSunatEndpoint")
    private String endpoint;

    private String username = "MODDATOS";
    private String password = "MODDATOS";

    public void upload(FileUploadEvent fileUploadEvent) throws JMSException {
        UploadedFile uploadedFile = fileUploadEvent.getFile();

        byte[] bytes = uploadedFile.getContents();
        String fileName = uploadedFile.getFileName();

        BytesMessage bytesMessage = context.createBytesMessage();
        bytesMessage.writeBytes(bytes);

        bytesMessage.setStringProperty("CamelFileName", fileName);
        bytesMessage.setStringProperty("CamelSunatEndpoint", endpoint);
        bytesMessage.setStringProperty("CamelSunatUsername", username);
        bytesMessage.setStringProperty("CamelSunatPassword", password);

        context.createProducer().send(queue, bytesMessage);

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

package io.github.carlosthe19916.controller.jsf;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

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
    private ConfigController configController;

    private String tipoComprobante;

    public void upload(FileUploadEvent fileUploadEvent) throws JMSException {
        UploadedFile uploadedFile = fileUploadEvent.getFile();

        byte[] bytes = uploadedFile.getContents();
        String fileName = uploadedFile.getFileName();

        BytesMessage bytesMessage = context.createBytesMessage();
        bytesMessage.writeBytes(bytes);

        bytesMessage.setStringProperty("CamelFileName", fileName);
        bytesMessage.setStringProperty("CamelSunatRuc", configController.getRuc());
        bytesMessage.setStringProperty("CamelSunatEndpoint", configController.getDefaultEndpoint());
        bytesMessage.setStringProperty("CamelSunatTipoComprobante", tipoComprobante);

        context.createProducer().send(queue, bytesMessage);

        FacesMessage message = new FacesMessage("Succesful", fileName + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    // Getter and Setters

    public String getTipoComprobante() {
        return tipoComprobante;
    }

    public void setTipoComprobante(String tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }
}

package io.github.carlosthe19916.service;

import io.github.carlosthe19916.model.SendConfig;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.*;

@ApplicationScoped
public class SunatServiceImpl implements SunatService {

    @Inject
    private JMSContext context;

    @Resource(lookup = "/jms/queue/SunatQueue")
    private Queue queue;

    @Override
    public void sendFile(SendConfig sendConfig, byte[] bytes) throws JMSException {
        BytesMessage bytesMessage = context.createBytesMessage();
        bytesMessage.writeBytes(bytes);

        bytesMessage.setStringProperty("CamelSunatEndpoint", sendConfig.getEndpoint());
        bytesMessage.setStringProperty("CamelSunatUsername", sendConfig.getUsername());
        bytesMessage.setStringProperty("CamelSunatPassword", sendConfig.getPassword());

        context.createProducer().send(queue, bytesMessage);
    }

    @Override
    public void checkTicket(SendConfig sendConfig, String ruc, String ticket) throws JMSException {
        TextMessage textMessage = context.createTextMessage(ticket);

        textMessage.setStringProperty("CamelSunatRuc", ruc);
        textMessage.setStringProperty("CamelSunatEndpoint", sendConfig.getEndpoint());
        textMessage.setStringProperty("CamelSunatUsername", sendConfig.getUsername());
        textMessage.setStringProperty("CamelSunatPassword", sendConfig.getPassword());

        context.createProducer().send(queue, textMessage);
    }

}

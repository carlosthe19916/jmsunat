package io.github.carlosthe19916.jms.sender.service;

import io.github.carlosthe19916.config.QueueName;
import io.github.carlosthe19916.model.SendConfig;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@ApplicationScoped
public class SunatServiceImpl implements SunatService {

    @Inject
    @QueueName
    private String queueName;

    @Inject
    private JMSContext context;

    private Queue queue;

    @PostConstruct
    private void init() throws NamingException {
        InitialContext initialContext = new InitialContext();
        queue = (Queue) initialContext.lookup(queueName);
    }

    @Override
    public void sendFile(SendConfig sendConfig, byte[] bytes) throws JMSException {
        BytesMessage bytesMessage = context.createBytesMessage();
        bytesMessage.writeBytes(bytes);

        bytesMessage.setStringProperty("CamelSunatEndpoint", sendConfig.getEndpoint());
        bytesMessage.setStringProperty("CamelSunatUsername", sendConfig.getUsername());
        bytesMessage.setStringProperty("CamelSunatPassword", sendConfig.getPassword());

        JMSProducer producer = context.createProducer();
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        producer.setTimeToLive(0);

//        TemporaryQueue temporaryQueue = context.createTemporaryQueue();
//        producer.setJMSReplyTo(temporaryQueue);

        producer.send(queue, bytesMessage);
    }

    @Override
    public void checkTicket(SendConfig sendConfig, String ruc, String ticket) throws JMSException {
        TextMessage textMessage = context.createTextMessage(ticket);

        textMessage.setStringProperty("CamelSunatRuc", ruc);
        textMessage.setStringProperty("CamelSunatEndpoint", sendConfig.getEndpoint());
        textMessage.setStringProperty("CamelSunatUsername", sendConfig.getUsername());
        textMessage.setStringProperty("CamelSunatPassword", sendConfig.getPassword());

        JMSProducer producer = context.createProducer();
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        producer.setTimeToLive(0);

//        TemporaryQueue temporaryQueue = context.createTemporaryQueue();
//        producer.setJMSReplyTo(temporaryQueue);

        producer.send(queue, textMessage);
    }

}

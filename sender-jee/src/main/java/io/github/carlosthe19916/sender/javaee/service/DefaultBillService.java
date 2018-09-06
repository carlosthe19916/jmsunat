package io.github.carlosthe19916.sender.javaee.service;

import io.github.carlosthe19916.sender.javaee.qualifiers.JMSunatContext;
import io.github.carlosthe19916.sender.javaee.qualifiers.JMSunatQueue;
import io.github.carlosthe19916.sender.model.JMSBillMessageBean;
import io.github.carlosthe19916.sender.model.JMSSenderConfig;
import io.github.carlosthe19916.sender.service.JMSBillService;
import io.github.carlosthe19916.util.SenderConstants;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.*;

@ApplicationScoped
public class DefaultBillService implements JMSBillService {

    @Inject
    @JMSunatContext
    private JMSContext context;

    @Inject
    @JMSunatQueue
    private Queue queue;

    @Override
    public void sendBill(JMSSenderConfig config, JMSBillMessageBean bean) throws JMSException {
        sendFile(config, bean, SenderConstants.SEND_BILL);
    }

    @Override
    public void getStatus(JMSSenderConfig config, String ticket) throws JMSException {
        TextMessage message = context.createTextMessage();
        message.setText(ticket);

        message.setStringProperty(SenderConstants.WS_URL, config.getEndpoint());
        message.setStringProperty(SenderConstants.WS_OPERATION, SenderConstants.GET_STATUS);

        message.setStringProperty(SenderConstants.SUNAT_USERNAME, config.getUsername());
        message.setStringProperty(SenderConstants.SUNAT_PASSWORD, config.getPassword());

        JMSProducer producer = context.createProducer();
        producer.send(queue, message);
    }

    @Override
    public void sendSummary(JMSSenderConfig config, JMSBillMessageBean bean) throws JMSException {
        sendFile(config, bean, SenderConstants.SEND_SUMMARY);
    }

    @Override
    public void sendPack(JMSSenderConfig config, JMSBillMessageBean bean) throws JMSException {
        sendFile(config, bean, SenderConstants.SEND_PACK);
    }

    private void sendFile(JMSSenderConfig config, JMSBillMessageBean bean, String operation) throws JMSException {
        BytesMessage message = context.createBytesMessage();
        message.writeBytes(bean.getContentFile());

        message.setStringProperty(SenderConstants.WS_URL, config.getEndpoint());
        message.setStringProperty(SenderConstants.WS_OPERATION, operation);

        message.setStringProperty(SenderConstants.SUNAT_USERNAME, config.getUsername());
        message.setStringProperty(SenderConstants.SUNAT_PASSWORD, config.getPassword());

        message.setStringProperty(SenderConstants.FILE_NAME, bean.getFileName());
        message.setStringProperty(SenderConstants.PARTY_TYPE, bean.getPartyType());

        JMSProducer producer = context.createProducer();
        producer.send(queue, message);
    }
}

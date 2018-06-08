package io.github.carlosthe19916.sender.service;

import io.github.carlosthe19916.sender.config.JMSSenderContext;
import io.github.carlosthe19916.sender.config.SunatSenderQueue;
import io.github.carlosthe19916.sender.model.BillBean;
import io.github.carlosthe19916.sender.model.SenderConfig;
import io.github.carlosthe19916.model.Constants;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.*;

@ApplicationScoped
public class DefaultBillService implements BillService {

    @Inject
    @JMSSenderContext
    private JMSContext context;

    @Inject
    @SunatSenderQueue
    private Queue queue;

    @Override
    public void sendBill(SenderConfig config, BillBean bean) throws JMSException {
        sendFile(config, bean, Constants.OPERATION_SEND_BILL);
    }

    @Override
    public void getStatus(SenderConfig config, String ticket) throws JMSException {
        TextMessage message = context.createTextMessage();
        message.setText(ticket);

        message.setStringProperty(Constants.SUNAT_JMS_SERVICE_URL, config.getEndpoint());
        message.setStringProperty(Constants.SUNAT_USERNAME, config.getUsername());
        message.setStringProperty(Constants.SUNAT_PASSWORD, config.getPassword());

        message.setStringProperty(Constants.OPERATION_NAME, Constants.OPERATION_GET_STATUS);

        JMSProducer producer = context.createProducer();
        producer.send(queue, message);
    }

    @Override
    public void sendSummary(SenderConfig config, BillBean bean) throws JMSException {
        sendFile(config, bean, Constants.OPERATION_SEND_SUMMARY);
    }

    @Override
    public void sendPack(SenderConfig config, BillBean bean) throws JMSException {
        sendFile(config, bean, Constants.OPERATION_SEND_PACK);
    }

    private void sendFile(SenderConfig config, BillBean bean, String operation) throws JMSException {
        BytesMessage message = context.createBytesMessage();
        message.writeBytes(bean.getContentFile());

        message.setStringProperty(Constants.SUNAT_JMS_SERVICE_URL, config.getEndpoint());
        message.setStringProperty(Constants.SUNAT_USERNAME, config.getUsername());
        message.setStringProperty(Constants.SUNAT_PASSWORD, config.getPassword());

        message.setStringProperty(Constants.FILE_NAME, bean.getFileName());
        message.setStringProperty(Constants.PARTY_TYPE, bean.getPartyType());

        message.setStringProperty(Constants.OPERATION_NAME, operation);

        JMSProducer producer = context.createProducer();
        producer.send(queue, message);
    }
}

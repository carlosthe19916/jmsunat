package io.github.carlosthe19916.wildflyswarm.sender;

import io.github.carlosthe19916.qualifiers.JMSunatContext;
import io.github.carlosthe19916.qualifiers.JMSunatQueue;
import io.github.carlosthe19916.sender.model.BillBean;
import io.github.carlosthe19916.sender.model.SenderConfig;
import io.github.carlosthe19916.sender.model.SenderConstants;
import io.github.carlosthe19916.sender.service.BillService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.*;

@ApplicationScoped
public class DefaultBillService implements BillService {

    @Inject
    @JMSunatContext
    private JMSContext context;

    @Inject
    @JMSunatQueue
    private Queue queue;

    @Override
    public void sendBill(SenderConfig config, BillBean bean) throws JMSException {
        sendFile(config, bean, SenderConstants.SEND_BILL);
    }

    @Override
    public void getStatus(SenderConfig config, String ticket) throws JMSException {
        TextMessage message = context.createTextMessage();
        message.setText(ticket);

        message.setStringProperty(SenderConstants.DESTINY, config.getEndpoint());
        message.setStringProperty(SenderConstants.DESTINY_OPERATION, SenderConstants.GET_STATUS);

        message.setStringProperty(SenderConstants.SENDER_USERNAME, config.getUsername());
        message.setStringProperty(SenderConstants.SENDER_PASSWORD, config.getPassword());

        JMSProducer producer = context.createProducer();
        producer.send(queue, message);
    }

    @Override
    public void sendSummary(SenderConfig config, BillBean bean) throws JMSException {
        sendFile(config, bean, SenderConstants.SEND_SUMMARY);
    }

    @Override
    public void sendPack(SenderConfig config, BillBean bean) throws JMSException {
        sendFile(config, bean, SenderConstants.SEND_PACK);
    }

    private void sendFile(SenderConfig config, BillBean bean, String operation) throws JMSException {
        BytesMessage message = context.createBytesMessage();
        message.writeBytes(bean.getContentFile());

        message.setStringProperty(SenderConstants.DESTINY, config.getEndpoint());
        message.setStringProperty(SenderConstants.DESTINY_OPERATION, operation);

        message.setStringProperty(SenderConstants.SENDER_USERNAME, config.getUsername());
        message.setStringProperty(SenderConstants.SENDER_PASSWORD, config.getPassword());

        message.setStringProperty(SenderConstants.FILE_NAME, bean.getFileName());
        message.setStringProperty(SenderConstants.PARTY_TYPE, bean.getPartyType());

        JMSProducer producer = context.createProducer();
        producer.send(queue, message);
    }
}

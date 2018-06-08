package io.github.carlosthe19916.jms.sender.service;

import io.github.carlosthe19916.jms.sender.model.BillBean;
import io.github.carlosthe19916.jms.sender.model.SendConfig;
import io.github.carlosthe19916.model.Constants;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.*;

@ApplicationScoped
public class BillServiceImpl implements BillService {

    @Inject
    @JMSConnectionFactory("java:/jms/remote-mq")
    private JMSContext context;

    @Resource(mappedName = "jms/queue/sunat-queue")
    private Queue queue;

    @Override
    public void sendBill(SendConfig config, BillBean bean) throws JMSException {
        sendFile(config, bean, Constants.OPERATION_SEND_BILL);
    }

    @Override
    public void getStatus(SendConfig config, String ticket) throws JMSException {
        TextMessage message = context.createTextMessage();
        message.setText(ticket);

        message.setStringProperty(Constants.SUNAT_JMS_SERVICE_URL, config.getEndpoint());
        message.setStringProperty(Constants.SUNAT_USERNAME, config.getUsername());
        message.setStringProperty(Constants.SUNAT_PASSWORD, config.getPassword());

        message.setStringProperty(Constants.OPERATION, Constants.OPERATION_GET_STATUS);

        JMSProducer producer = context.createProducer();
        producer.send(queue, message);
    }

    @Override
    public void sendSummary(SendConfig config, BillBean bean) throws JMSException {
        sendFile(config, bean, Constants.OPERATION_SEND_SUMMARY);
    }

    @Override
    public void sendPack(SendConfig config, BillBean bean) throws JMSException {
        sendFile(config, bean, Constants.OPERATION_SEND_PACK);
    }

    private void sendFile(SendConfig config, BillBean bean, String operation) throws JMSException {
        BytesMessage message = context.createBytesMessage();
        message.writeBytes(bean.getContentFile());

        message.setStringProperty(Constants.SUNAT_JMS_SERVICE_URL, config.getEndpoint());
        message.setStringProperty(Constants.SUNAT_USERNAME, config.getUsername());
        message.setStringProperty(Constants.SUNAT_PASSWORD, config.getPassword());

        message.setStringProperty(Constants.FILE_NAME, bean.getFileName());
        message.setStringProperty(Constants.PARTY_TYPE, bean.getPartyType());

        message.setStringProperty(Constants.OPERATION, operation);

        JMSProducer producer = context.createProducer();
        producer.send(queue, message);
    }
}

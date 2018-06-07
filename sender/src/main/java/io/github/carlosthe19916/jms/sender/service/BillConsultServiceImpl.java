package io.github.carlosthe19916.jms.sender.service;

import io.github.carlosthe19916.jms.sender.model.BillConsultBean;
import io.github.carlosthe19916.jms.sender.model.SendConfig;
import io.github.carlosthe19916.model.Constants;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.*;

@ApplicationScoped
public class BillConsultServiceImpl implements BillConsultService {

    @Inject
    @JMSConnectionFactory("java:/jms/remote-mq")
    private JMSContext context;

    @Resource(mappedName = "jms/queue/sunat-queue")
    private Queue queue;

    @Override
    public void getStatus(SendConfig config, BillConsultBean bean) throws JMSException {
        consult(config, bean);
    }

    @Override
    public void getStatusCdr(SendConfig config, BillConsultBean bean) throws JMSException {
        consult(config, bean);
    }

    private void consult(SendConfig config, BillConsultBean bean) throws JMSException {
        MapMessage message = context.createMapMessage();
        message.setString(Constants.RUC_COMPROBANTE, bean.getRucComprobante());
        message.setString(Constants.TIPO_COMPROBANTE, bean.getTipoComprobante());
        message.setString(Constants.SERIE_COMPROBANTE, bean.getSerieComprobante());
        message.setInt(Constants.NUMERO_COMPROBANTE, bean.getNumeroComprobante());

        message.setStringProperty(Constants.SUNAT_JMS_SERVICE_URL, config.getEndpoint());
        message.setStringProperty(Constants.SUNAT_USERNAME, config.getUsername());
        message.setStringProperty(Constants.SUNAT_PASSWORD, config.getPassword());

        JMSProducer producer = context.createProducer();
        producer.send(queue, message);
    }

}

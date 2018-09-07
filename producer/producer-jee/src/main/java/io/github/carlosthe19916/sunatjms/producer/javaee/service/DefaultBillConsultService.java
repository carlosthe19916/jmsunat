package io.github.carlosthe19916.sunatjms.producer.javaee.service;

import io.github.carlosthe19916.sunatjms.producer.model.JMSBillConsultMessageBean;
import io.github.carlosthe19916.sunatjms.producer.model.JMSSenderConfig;
import io.github.carlosthe19916.sunatjms.producer.service.JMSBillConsultService;
import io.github.carlosthe19916.util.SenderConstants;

import javax.enterprise.context.ApplicationScoped;
import javax.jms.*;

@ApplicationScoped
public class DefaultBillConsultService implements JMSBillConsultService {

//    @Inject
//    @JMSunatContext
//    private JMSContext context;
//
//    @Inject
//    @JMSunatQueue
//    private Queue queue;

    @Override
    public void getStatus(JMSSenderConfig config, JMSBillConsultMessageBean bean) throws JMSException {
        consult(config, bean, SenderConstants.GET_STATUS);
    }

    @Override
    public void getStatusCdr(JMSSenderConfig config, JMSBillConsultMessageBean bean) throws JMSException {
        consult(config, bean, SenderConstants.GET_STATUS_CDR);
    }

    private void consult(JMSSenderConfig config, JMSBillConsultMessageBean bean, String operation) throws JMSException {
//        MapMessage message = context.createMapMessage();
//        message.setString(SenderConstants.RUC_COMPROBANTE, bean.getRucComprobante());
//        message.setString(SenderConstants.TIPO_COMPROBANTE, bean.getTipoComprobante());
//        message.setString(SenderConstants.SERIE_COMPROBANTE, bean.getSerieComprobante());
//        message.setInt(SenderConstants.NUMERO_COMPROBANTE, bean.getNumeroComprobante());
//
//        message.setStringProperty(SenderConstants.WS_URL, config.getEndpoint());
//        message.setStringProperty(SenderConstants.WS_OPERATION, operation);
//
//        message.setStringProperty(SenderConstants.SUNAT_USERNAME, config.getUsername());
//        message.setStringProperty(SenderConstants.SUNAT_PASSWORD, config.getPassword());
//
//        JMSProducer producer = context.createProducer();
//        producer.send(queue, message);
    }

}

package io.github.carlosthe19916.sunatjms.producer.javaee.service;

import io.github.carlosthe19916.sunatjms.producer.model.JMSBillValidMessageBean;
import io.github.carlosthe19916.sunatjms.producer.model.JMSSenderConfig;
import io.github.carlosthe19916.sunatjms.producer.service.JMSBillValidService;

import javax.enterprise.context.ApplicationScoped;
import javax.jms.*;

@ApplicationScoped
public class DefaultBillValidService implements JMSBillValidService {

//    @Inject
//    @JMSunatContext
//    private JMSContext context;
//
//    @Inject
//    @JMSunatQueue
//    private Queue queue;

    @Override
    public void validaCDPcriterios(JMSSenderConfig config, JMSBillValidMessageBean bean) throws JMSException {
//        MapMessage message = context.createMapMessage();
//        message.setString(SenderConstants.RUC_COMPROBANTE, bean.getRucEmisor());
//        message.setString(SenderConstants.TIPO_COMPROBANTE, bean.getTipoCDP());
//        message.setString(SenderConstants.SERIE_COMPROBANTE, bean.getSerieCDP());
//        message.setString(SenderConstants.NUMERO_COMPROBANTE, bean.getNumeroCDP());
//        message.setString(SenderConstants.TIPO_DOCUMENTO_RECEPTOR, bean.getTipoDocIdReceptor());
//        message.setString(SenderConstants.NUMERO_DOCUMENTO_RECEPTOR, bean.getNumeroDocIdReceptor());
//        message.setString(SenderConstants.FECHA_EMISION, bean.getFechaEmision());
//        message.setDouble(SenderConstants.IMPORTE_TOTAL, bean.getImporteTotal());
//        message.setString(SenderConstants.NUMERO_AUTORIZACION, bean.getNroAutorizacion());
//
//        message.setStringProperty(SenderConstants.WS_URL, config.getEndpoint());
//        message.setStringProperty(SenderConstants.WS_OPERATION, SenderConstants.GET_STATUS_CDR);
//
//        message.setStringProperty(SenderConstants.SUNAT_USERNAME, config.getUsername());
//        message.setStringProperty(SenderConstants.SUNAT_PASSWORD, config.getPassword());
//
//        JMSProducer producer = context.createProducer();
//        producer.send(queue, message);
    }

}

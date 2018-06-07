package io.github.carlosthe19916.jms.sender.service;

import io.github.carlosthe19916.jms.sender.model.BillValidBean;
import io.github.carlosthe19916.jms.sender.model.SendConfig;
import io.github.carlosthe19916.model.Constants;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.*;

@ApplicationScoped
public class BillValidServiceImpl implements BillValidService {

    @Inject
    @JMSConnectionFactory("java:/jms/remote-mq")
    private JMSContext context;

    @Resource(mappedName = "jms/queue/sunat-queue")
    private Queue queue;

    @Override
    public void validaCDPcriterios(SendConfig config, BillValidBean bean) throws JMSException {
        MapMessage message = context.createMapMessage();
        message.setString(Constants.RUC_COMPROBANTE, bean.getRucEmisor());
        message.setString(Constants.TIPO_COMPROBANTE, bean.getTipoCDP());
        message.setString(Constants.SERIE_COMPROBANTE, bean.getSerieCDP());
        message.setString(Constants.NUMERO_COMPROBANTE, bean.getNumeroCDP());
        message.setString(Constants.TIPO_DOCUMENTO_RECEPTOR, bean.getTipoDocIdReceptor());
        message.setString(Constants.NUMERO_DOCUMENTO_RECEPTOR, bean.getNumeroDocIdReceptor());
        message.setString(Constants.FECHA_EMISION, bean.getFechaEmision());
        message.setDouble(Constants.IMPORTE_TOTAL, bean.getImporteTotal());
        message.setString(Constants.NUMERO_AUTORIZACION, bean.getNroAutorizacion());
        message.setStringProperty(Constants.SUNAT_JMS_SERVICE_URL, config.getEndpoint());
        message.setStringProperty(Constants.SUNAT_USERNAME, config.getUsername());
        message.setStringProperty(Constants.SUNAT_PASSWORD, config.getPassword());

        JMSProducer producer = context.createProducer();
        producer.send(queue, message);
    }

}

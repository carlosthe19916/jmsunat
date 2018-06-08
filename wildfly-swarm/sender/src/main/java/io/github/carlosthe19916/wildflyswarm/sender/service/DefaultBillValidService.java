package io.github.carlosthe19916.wildflyswarm.sender.service;

import io.github.carlosthe19916.wildflyswarm.sender.qualifiers.JMSunatContext;
import io.github.carlosthe19916.wildflyswarm.sender.qualifiers.JMSunatQueue;
import io.github.carlosthe19916.sender.model.BillValidBean;
import io.github.carlosthe19916.sender.model.SenderConfig;
import io.github.carlosthe19916.sender.model.SenderConstants;
import io.github.carlosthe19916.sender.service.BillValidService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.*;

@ApplicationScoped
public class DefaultBillValidService implements BillValidService {

    @Inject
    @JMSunatContext
    private JMSContext context;

    @Inject
    @JMSunatQueue
    private Queue queue;

    @Override
    public void validaCDPcriterios(SenderConfig config, BillValidBean bean) throws JMSException {
        MapMessage message = context.createMapMessage();
        message.setString(SenderConstants.RUC_COMPROBANTE, bean.getRucEmisor());
        message.setString(SenderConstants.TIPO_COMPROBANTE, bean.getTipoCDP());
        message.setString(SenderConstants.SERIE_COMPROBANTE, bean.getSerieCDP());
        message.setString(SenderConstants.NUMERO_COMPROBANTE, bean.getNumeroCDP());
        message.setString(SenderConstants.TIPO_DOCUMENTO_RECEPTOR, bean.getTipoDocIdReceptor());
        message.setString(SenderConstants.NUMERO_DOCUMENTO_RECEPTOR, bean.getNumeroDocIdReceptor());
        message.setString(SenderConstants.FECHA_EMISION, bean.getFechaEmision());
        message.setDouble(SenderConstants.IMPORTE_TOTAL, bean.getImporteTotal());
        message.setString(SenderConstants.NUMERO_AUTORIZACION, bean.getNroAutorizacion());

        message.setStringProperty(SenderConstants.DESTINY, config.getEndpoint());
        message.setStringProperty(SenderConstants.DESTINY_OPERATION, SenderConstants.GET_STATUS_CDR);

        message.setStringProperty(SenderConstants.SENDER_USERNAME, config.getUsername());
        message.setStringProperty(SenderConstants.SENDER_PASSWORD, config.getPassword());

        JMSProducer producer = context.createProducer();
        producer.send(queue, message);
    }

}

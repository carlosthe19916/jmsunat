package io.github.carlosthe19916.sunatjms.producer.service;

import io.github.carlosthe19916.sunatjms.producer.model.JMSBillConsultMessageBean;
import io.github.carlosthe19916.sunatjms.producer.model.JMSSenderConfig;

import javax.jms.JMSException;

public interface JMSBillConsultService {

    void getStatus(JMSSenderConfig config, JMSBillConsultMessageBean bean) throws JMSException;

    void getStatusCdr(JMSSenderConfig config, JMSBillConsultMessageBean bean) throws JMSException;

}

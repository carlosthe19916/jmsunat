package io.github.carlosthe19916.sunatjms.producer.service;

import io.github.carlosthe19916.sunatjms.producer.model.JMSBillMessageBean;
import io.github.carlosthe19916.sunatjms.producer.model.JMSSenderConfig;

import javax.jms.JMSException;

public interface JMSBillService {

    void sendBill(JMSSenderConfig config, JMSBillMessageBean bean) throws JMSException;

    void getStatus(JMSSenderConfig config, String ticket) throws JMSException;

    void sendSummary(JMSSenderConfig config, JMSBillMessageBean bean) throws JMSException;

    void sendPack(JMSSenderConfig config, JMSBillMessageBean bean) throws JMSException;

}

package io.github.carlosthe19916.sunatjms.producer.service;

import io.github.carlosthe19916.sunatjms.producer.model.JMSBillValidMessageBean;
import io.github.carlosthe19916.sunatjms.producer.model.JMSSenderConfig;

import javax.jms.JMSException;

public interface JMSBillValidService {

    void validaCDPcriterios(JMSSenderConfig config, JMSBillValidMessageBean bean) throws JMSException;

}

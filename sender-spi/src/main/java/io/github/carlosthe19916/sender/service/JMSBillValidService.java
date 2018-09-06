package io.github.carlosthe19916.sender.service;

import io.github.carlosthe19916.sender.model.JMSBillValidMessageBean;
import io.github.carlosthe19916.sender.model.JMSSenderConfig;

import javax.jms.JMSException;

public interface JMSBillValidService {

    void validaCDPcriterios(JMSSenderConfig config, JMSBillValidMessageBean bean) throws JMSException;

}

package io.github.carlosthe19916.jms.sender.service;

import io.github.carlosthe19916.jms.sender.model.BillValidBean;
import io.github.carlosthe19916.jms.sender.model.SendConfig;

import javax.jms.JMSException;

public interface BillValidService {

    void validaCDPcriterios(SendConfig config, BillValidBean bean) throws JMSException;

}

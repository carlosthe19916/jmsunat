package io.github.carlosthe19916.sender.service;

import io.github.carlosthe19916.sender.model.BillValidBean;
import io.github.carlosthe19916.sender.model.SenderConfig;

import javax.jms.JMSException;

public interface BillValidService {

    void validaCDPcriterios(SenderConfig config, BillValidBean bean) throws JMSException;

}

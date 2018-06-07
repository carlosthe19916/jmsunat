package io.github.carlosthe19916.jms.sender.service;

import io.github.carlosthe19916.jms.sender.model.BillConsultBean;
import io.github.carlosthe19916.jms.sender.model.SendConfig;

import javax.jms.JMSException;

public interface BillConsultService {

    void getStatus(SendConfig config, BillConsultBean bean) throws JMSException;

    void getStatusCdr(SendConfig config, BillConsultBean bean) throws JMSException;

}

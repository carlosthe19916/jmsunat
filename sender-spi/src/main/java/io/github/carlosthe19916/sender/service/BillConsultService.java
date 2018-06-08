package io.github.carlosthe19916.sender.service;

import io.github.carlosthe19916.sender.model.BillConsultBean;
import io.github.carlosthe19916.sender.model.SenderConfig;

import javax.jms.JMSException;

public interface BillConsultService {

    void getStatus(SenderConfig config, BillConsultBean bean) throws JMSException;

    void getStatusCdr(SenderConfig config, BillConsultBean bean) throws JMSException;

}

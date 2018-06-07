package io.github.carlosthe19916.jms.sender.service;

import io.github.carlosthe19916.jms.sender.model.BillBean;
import io.github.carlosthe19916.jms.sender.model.SendConfig;

import javax.jms.JMSException;

public interface BillService {

    void sendBill(SendConfig config, BillBean bean) throws JMSException;

    void getStatus(SendConfig config, String ticket) throws JMSException;

    void sendSummary(SendConfig config, BillBean bean) throws JMSException;

    void sendPack(SendConfig config, BillBean bean) throws JMSException;

}

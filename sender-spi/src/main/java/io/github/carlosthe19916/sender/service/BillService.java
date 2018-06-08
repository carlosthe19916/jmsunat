package io.github.carlosthe19916.sender.service;

import io.github.carlosthe19916.sender.model.BillBean;
import io.github.carlosthe19916.sender.model.SenderConfig;

import javax.jms.JMSException;

public interface BillService {

    void sendBill(SenderConfig config, BillBean bean) throws JMSException;

    void getStatus(SenderConfig config, String ticket) throws JMSException;

    void sendSummary(SenderConfig config, BillBean bean) throws JMSException;

    void sendPack(SenderConfig config, BillBean bean) throws JMSException;

}

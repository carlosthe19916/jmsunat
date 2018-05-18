package io.github.carlosthe19916.service;

import io.github.carlosthe19916.model.SendConfig;

import javax.jms.JMSException;

public interface SunatService {

    void sendFile(SendConfig sendConfig, byte[] file) throws JMSException;

    void checkTicket(SendConfig config, String ruc, String ticket) throws JMSException;

}

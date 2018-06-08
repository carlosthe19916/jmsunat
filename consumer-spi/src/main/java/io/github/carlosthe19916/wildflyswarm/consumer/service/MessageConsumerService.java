package io.github.carlosthe19916.wildflyswarm.consumer.service;

import javax.jms.Message;

public interface MessageConsumerService {

    void consumeMessage(Message message);

}

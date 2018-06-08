package io.github.carlosthe19916.wildflyswarm.sender.producers;

import io.github.carlosthe19916.wildflyswarm.sender.qualifiers.JMSunatQueue;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;

@ApplicationScoped
public class SenderConfigFactory {

    @Inject
    @JMSConnectionFactory("java:/jms/remote-mq")
    private JMSContext context;

    @Resource(mappedName = "jms/queue/sunat-queue")
    private Queue queue;

    @Produces
    @JMSunatQueue
    public JMSContext getJMSUnatContext() {
        return context;
    }

    @Produces
    @JMSunatQueue
    public Queue getJMSunatQueue() {
        return queue;
    }

}

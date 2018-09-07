package io.github.carlosthe19916.sunatjms.producer.javaee.producers;

import io.github.carlosthe19916.sunatjms.producer.javaee.qualifiers.JMSunatContext;
import io.github.carlosthe19916.sunatjms.producer.javaee.qualifiers.JMSunatQueue;

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
    @JMSConnectionFactory("java:/jms/RemoteArtemisCF")
    private JMSContext context;

    @Resource(lookup = "java:/exampleQueue")
    private Queue queue;

    @Produces
    @JMSunatContext
    public JMSContext getJMSUnatContext() {
        return context;
    }

    @Produces
    @JMSunatQueue
    public Queue getJMSunatQueue() {
        return queue;
    }

}

package io.github.carlosthe19916.sunatjms.consumer.javaee.service.producers;

import io.github.carlosthe19916.sunatjms.consumer.javaee.service.qualifiers.JMSunatContext;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;

@ApplicationScoped
public class SenderConfigFactory {

    @Inject
    @JMSConnectionFactory("java:/jms/RemoteArtemisCF")
    private JMSContext context;

    @Produces
    @JMSunatContext
    public JMSContext getJMSUnatContext() {
        return context;
    }
}

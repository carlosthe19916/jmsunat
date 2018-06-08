package io.github.carlosthe19916.wildflyswarm.sender;

import io.github.carlosthe19916.config.*;
import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;

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
    @BillServiceCpeUrl
    public String getBillServiceCpeUrl(
            @ConfigurationValue("io.github.carlosthe19916.ws.billServiceCpeUrl") String billServiceCpeUrl
    ) {
        return billServiceCpeUrl;
    }

}

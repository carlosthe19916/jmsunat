package io.github.carlosthe19916.wildflyswarm.config;

import io.github.carlosthe19916.config.*;
import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class CoreConfigFactory {

    @Produces
    @BillServiceCpeUrl
    public String getBillServiceCpeUrl(
            @ConfigurationValue("io.github.carlosthe19916.ws.billServiceCpeUrl") String billServiceCpeUrl
    ) {
        return billServiceCpeUrl;
    }

    @Produces
    @BillServiceGuiaUrl
    public String getBillServiceGuiaUrl(@ConfigurationValue("io.github.carlosthe19916.ws.billServiceGuiaUrl") String billServiceGuiaUrl) {
        return billServiceGuiaUrl;
    }

    @Produces
    @BillServiceRetentionPerceptionUrl
    public String getBillRetentionPerceptionUrl(
            @ConfigurationValue("io.github.carlosthe19916.ws.billRetentionPerceptionUrl") String billRetentionPerceptionUrl
    ) {
        return billRetentionPerceptionUrl;
    }

    @Produces
    @BillConsultServiceUrl
    public String getBillConsultService(
            @ConfigurationValue("io.github.carlosthe19916.ws.billConsultService") String billConsultService
    ) {
        return billConsultService;
    }

    @Produces
    @BillValidServiceUrl
    public String getBillValidServiceUrl(
            @ConfigurationValue("io.github.carlosthe19916.ws.billValidServiceUrl") String billValidServiceUrl
    ) {
        return billValidServiceUrl;
    }

}

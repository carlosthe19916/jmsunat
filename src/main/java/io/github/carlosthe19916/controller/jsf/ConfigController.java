package io.github.carlosthe19916.controller.jsf;

import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
public class ConfigController {

    @Inject
    @ConfigurationValue("io.github.carlosthe19916.defaultSunatEndpoint")
    private String defaultEndpoint;

    private String ruc = "10467793549";

    // Getters and Setters

    public String getDefaultEndpoint() {
        return defaultEndpoint;
    }

    public void setDefaultEndpoint(String defaultEndpoint) {
        this.defaultEndpoint = defaultEndpoint;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }
}

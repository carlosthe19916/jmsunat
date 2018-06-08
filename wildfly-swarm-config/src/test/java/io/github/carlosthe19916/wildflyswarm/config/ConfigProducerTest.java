package io.github.carlosthe19916.wildflyswarm.config;

import io.github.carlosthe19916.config.*;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.swarm.jaxrs.JAXRSArchive;

import javax.inject.Inject;
import java.io.File;
import java.net.URL;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class ConfigProducerTest {

    @Inject
    @BillServiceCpeUrl
    private String billServiceCpeUrl;

    @Inject
    @BillServiceGuiaUrl
    private String billServiceGuiaUrl;

    @Inject
    @BillServiceRetentionPerceptionUrl
    private String billServiceRetentionPerceptionUrl;

    @Inject
    @BillValidServiceUrl
    private String billValidServiceUrl;

    @Inject
    @BillConsultServiceUrl
    private String billConsultServiceUrl;

    @Deployment
    public static Archive createDeployment() throws Exception {
        URL url = Thread.currentThread().getContextClassLoader().getResource("project-test-defaults-path.yml");
        Assert.assertNotNull(url);
        File projectDefaults = new File(url.toURI());
        JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class, "sunat-jms-wildfly-swarm-config.war");
        deployment.addPackages(true, CoreConfigFactory.class.getPackage());
        deployment.setContextRoot("rest");
        deployment.addAsResource(projectDefaults, "/project-defaults.yml");
        deployment.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        deployment.addAllDependencies();
        return deployment;
    }

    @Test
    public void shouldProduce() {
        assertEquals("https://e-factura.sunat.gob.pe/ol-ti-itcpfegem/billService?wsdl", billServiceCpeUrl);
        assertEquals("https://e-guiaremision.sunat.gob.pe/ol-ti-itemision-guia-gem/billService?wsdl", billServiceGuiaUrl);
        assertEquals("https://e-factura.sunat.gob.pe/ol-ti-itemision-otroscpe-gem/billService?wsdl", billServiceRetentionPerceptionUrl);
        assertEquals("https://e-factura.sunat.gob.pe/ol-it-wsconsvalidcpe/billValidService?wsdl", billValidServiceUrl);
        assertEquals("https://e-factura.sunat.gob.pe/ol-it-wsconscpegem/billConsultService?wsdl", billConsultServiceUrl);
    }

}

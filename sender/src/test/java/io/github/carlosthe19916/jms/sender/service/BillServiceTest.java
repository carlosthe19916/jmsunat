package io.github.carlosthe19916.jms.sender.service;

import io.github.carlosthe19916.jms.sender.model.BillBean;
import io.github.carlosthe19916.jms.sender.model.SendConfig;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.swarm.jaxrs.JAXRSArchive;

import javax.inject.Inject;
import java.io.File;
import java.net.URL;

@Ignore
@RunWith(Arquillian.class)
public class BillServiceTest {

    private SendConfig config;

    @Inject
    private BillService billService;

    @Deployment
    public static Archive createDeployment() throws Exception {
        URL url = Thread.currentThread().getContextClassLoader().getResource("project-test-defaults-path.yml");
        Assert.assertNotNull(url);
        File projectDefaults = new File(url.toURI());
        JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class, "sunat-jms-thorntail.war");

        deployment.addClasses(BillService.class, BillServiceImpl.class);
        deployment.addClasses(SendConfig.class, BillBean.class);

        deployment.setContextRoot("rest");
        deployment.addAsResource(projectDefaults, "/project-defaults.yml");
        deployment.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        deployment.addAllDependencies();
        return deployment;
    }

    @Before
    public void before() {
        config = new SendConfig.Builder()
                .endpoint("myEndpoint")
                .username("myUsername")
                .password("myPassword")
                .build();
    }

    @Test
    public void shouldSendTicket() throws Exception {
        billService.getStatus(config, "myTicket");
        Assert.assertTrue(true);
    }

}
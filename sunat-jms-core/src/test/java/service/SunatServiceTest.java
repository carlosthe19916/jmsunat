package service;

import io.github.carlosthe19916.AbstractIntegrationTest;
import io.github.carlosthe19916.Log;
import io.github.carlosthe19916.LogAssert;
import io.github.carlosthe19916.model.SendConfig;
import io.github.carlosthe19916.repository.SimpleSunatRepository;
import io.github.carlosthe19916.repository.SunatRepository;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.swarm.jaxrs.JAXRSArchive;

import javax.inject.Inject;
import java.io.File;
import java.net.URL;

@RunWith(Arquillian.class)
public class SunatServiceTest extends AbstractIntegrationTest {

    private SendConfig sendConfig;

    @Inject
    private SunatService sunatService;

    @Deployment
    public static Archive createDeployment() throws Exception {
        URL url = Thread.currentThread().getContextClassLoader().getResource("project-test-defaults-path.yml");
        Assert.assertNotNull(url);
        File projectDefaults = new File(url.toURI());
        JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class, "sunat-jms-thorntail.war");

        deployment.addClasses(SunatRepository.class, SimpleSunatRepository.class);
        deployment.addClasses(SunatService.class, SunatServiceImpl.class);
        deployment.addClasses(SendConfig.class);

        deployment.addClasses(Log.class, LogAssert.class);

        deployment.setContextRoot("rest");
        deployment.addAsResource(projectDefaults, "/project-defaults.yml");
        deployment.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        deployment.addAllDependencies();
        return deployment;
    }

    @Before
    public void before() {
        sendConfig = new SendConfig.Builder()
                .endpoint("myEndpoint")
                .username("myUsername")
                .password("myPassword")
                .build();
    }

    @Test
    public void test() throws Exception {
        sunatService.checkTicket(sendConfig, "myRuc", "myTicket");
        Assert.assertTrue(true);
    }

}
package io.github.carlosthe19916.sunatjms.producer.model.copy;

import org.junit.Assert;
import org.junit.Test;

import io.github.carlosthe19916.sunatjms.producer.model.JMSSenderConfig;

public class JMSSenderConfigTest {

	/***
	 * {@link JMSSenderConfig#builder()}*/
	@Test
	public void test() {
		JMSSenderConfig config = new JMSSenderConfig.Builder()
		.endpoint("myendpoint")
		.username("myusername")
		.password("mypassword")
		.build();
		
		Assert.assertEquals("myendpoint", config.getEndpoint());
		Assert.assertEquals("myusername", config.getUsername());
		Assert.assertEquals("mypassword", config.getPassword());
	}

}

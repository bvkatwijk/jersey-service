package org.bvkatwijk.micro.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Assert;
import org.junit.Test;

public class ResourceConfigFactoryTest {

	private static final ResourceConfig result = ResourceConfigFactoryTest.createConfigFromFactory();

	@Test(expected = NullPointerException.class)
	public void factory_throwsWhenNullConfigurationProvided() {
		new ResourceConfigFactory(
				null,
				"servletPackage",
				"applicationName");
	}

	@Test
	public void factoryResult_shouldHaveCorrectApplicationName() {
		Assert.assertEquals("applicationName", result.getApplicationName());
	}

	private static ResourceConfig createConfigFromFactory() {
		return new ResourceConfigFactory(
				new NoConfiguration(),
				null,
				"applicationName")
				.createResourceConfig();
	}

}

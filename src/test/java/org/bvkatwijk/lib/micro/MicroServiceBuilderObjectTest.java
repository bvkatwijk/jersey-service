package org.bvkatwijk.lib.micro;

import org.bvkatwijk.lib.micro.MicroService.MicroServiceBuilder;
import org.junit.Assert;
import org.junit.Test;

public class MicroServiceBuilderObjectTest {

	private static MicroServiceBuilder microServiceBuilder = TestMicroServices.getStandardMicroServiceBuilder();

	@Test
	public void microServiceToString_showsApplicationName() {
		Assert.assertTrue(microServiceBuilder.toString().contains("Test Application"));
	}

	@Test
	public void microServiceToString_showsPort() {
		Assert.assertTrue(microServiceBuilder.toString().contains("8080"));
	}

	@Test
	public void microServiceToString_showsHomePageFileName() {
		Assert.assertTrue(microServiceBuilder.toString().contains("index.html"));
	}

	@Test
	public void microServiceToString_showsHomePageFolder() {
		Assert.assertTrue(microServiceBuilder.toString().contains("web"));
	}

	@Test
	public void microServiceToString_showsServletUrlPath() {
		Assert.assertTrue(microServiceBuilder.toString().contains("api"));
	}

	@Test
	public void microServiceToString_showsPackage() {
		Assert.assertTrue(microServiceBuilder.toString().contains("org.bvkatwijk.micro"));
	}

}

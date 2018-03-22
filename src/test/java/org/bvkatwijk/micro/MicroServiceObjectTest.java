package org.bvkatwijk.micro;

import org.bvkatwijk.micro.e2e.base.TestMicroServices;
import org.junit.Assert;
import org.junit.Test;

public class MicroServiceObjectTest {

	private static MicroService microService = TestMicroServices.getStandardMicroServiceBuilder().build();

	@Test
	public void microServiceToString_showsApplicationName() {
		Assert.assertTrue(microService.toString().contains("Test Application"));
	}

	@Test
	public void microServiceToString_showsPort() {
		Assert.assertTrue(microService.toString().contains("" + TestMicroServices.randomPort));
	}

	@Test
	public void microServiceToString_showsHomePageFileName() {
		Assert.assertTrue(microService.toString().contains("index.html"));
	}

	@Test
	public void microServiceToString_showsHomePageFolder() {
		Assert.assertTrue(microService.toString().contains("web"));
	}

	@Test
	public void microServiceToString_showsServletUrlPath() {
		Assert.assertTrue(microService.toString().contains("api"));
	}

	@Test
	public void microServiceToString_showsPackage() {
		Assert.assertTrue(microService.toString().contains("org.bvkatwijk.micro"));
	}
}

package org.bvkatwijk.micro.def;

import org.bvkatwijk.micro.def.MicroServiceDefaults;
import org.junit.Assert;
import org.junit.Test;

public class MicroServiceDefaultsTest {

	@Test
	public void defaultServletPath_shouldBeSet() {
		Assert.assertEquals("/api/*", MicroServiceDefaults.SERVLETS_URL_PATH);
	}

	@Test
	public void defaultHomePageFileName_shouldBeSet() {
		Assert.assertEquals("index.html", MicroServiceDefaults.HOME_PAGE_FILENAME);
	}

	@Test
	public void defaultApplicationName_shouldBeSet() {
		Assert.assertEquals("Application", MicroServiceDefaults.APPLICATION_NAME);
	}

	@Test
	public void defaultServletPackage_shouldBeSet() {
		Assert.assertEquals("org.bvkatwijk.api", MicroServiceDefaults.SERVLET_PACKAGE);
	}

	@Test
	public void defaultPort_shouldBeSet() {
		Assert.assertEquals(8080, MicroServiceDefaults.PORT_NUMBER);
	}

}

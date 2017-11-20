package org.bvkatwijk.micro.handler;

import org.eclipse.jetty.server.handler.ResourceHandler;
import org.junit.Assert;
import org.junit.Test;

public class ResourceHandlerFactoryTest {

	private static final ResourceHandler result = ResourceHandlerFactoryTest.createResourceHandler();

	@Test(expected = NullPointerException.class)
	public void factory_shouldThrowonNullHomepageFileName() {
		new ResourceHandlerFactory(null, this.getClass(), "homepageFolder");
	}

	@Test(expected = NullPointerException.class)
	public void factory_shouldThrowonNullMainClass() {
		new ResourceHandlerFactory("homepageFileName", null, "homepageFolder");
	}

	@Test(expected = NullPointerException.class)
	public void factory_shouldThrowonNullMainClassa() {
		new ResourceHandlerFactory("homepageFileName", this.getClass(), null);
	}

	@Test
	public void result_shouldHaveDirectoriesListedTrue() {
		Assert.assertTrue(result.isDirectoriesListed());
	}

	@Test
	public void result_shouldHaveOneWelcomeFile() {
		Assert.assertEquals(
				1,
				result.getWelcomeFiles().length);
	}

	@Test
	public void result_shouldHaveCorrectWelcomeFile() {
		Assert.assertEquals(
				"homepageFileName",
				result.getWelcomeFiles()[0]);
	}

	@Test
	public void result_shouldHaveHomepageFolderPathSet() {
		Assert.assertTrue(result.getResourceBase().endsWith("homepageFolder"));
	}

	private static ResourceHandler createResourceHandler() {
		return new ResourceHandlerFactory(
				"homepageFileName",
				ResourceHandlerFactoryTest.class,
				"homepageFolder")
				.createResourceHandler();
	}

}

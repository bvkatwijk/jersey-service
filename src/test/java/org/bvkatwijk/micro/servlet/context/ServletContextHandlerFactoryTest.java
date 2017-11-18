package org.bvkatwijk.micro.servlet.context;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.Assert;
import org.junit.Test;

public class ServletContextHandlerFactoryTest {

	private static final ServletContextHandler result = ServletContextHandlerFactoryTest.useFactory();

	@Test(expected = NullPointerException.class)
	public void factory_throwsWhenNullServletHolder() {
		new ServletContextHandlerFactory(null, "servletsUrlPath");
	}

	@Test(expected = NullPointerException.class)
	public void factory_throwsWhenNullServletsUrlPath() {
		new ServletContextHandlerFactory(new ServletHolder(), null);
	}

	@Test
	public void resultingHandler_shouldHaveContextPathSet() {
		Assert.assertEquals("/", result.getContextPath());
	}

	private static ServletContextHandler useFactory() {
		return new ServletContextHandlerFactory(new ServletHolder(), "servletsUrlPath")
				.get();
	}

}

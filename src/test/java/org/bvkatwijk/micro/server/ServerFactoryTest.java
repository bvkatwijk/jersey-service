package org.bvkatwijk.micro.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.junit.Assert;
import org.junit.Test;

public class ServerFactoryTest {

	private static final int port = (int)(Math.random() * 10000);
	private static final Server result = ServerFactoryTest.createServer(port);

	@Test(expected = NullPointerException.class)
	public void factory_shouldThrowOnNullContext() {
		new ServerFactory(port, null, new ResourceHandler());
	}

	@Test(expected = NullPointerException.class)
	public void factory_shouldThrowOnNullResourceHandler() {
		new ServerFactory(port, new ServletContextHandler(), null);
	}

	@Test
	public void result_shouldHaveCorrectPortSet() {
		Assert.assertEquals(
				port,
				((ServerConnector) result.getConnectors()[0]).getPort());
	}

	private static Server createServer(int port) {
		return new ServerFactory(
				port,
				new ServletContextHandler(),
				new ResourceHandler())
				.get();
	}

}

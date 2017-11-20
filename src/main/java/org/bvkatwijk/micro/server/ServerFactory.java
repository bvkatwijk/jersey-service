package org.bvkatwijk.micro.server;

import java.util.function.Supplier;

import org.bvkatwijk.micro.MicroService;
import org.bvkatwijk.micro.config.ResourceConfigFactory;
import org.bvkatwijk.micro.consume.Subject;
import org.bvkatwijk.micro.handler.ResourceHandlerFactory;
import org.bvkatwijk.micro.servlet.context.ServletContextHandlerFactory;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.handler.gzip.GzipHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ServerFactory implements Supplier<Server> {

	private final int port;
	private final @NonNull ServletContextHandler context;
	private final @NonNull ResourceHandler resouceHandler;

	@Override
	public Server get() {
		return new Subject<Server>()
				.lendTo(it -> it.setHandler(createGzipHandler()))
				.apply(new Server(port));
	}

	private Handler createGzipHandler() {
		return new Subject<GzipHandler>()
				.lendTo(it -> it.setHandler(createHandlerList()))
				.apply(new GzipHandler());
	}

	private HandlerList createHandlerList() {
		return new Subject<HandlerList>()
				.lendTo(it -> it.setHandlers(createHandlerArray()))
				.apply(new HandlerList());
	}

	private Handler[] createHandlerArray() {
		return new Handler[] {
				resouceHandler,
				context,
				new DefaultHandler(),
				new RequestLogHandler() };
	}

	public static Server createFor(MicroService microService) {
		return new ServerFactory(
				microService.getPort(),
				ServerFactory.createServletContextHandler(microService),
				new ResourceHandlerFactory(
						microService.getHomePageFileName(),
						microService.getMainClass(),
						microService.getHomePageFolder())
				.createResourceHandler())
				.get();
	}

	private static ServletContextHandler createServletContextHandler(MicroService microService) {
		return new ServletContextHandlerFactory(
				ServerFactory.createServletHolder(ResourceConfigFactory.createFor(microService)),
				microService.getServletsUrlPath()).get();
	}

	private static ServletHolder createServletHolder(ResourceConfig jerseyApplication) {
		return new Subject<ServletHolder>()
				.lendTo(it -> it.setInitOrder(0))
				.apply(new ServletHolder(new ServletContainer(jerseyApplication)));
	}

}

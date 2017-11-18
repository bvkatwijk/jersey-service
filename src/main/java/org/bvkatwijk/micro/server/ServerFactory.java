package org.bvkatwijk.micro.server;

import java.util.function.Supplier;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.handler.gzip.GzipHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ServerFactory implements Supplier<Server> {

	private final int port;
	private final @NonNull ServletContextHandler context;
	private final @NonNull ResourceHandler resouceHandler;

	@Override
	public Server get() {
		Server server = new Server(port);
		server.setHandler(createGzipHandler());
		return server;
	}

	private Handler createGzipHandler() {
		GzipHandler gzipHandler = new GzipHandler();
		gzipHandler.setHandler(createHandlerList());
		return gzipHandler;
	}

	private HandlerList createHandlerList() {
		HandlerList handlers = new HandlerList();
		handlers.setHandlers(new Handler[] {
				resouceHandler,
				context,
				new DefaultHandler(),
				new RequestLogHandler() });
		return handlers;
	}

}

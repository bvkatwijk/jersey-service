package org.bvkatwijk.micro;

import org.bvkatwijk.micro.config.Configuration;
import org.bvkatwijk.micro.config.ResourceConfigFactory;
import org.bvkatwijk.micro.consume.ConsumingFunction;
import org.bvkatwijk.micro.def.MicroServiceDefaults;
import org.bvkatwijk.micro.folder.HomepageFolderProvider;
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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * MicroService blueprint. Use builder for fluid initialization. All fields have
 * some default value set. Call {@link #start()} after construction.
 *
 * @author bvkatwijk
 */
@Slf4j
@Builder
@ToString
@AllArgsConstructor
public class MicroService {

	private final Class<?> mainClass;
	private final String servletsUrlPath;
	private final String homePageFolder;
	private final String homePageFileName;
	private final String applicationName;
	private final int port;
	private final String servletPackage;
	private final Configuration configuration;

	/**
	 * Initialize MicroService Builder
	 * @param mainClass Application root class
	 */
	public static MicroServiceBuilder builder(Class<?> mainClass) {
		return new MicroServiceBuilder().mainClass(mainClass);
	}

	public static class MicroServiceBuilder {

		private String servletsUrlPath = MicroServiceDefaults.SERVLETS_URL_PATH;
		private String homePageFolder = MicroServiceDefaults.HOME_PAGE_FOLDER;
		private String homePageFileName = MicroServiceDefaults.HOME_PAGE_FILENAME;
		private String applicationName = MicroServiceDefaults.APPLICATION_NAME;
		private String servletPackage = MicroServiceDefaults.SERVLET_PACKAGE;
		private int port = MicroServiceDefaults.PORT_NUMBER;
		private Configuration configuration = MicroServiceDefaults.CONFIGURATION;

	}

	/**
	 * Start a {@link Server} hosting this {@link MicroService}
	 *
	 * @throws Exception when {@link Server#start()} does so.
	 * @since 0.0.1
	 */
	public void start() throws Exception {
		Server server = createServer();
		server.start();
		log.trace(server.dump());
	}

	private ResourceConfig createResourceConfig() {
		return new ResourceConfigFactory(configuration, servletPackage, applicationName)
				.createResourceConfig();
	}

	private static ServletHolder createServletHolder(ResourceConfig jerseyApplication) {
		ServletHolder jerseyServlet = new ServletHolder(new ServletContainer(jerseyApplication));
		jerseyServlet.setInitOrder(0);
		return jerseyServlet;
	}

	private Handler createGzipHandler(ServletContextHandler context) {
		GzipHandler gzipHandler = new GzipHandler();
		HandlerList handlers = new HandlerList();
		handlers.setHandlers(new Handler[] {
				createResourceHandler(),
				context,
				new DefaultHandler(),
				new RequestLogHandler() });
		gzipHandler.setHandler(handlers);
		return gzipHandler;
	}

	private ResourceHandler createResourceHandler() {
		return ConsumingFunction.<ResourceHandler>identity()
				.andThen(it -> it.setDirectoriesListed(true))
				.andThen(it -> it.setWelcomeFiles(new String[] { homePageFileName }))
				.andThen(it -> it.setResourceBase(new HomepageFolderProvider(mainClass, homePageFolder).get()))
				.apply(new ResourceHandler());
	}

	private Server createServer() {
		ServletHolder createServletHolder = MicroService.createServletHolder(createResourceConfig());
		return setup(
				new Server(port),
				new ServletContextHandlerFactory(createServletHolder, servletsUrlPath).get());
	}

	private Server setup(Server server, ServletContextHandler context) {
		server.setHandler(context);
		server.setHandler(createGzipHandler(context));
		return server;
	}

}

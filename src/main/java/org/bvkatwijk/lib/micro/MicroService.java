package org.bvkatwijk.lib.micro;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.handler.gzip.GzipHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * MicroService blueprint. Use builder for fluid initialization. All fields have
 * some default value set. Call {@link #start()} after construction.
 *
 * @author borisk
 */
@Slf4j
@ToString
@Builder
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
	 * @throws Exception
	 *             when {@link Server#start()} does so.
	 * @since 0.0.1
	 */
	public void start() throws Exception {
		Server server = createServer();
		server.start();
		log.trace(server.dump());
	}

	private void setup(Server server, ServletContextHandler context) {
		context.addServlet(createServletHolder(createResourceConfig()), servletsUrlPath);
		server.setHandler(context);
		server.setHandler(createGzipHandler(context));
	}

	private ResourceConfig createResourceConfig() {
		return new ResourceConfig()
				.register(createProvider())
				.register(bindings())
				.packages(servletPackage)
				.setApplicationName(applicationName);
	}

	private static JacksonJaxbJsonProvider createProvider() {
		JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider();
		provider.setMapper(createObjectMapper());
		return provider;
	}

	private static ObjectMapper createObjectMapper() {
		return new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
	}

	private AbstractBinder bindings() {
		return new AbstractBinder() {
			@Override
			protected void configure() {
				configuration.configure(this);
			}
		};
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
		ResourceHandler resourceHandler = new ResourceHandler();
		resourceHandler.setDirectoriesListed(true);
		resourceHandler.setWelcomeFiles(new String[] { homePageFileName });
		resourceHandler.setResourceBase(new HomepageFolderProvider(mainClass, homePageFolder).get());
		return resourceHandler;
	}

	private Server createServer() {
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");
		Server server = new Server(port);
		setup(server, context);
		return server;
	}
}

package org.bvkatwijk.micro;

import org.bvkatwijk.micro.config.Configuration;
import org.bvkatwijk.micro.consume.Subject;
import org.bvkatwijk.micro.def.MicroServiceDefaults;
import org.bvkatwijk.micro.server.ServerFactory;
import org.eclipse.jetty.server.Server;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
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
@Getter
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
	public Server start() throws Exception {
		return new Subject<Server>()
				.lendTo(it -> start(it))
				.lendTo(it -> log.trace(it.dump()))
				.apply(ServerFactory.createFor(this));
	}

	private void start(Server server) {
		try {
			server.start();
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

}

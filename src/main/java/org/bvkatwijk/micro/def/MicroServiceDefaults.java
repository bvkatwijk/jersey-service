package org.bvkatwijk.micro.def;

import org.bvkatwijk.micro.config.Configuration;
import org.bvkatwijk.micro.config.NoConfiguration;

public final class MicroServiceDefaults {

	public static final String SERVLETS_URL_PATH = "/api/*";
	public static final String HOME_PAGE_FOLDER = "web";
	public static final String HOME_PAGE_FILENAME = "index.html";
	public static final String APPLICATION_NAME = "Application";
	public static final String SERVLET_PACKAGE = "org.bvkatwijk.api";
	public static final int PORT_NUMBER = 8080;
	public static final Configuration CONFIGURATION = new NoConfiguration();

}

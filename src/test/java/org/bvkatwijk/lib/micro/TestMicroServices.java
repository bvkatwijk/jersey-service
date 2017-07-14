package org.bvkatwijk.lib.micro;

import org.bvkatwijk.lib.micro.MicroService;
import org.bvkatwijk.lib.micro.MicroService.MicroServiceBuilder;

public class TestMicroServices {

	public static MicroServiceBuilder getStandardMicroServiceBuilder() {
		return MicroService.builder(TestMicroServices.class)
				.applicationName("Test Application")
				.port(8080)
				.homePageFileName("index.html")
				.homePageFolder("web")
				.servletsUrlPath("api")
				.servletPackage("org.bvkatwijk.micro");
	}
}

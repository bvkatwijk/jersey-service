package org.bvkatwijk.micro;

import org.bvkatwijk.micro.service.MicroService;
import org.bvkatwijk.micro.service.MicroService.MicroServiceBuilder;

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

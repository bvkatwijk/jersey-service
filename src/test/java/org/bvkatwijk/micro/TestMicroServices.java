package org.bvkatwijk.micro;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.bvkatwijk.micro.MicroService.MicroServiceBuilder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mashape.unirest.http.Unirest;

public class TestMicroServices {

	public static final int randomPort = (int) (Math.random() * 10000);

	@Path("/test")
	@Produces(MediaType.TEXT_PLAIN)
	public static class TestApi {

		@GET
		public String getText() {
			return "Hello World from MicroService";
		}

	}

	@BeforeClass
	public static void startServer() {
		TestMicroServices.getStandardMicroServiceBuilder()
		.build()
		.start();
	}

	public static MicroServiceBuilder getStandardMicroServiceBuilder() {
		return MicroService.builder(TestMicroServices.class)
				.applicationName("Test Application")
				.homePageFileName("index.html")
				.homePageFolder("web")
				.port(randomPort)
				.servletsUrlPath("/api/*")
				.servletPackage("org.bvkatwijk.micro");
	}

	@Test
	public void microService_getText_shouldReturnCorrectString() throws Exception {
		Assert.assertEquals("Hello World from MicroService",
				Unirest.get("http://localhost:" + randomPort + "/api/test")
				.asString()
				.getBody());
	}

	@Test
	public void microService_getText_shouldRespond200() throws Exception {
		Assert.assertEquals(200,
				Unirest.get("http://localhost:" + randomPort + "/api/test")
				.asString()
				.getStatus());
	}

}

package org.bvkatwijk.micro.e2e.base;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.bvkatwijk.micro.MicroService;
import org.bvkatwijk.micro.MicroService.MicroServiceBuilder;
import org.eclipse.jetty.server.Server;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mashape.unirest.http.Unirest;

public class TestMicroServices {

	public static final int randomPort = (int) (Math.random() * 10000);
	private static Server server;

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
		server = TestMicroServices.getStandardMicroServiceBuilder()
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
				.servletPackage("org.bvkatwijk.micro.e2e.base");
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

	@Test
	public void microService_getHeaders_shouldNotHaveAdditionalFilters() throws Exception {
		Assert.assertEquals(null,
				Unirest.get("http://localhost:" + randomPort + "/api/test")
						.asString()
						.getHeaders()
						.get("Access-Control-Allow-Origin"));
	}

	@AfterClass
	public static void stopServer() throws Exception {
		server.stop();
	}

}

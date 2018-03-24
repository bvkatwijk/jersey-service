package org.bvkatwijk.micro.e2e.cors;

import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.bvkatwijk.micro.MicroService;
import org.bvkatwijk.micro.config.CORSFilter;
import org.bvkatwijk.micro.e2e.base.TestMicroServices;
import org.junit.Assert;
import org.junit.Test;

import com.mashape.unirest.http.Headers;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class MicroServiceHeadersTest {

	private static Headers headers = requestHeaders();

	@Path("/test")
	@Produces(MediaType.TEXT_PLAIN)
	public static class TestApi {

		@GET
		public String getText() {
			return "Hello World from MicroService";
		}

	}

	private static Headers requestHeaders() {
		MicroService.builder(TestMicroServices.class)
				.applicationName("Test Application")
				.additionalProviders(Set.of(CORSFilter.class))
				.homePageFileName("index.html")
				.homePageFolder("web")
				.port(10000)
				.servletsUrlPath("/api/*")
				.servletPackage("org.bvkatwijk.micro.e2e.cors")
				.build()
				.start();
		try {
			return Unirest.get("http://localhost:" + 10000 + "/api/test")
					.asString()
					.getHeaders();
		} catch (UnirestException e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void headers_shouldContainAddedElements() {
		Assert.assertEquals("*", headers.get("Access-Control-Allow-Origin").get(0));
	}

}

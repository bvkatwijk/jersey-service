package org.bvkatwijk.micro.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

public class MappingProviderFactory {

	public static JacksonJaxbJsonProvider create() {
		return new JacksonJaxbJsonProvider(
				MappingProviderFactory.createObjectMapper(),
				JacksonJaxbJsonProvider.DEFAULT_ANNOTATIONS);
	}

	private static ObjectMapper createObjectMapper() {
		return new ObjectMapper()
				.enable(SerializationFeature.INDENT_OUTPUT);
	}

}

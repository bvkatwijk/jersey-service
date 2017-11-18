package org.bvkatwijk.micro.service.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

public class MappingProviderFactory {

	public JacksonJaxbJsonProvider create() {
		JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider();
		provider.setMapper(get());
		return provider;
	}

	private ObjectMapper get() {
		return new ObjectMapper()
				.enable(SerializationFeature.INDENT_OUTPUT);
	}

}

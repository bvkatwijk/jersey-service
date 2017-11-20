package org.bvkatwijk.micro.mapper;

import org.bvkatwijk.micro.consume.Subject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

public class MappingProviderFactory {

	public JacksonJaxbJsonProvider create() {
		return new Subject<JacksonJaxbJsonProvider>()
				.lendTo(it -> it.setMapper(createObjectMapper()))
				.apply(new JacksonJaxbJsonProvider());
	}

	private ObjectMapper createObjectMapper() {
		return new ObjectMapper()
				.enable(SerializationFeature.INDENT_OUTPUT);
	}

}

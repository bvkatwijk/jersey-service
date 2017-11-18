package org.bvkatwijk.micro.service.config;

import org.bvkatwijk.micro.service.mapper.MappingProviderFactory;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ResourceConfigFactory {

	private final Configuration configuration;
	private final String servletPackage;
	private final String applicationName;

	public ResourceConfig createResourceConfig() {
		return new ResourceConfig()
				.register(new MappingProviderFactory().create())
				.register(bindings())
				.packages(servletPackage)
				.setApplicationName(applicationName);
	}

	private AbstractBinder bindings() {
		return new AbstractBinder() {
			@Override
			protected void configure() {
				configuration.configure(this);
			}
		};
	}

}

package org.bvkatwijk.micro.config;

import java.util.Set;

import org.bvkatwijk.micro.MicroService;
import org.bvkatwijk.micro.mapper.MappingProviderFactory;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ResourceConfigFactory {

	private final @NonNull Configuration configuration;
	private final String servletPackage;
	private final String applicationName;
	private final Set<Class<?>> additionalClasses;

	public ResourceConfig createResourceConfig() {
		return new ResourceConfig()
				.register(MappingProviderFactory.create())
				.registerClasses(this.additionalClasses)
				.register(bindings())
				.packages(this.servletPackage)
				.setApplicationName(this.applicationName);
	}

	private AbstractBinder bindings() {
		return new AbstractBinder() {

			@Override
			protected void configure() {
				ResourceConfigFactory.this.configuration.configure(this);
			}
		};
	}

	public static ResourceConfig createFor(MicroService service) {
		return new ResourceConfigFactory(
				service.getConfiguration(),
				service.getServletPackage(),
				service.getApplicationName(),
				service.getAdditionalProviders())
						.createResourceConfig();
	}

}

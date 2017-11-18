package org.bvkatwijk.micro.handler;

import java.util.function.Consumer;

import org.bvkatwijk.micro.consume.Subject;
import org.bvkatwijk.micro.folder.HomepageFolderProvider;
import org.eclipse.jetty.server.handler.ResourceHandler;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ResourceHandlerFactory {

	private final String homepageFileName;
	private final Class<?> mainClass;
	private final String homepageFolder;

	public ResourceHandler createResourceHandler() {
		return new Subject<ResourceHandler>()
				.lendTo(it -> it.setDirectoriesListed(true))
				.lendTo(it -> it.setWelcomeFiles(new String[] { homepageFileName }))
				.lendTo(setResouceBase())
				.apply(new ResourceHandler());
	}

	private Consumer<ResourceHandler> setResouceBase() {
		return it -> it.setResourceBase(
				new HomepageFolderProvider(
						mainClass,
						homepageFolder)
				.get());
	}

}

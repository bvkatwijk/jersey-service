package org.bvkatwijk.micro.handler;

import java.util.function.Consumer;

import org.bvkatwijk.micro.consume.Subject;
import org.bvkatwijk.micro.folder.HomepageFolderProvider;
import org.eclipse.jetty.server.handler.ResourceHandler;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ResourceHandlerFactory {

	private final @NonNull String homepageFileName;
	private final @NonNull Class<?> mainClass;
	private final @NonNull String homepageFolder;

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

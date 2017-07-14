package org.bvkatwijk.lib.micro;

import java.net.URL;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class HomepageFolderProvider {

	private final Class<?> mainClass;
	private final String homePageFolder;

	public String get() {
		return getRelativeHomepageFolder() == null ? getDebugHomepageFolder() : getProductionHomepageFolder();
	}

	private String getProductionHomepageFolder() {
		log.info("Running in production mode.");
		return getRelativeHomepageFolder().toExternalForm();
	}

	private String getDebugHomepageFolder() {
		log.warn("Running in debug mode.");
		return homePageFolder;
	}

	private URL getRelativeHomepageFolder() {
		return mainClass.getClassLoader().getResource(homePageFolder);
	}

}

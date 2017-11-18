package org.bvkatwijk.micro.service.folder;

import org.junit.Assert;
import org.junit.Test;

public class HomepageFolderProviderTest {

	private static final HomepageFolderProvider provider = new HomepageFolderProvider(
			HomepageFolderProviderTest.class,
			"homepageFolder"
			);

	@Test
	public void homepageFolderProvider_shouldReturnDebugHomepageFolder() {
		Assert.assertEquals("homepageFolder", provider.get());
	}

}

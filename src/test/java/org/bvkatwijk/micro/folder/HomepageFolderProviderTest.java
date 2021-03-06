package org.bvkatwijk.micro.folder;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test cases for {@link HomepageFolderProvider}
 */
public class HomepageFolderProviderTest {

	@Test(expected = NullPointerException.class)
	public void homepageFolderProvider_shouldThrow_whenNullClassProvided() {
		new HomepageFolderProvider(null, "web");
	}

	@Test(expected = NullPointerException.class)
	public void homepageFolderProvider_shouldThrow_whenNullFolderProvided() {
		new HomepageFolderProvider(HomepageFolderProviderTest.class, null);
	}

	@Test
	public void homepageFolderProvider_shouldReturnInputFolder_whenNotFindingRelativeFolder() {
		Assert.assertEquals(
				"folder",
				new HomepageFolderProvider(HomepageFolderProviderTest.class, "folder").get());
	}

}

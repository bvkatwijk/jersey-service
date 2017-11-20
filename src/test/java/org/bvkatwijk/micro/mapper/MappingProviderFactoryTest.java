package org.bvkatwijk.micro.mapper;

import org.junit.Assert;
import org.junit.Test;

public class MappingProviderFactoryTest {

	@Test
	public void factory_shouldCreateMappingProvider() {
		Assert.assertNotNull(MappingProviderFactory.create());
	}

}

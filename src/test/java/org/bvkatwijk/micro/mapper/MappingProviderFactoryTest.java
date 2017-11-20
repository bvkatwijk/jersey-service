package org.bvkatwijk.micro.mapper;

import org.junit.Assert;
import org.junit.Test;

public class MappingProviderFactoryTest {

	@Test
	public void factory_shouldCreateMappingProviderWithObjectMapperSet() {
		Assert.assertNotNull(new MappingProviderFactory().create().locateMapper(null, null));
	}

}

package org.bvkatwijk.micro.consume;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class SubjectTest {

	private static final List<String> NO_OP = new Subject<List<String>>()
			.apply(new ArrayList<>());

	private static final List<String> ONE_OP = new Subject<List<String>>()
			.lendTo(list -> list.add("A"))
			.apply(new ArrayList<>());

	private static final List<String> TWO_OPS = new Subject<List<String>>()
			.lendTo(list -> list.add("A"))
			.lendTo(list -> list.add("B"))
			.apply(new ArrayList<>());

	@Test
	public void subject_withNoOperation_shouldHaveExecutedCorrectly() {
		Assert.assertEquals(0, NO_OP.size());
	}

	@Test
	public void subject_withOneOperation_shouldHaveExecutedCorrectAmount() {
		Assert.assertEquals(1, ONE_OP.size());
	}

	@Test
	public void subject_withOneOperation_shouldHaveCorrectResult() {
		Assert.assertEquals("A", ONE_OP.get(0));
	}

	@Test
	public void subject_withTwoOperations_shouldHaveExecutedCorrectly() {
		Assert.assertEquals(2, TWO_OPS.size());
	}

	@Test
	public void subject_withTwoOperations_shouldHaveCorrectFirstResult() {
		Assert.assertEquals("A", TWO_OPS.get(0));
	}

	@Test
	public void subject_withTwoOperations_shouldHaveCorrectSecondResult() {
		Assert.assertEquals("B", TWO_OPS.get(1));
	}


}

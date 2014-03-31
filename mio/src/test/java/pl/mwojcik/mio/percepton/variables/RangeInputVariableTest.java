package pl.mwojcik.mio.percepton.variables;

import org.junit.Test;

public class RangeInputVariableTest {

	@Test(expected = IllegalArgumentException.class)
	public void testRangeInputVariable() {
		@SuppressWarnings("unused")
		RangeInputVariable<BinaryRange> variable = BinaryVariable.factory(2);
	}

}

package pl.mwojcik.mio.percepton.variables;

import org.junit.Test;

import pl.mwojcik.mio.percepton.variables.BinaryRange;
import pl.mwojcik.mio.percepton.variables.BinaryVariable;
import pl.mwojcik.mio.percepton.variables.RangeInputVariable;

public class RangeInputVariableTest {

	@Test(expected = IllegalArgumentException.class)
	public void testRangeInputVariable() {
		@SuppressWarnings("unused")
		RangeInputVariable<BinaryRange> variable = BinaryVariable.factory(2);
	}

}

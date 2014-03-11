package pl.mwojcik.mio.percepton;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import pl.mwojcik.mio.percepton.WeightedSumPerceptron;
import pl.mwojcik.mio.percepton.functions.Function;
import pl.mwojcik.mio.percepton.functions.LineFunction;
import pl.mwojcik.mio.percepton.variables.BinaryRange;
import pl.mwojcik.mio.percepton.variables.BinaryVariable;
import pl.mwojcik.mio.percepton.variables.InputVariableList;
import pl.mwojcik.mio.percepton.variables.RangeInputVariable;

public class WeightedSumPerceptronTest {

	WeightedSumPerceptron<RangeInputVariable<BinaryRange>> perceptron;
	
	@Before
	public void setUp() throws Exception {
		Function function = new LineFunction(1, 0);

		List<Double> weights = new ArrayList<>();
		
		weights.add(0.);
		weights.add(0.99);
		weights.add(1.1);
		weights.add(0.9);

		perceptron = new WeightedSumPerceptron<>(
				function, 3, weights, 2);
	}

	@Test
	public void testIsActiveDog() {

		InputVariableList<RangeInputVariable<BinaryRange>> dog = new InputVariableList<>(3);
		
		dog.setVariable(0, BinaryVariable.factory(1));
		dog.setVariable(1, BinaryVariable.factory(1));
		dog.setVariable(2, BinaryVariable.factory(0));
		
		assertTrue(perceptron.isActive(dog));
	}
	@Test
	public void testIsActiveCat() {

		InputVariableList<RangeInputVariable<BinaryRange>> cat = new InputVariableList<>(3);
		
		cat.setVariable(0, BinaryVariable.factory(1));
		cat.setVariable(1, BinaryVariable.factory(0));
		cat.setVariable(2, BinaryVariable.factory(1));
		
		assertFalse(perceptron.isActive(cat));
	}
}

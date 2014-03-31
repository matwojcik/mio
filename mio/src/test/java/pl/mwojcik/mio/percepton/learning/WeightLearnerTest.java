package pl.mwojcik.mio.percepton.learning;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import pl.mwojcik.mio.percepton.WeightedSumPerceptron;
import pl.mwojcik.mio.percepton.functions.Function;
import pl.mwojcik.mio.percepton.functions.LineFunction;
import pl.mwojcik.mio.percepton.functions.SigmoidalFunction;
import pl.mwojcik.mio.percepton.variables.BinaryRange;
import pl.mwojcik.mio.percepton.variables.BinaryVariable;
import pl.mwojcik.mio.percepton.variables.InputVariable;
import pl.mwojcik.mio.percepton.variables.InputVariableImpl;
import pl.mwojcik.mio.percepton.variables.InputVariableList;
import pl.mwojcik.mio.percepton.variables.RangeInputVariable;

public class WeightLearnerTest {

	@Test
	public void catDogTest() {

		InputVariableList<RangeInputVariable<BinaryRange>> cat = new InputVariableList<>(3);
		
		cat.setVariable(0, BinaryVariable.factory(1));
		cat.setVariable(1, BinaryVariable.factory(0));
		cat.setVariable(2, BinaryVariable.factory(1));
		
		InputVariableList<RangeInputVariable<BinaryRange>> dog = new InputVariableList<>(3);
		
		dog.setVariable(0, BinaryVariable.factory(1));
		dog.setVariable(1, BinaryVariable.factory(1));
		dog.setVariable(2, BinaryVariable.factory(0));
		

		Map<InputVariableList<RangeInputVariable<BinaryRange>>, Boolean> inputData= new HashMap<>();
		
		inputData.put(cat, false);
		inputData.put(dog, true);
		

		Function function = new LineFunction(1, 0);
		WeightedSumPerceptron<RangeInputVariable<BinaryRange>> perceptron = new WeightedSumPerceptron<>(
				function, 3, 2.);

		
		WeightLearner<RangeInputVariable<BinaryRange>> learner = new WeightLearner<>(perceptron, inputData);
		
		learner.learn();
		
		assertTrue("Dog should activate", perceptron.isActive(dog));
		assertFalse("Cat shouldn't activate", perceptron.isActive(cat));
		

		System.out.println("Dobre wagi: " + perceptron.getWeights());
		
	}
	
	@Test
	public void weatherTest() {
		InputVariableList<InputVariable> day1 = new InputVariableList<>(10);
		

		day1.setVariable(0, InputVariableImpl.factory(10)); // temp
		day1.setVariable(1, InputVariableImpl.factory(8)); // temp odczuwalna
		day1.setVariable(2, InputVariableImpl.factory(13)); // pr wiatru
		day1.setVariable(3, InputVariableImpl.factory(25)); // porywy wiatru
		day1.setVariable(4, InputVariableImpl.factory(90)); // zachmurzenie
		day1.setVariable(5, InputVariableImpl.factory(0.1)); // opady
		day1.setVariable(6, InputVariableImpl.factory(1005));  // cisnienie
		day1.setVariable(7, InputVariableImpl.factory(58)); // wilgotnosc
		day1.setVariable(8, InputVariableImpl.factory(10)); // szanse opadow
		day1.setVariable(9, InputVariableImpl.factory(1));// temp w nocy
		

		InputVariableList<InputVariable> day2 = new InputVariableList<>(10);

		day2.setVariable(0, InputVariableImpl.factory(12)); // temp
		day2.setVariable(1, InputVariableImpl.factory(10)); // temp odczuwalna
		day2.setVariable(2, InputVariableImpl.factory(16)); // pr wiatru
		day2.setVariable(3, InputVariableImpl.factory(40)); // porywy wiatru
		day2.setVariable(4, InputVariableImpl.factory(15)); // zachmurzenie
		day2.setVariable(5, InputVariableImpl.factory(0.)); // opady
		day2.setVariable(6, InputVariableImpl.factory(1032));  // cisnienie
		day2.setVariable(7, InputVariableImpl.factory(47)); // wilgotnosc
		day2.setVariable(8, InputVariableImpl.factory(0)); // szanse opadow
		day2.setVariable(9, InputVariableImpl.factory(3));// temp w nocy
		

		InputVariableList<InputVariable> day3 = new InputVariableList<>(10);
		

		day3.setVariable(0, InputVariableImpl.factory(12)); // temp
		day3.setVariable(1, InputVariableImpl.factory(9)); // temp odczuwalna
		day3.setVariable(2, InputVariableImpl.factory(34)); // pr wiatru
		day3.setVariable(3, InputVariableImpl.factory(55)); // porywy wiatru
		day3.setVariable(4, InputVariableImpl.factory(90)); // zachmurzenie
		day3.setVariable(5, InputVariableImpl.factory(0.5)); // opady
		day3.setVariable(6, InputVariableImpl.factory(1019));  // cisnienie
		day3.setVariable(7, InputVariableImpl.factory(55)); // wilgotnosc
		day3.setVariable(8, InputVariableImpl.factory(50)); // szanse opadow
		day3.setVariable(9, InputVariableImpl.factory(-1));// temp w nocy
		
		Function function = new SigmoidalFunction();
		WeightedSumPerceptron<InputVariable> perceptron = new WeightedSumPerceptron<>(
				function, 10, 0.9);

		
		Map<InputVariableList<InputVariable>, Boolean> inputData= new HashMap<>();
		
		inputData.put(day1, false);
		inputData.put(day2, false);
		inputData.put(day3, true);
		
		
		WeightLearner<InputVariable> learner = new WeightLearner<>(perceptron, inputData);
		
		learner.learn();
		
		assertFalse("Day 1 shouldn't activate", perceptron.isActive(day1));
		assertFalse("Day 2 shouldn't activate", perceptron.isActive(day2));
		assertTrue("Day 3 should activate", perceptron.isActive(day3));
		
		System.out.println("Dobre wagi: " + perceptron.getWeights());
	}
	
}

package pl.mwojcik.mio.percepton.learning;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import pl.mwojcik.mio.classes.Animal;
import pl.mwojcik.mio.percepton.WeightedSumPerceptron;
import pl.mwojcik.mio.percepton.functions.LineFunction;
import pl.mwojcik.mio.percepton.functions.SigmoidalFunction;
import pl.mwojcik.mio.percepton.variables.BinaryRange;
import pl.mwojcik.mio.percepton.variables.BinaryVariable;
import pl.mwojcik.mio.percepton.variables.InputVariable;
import pl.mwojcik.mio.percepton.variables.InputVariableList;
import pl.mwojcik.mio.percepton.variables.RangeInputVariable;

public class PerceptronLayerWeightLearnerTest {

	private InputVariableList<InputVariable> cat;
	private InputVariableList<InputVariable> dog;
	private InputVariableList<InputVariable> monkey;
	private PerceptronLayerWeightLearner<InputVariable, Animal> learner;

	@Before
	public void setUp() throws Exception {

		cat = new InputVariableList<>(3);

		cat.setVariable(0, BinaryVariable.factory(1));
		cat.setVariable(1, BinaryVariable.factory(0));
		cat.setVariable(2, BinaryVariable.factory(1));

		dog = new InputVariableList<>(3);

		dog.setVariable(0, BinaryVariable.factory(1));
		dog.setVariable(1, BinaryVariable.factory(1));
		dog.setVariable(2, BinaryVariable.factory(0));

		monkey = new InputVariableList<>(3);

		monkey.setVariable(0, BinaryVariable.factory(1));
		monkey.setVariable(1, BinaryVariable.factory(1));
		monkey.setVariable(2, BinaryVariable.factory(1));

		learner = new PerceptronLayerWeightLearner<>(new ArrayList<Animal>(Arrays.asList(Animal.values())),
				new LineFunction(1, 0), 3, 2);
		
		learner = new PerceptronLayerWeightLearner<>(new ArrayList<Animal>(Arrays.asList(Animal.values())),
				new SigmoidalFunction(), 3, 0.55);

		learner.addData(cat, Animal.CAT);
		learner.addData(dog, Animal.DOG);
//		 learner.addData(monkey, Animal.MONKEY);

	}

	@Test
	public void testClassify() {
		learner.learn();

		for (WeightedSumPerceptron<InputVariable> perceptron : learner.getPerceptronLayer().getPerceptronsMap().keySet()) {
			System.out.println("Dobre wagi dla " + learner.getPerceptronLayer().getPerceptronsMap().get(perceptron) + ": "
					+ perceptron.getWeights());
		}
	}

}

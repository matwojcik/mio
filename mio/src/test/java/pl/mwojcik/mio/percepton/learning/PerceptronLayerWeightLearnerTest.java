package pl.mwojcik.mio.percepton.learning;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import pl.mwojcik.mio.classes.Animal;
import pl.mwojcik.mio.percepton.WeightedSumPerceptron;
import pl.mwojcik.mio.percepton.functions.LineFunction;
import pl.mwojcik.mio.percepton.variables.InputVariable;

public class PerceptronLayerWeightLearnerTest extends AnimalsTest {

	PerceptronLayerWeightLearner<InputVariable, Animal> learner;

	@Before
	public void setUp() throws Exception {

		initilizeAnimals();

		learner = new PerceptronLayerWeightLearner<>(new ArrayList<Animal>(Arrays.asList(Animal.values())),
				new LineFunction(1, 0), 3, 2);

//		learner = new PerceptronLayerWeightLearner<>(new ArrayList<Animal>(Arrays.asList(Animal.values())),
//				new SigmoidalFunction(), 3, 0.55);

		learner.addData(cat, Animal.CAT);
		learner.addData(dog, Animal.DOG);
		learner.addData(monkey, Animal.MONKEY);

	}

	@Test
	public void testClassify() {
		learner.learn();

		for (WeightedSumPerceptron<InputVariable> perceptron : learner.getPerceptronLayer().getPerceptronsMap()
				.keySet()) {
			System.out.println("Dobre wagi dla " + learner.getPerceptronLayer().getPerceptronsMap().get(perceptron)
					+ ": " + perceptron.getWeights());
		}
	}

}

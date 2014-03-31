package pl.mwojcik.mio.percepton.learning;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;

import pl.mwojcik.mio.classes.Animal;
import pl.mwojcik.mio.percepton.PerceptronNetwork;
import pl.mwojcik.mio.percepton.PerceptronNetwork.Builder;
import pl.mwojcik.mio.percepton.WeightedSumPerceptron;
import pl.mwojcik.mio.percepton.functions.SigmoidalFunction;
import pl.mwojcik.mio.percepton.variables.InputVariable;

public class AnimalsBackPropagationWeightLearnerTest extends AnimalsTest{

	BackPropagationWeightLearner<Animal> learner;

	private Builder<InputVariable, WeightedSumPerceptron<InputVariable>, Animal> builder;
	
	@Before
	public void setUp() throws Exception {
		
		initilizeAnimals();
		
		builder = PerceptronNetwork
				.<InputVariable, WeightedSumPerceptron<InputVariable>, Animal> builder();
		
		builder.setInsideLayers(2, 4);
		builder.setClasses(Arrays.asList(Animal.values()));
		builder.setFunction(new SigmoidalFunction());
		builder.setPerceptronFactory(WeightedSumPerceptron.factory());
		builder.setVariablesCount(3);
		
		learner = new BackPropagationWeightLearner<>(builder.build());
		
	}

	@Test
	public void testLearn() {
		learner.learn(learnData, 100000, 0.05);
		
		Collection<Animal> catResult = learner.getNetwork().classify(cat);
		assertTrue(CollectionUtils.isEqualCollection(catResult, Arrays.asList(Animal.CAT)));
		

		Collection<Animal> dogResult = learner.getNetwork().classify(dog);
		assertTrue(CollectionUtils.isEqualCollection(dogResult, Arrays.asList(Animal.DOG)));

		Collection<Animal> monkeyResult = learner.getNetwork().classify(monkey);
		assertTrue(CollectionUtils.isEqualCollection(monkeyResult, Arrays.asList(Animal.MONKEY)));
		
	}

}

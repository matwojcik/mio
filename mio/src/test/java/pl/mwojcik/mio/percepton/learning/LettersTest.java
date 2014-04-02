package pl.mwojcik.mio.percepton.learning;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;

import pl.mwojcik.mio.classes.Letter;
import pl.mwojcik.mio.classes.Point;
import pl.mwojcik.mio.image.ImageFactory;
import pl.mwojcik.mio.percepton.PerceptronNetwork;
import pl.mwojcik.mio.percepton.PerceptronNetwork.Builder;
import pl.mwojcik.mio.percepton.WeightedSumPerceptron;
import pl.mwojcik.mio.percepton.functions.SigmoidalFunction;
import pl.mwojcik.mio.percepton.learning.datafactory.BackTrainingDataFactory;
import pl.mwojcik.mio.percepton.learning.datafactory.LettersTrainingDataFactory;
import pl.mwojcik.mio.percepton.learning.datafactory.TrainingDataFactory;
import pl.mwojcik.mio.percepton.variables.InputVariable;
import pl.mwojcik.mio.percepton.variables.InputVariableImpl;
import pl.mwojcik.mio.percepton.variables.InputVariableList;

public class LettersTest {

	BackPropagationWeightLearner<Letter> learner;

	private Builder<InputVariable, WeightedSumPerceptron<InputVariable>, Letter> builder;

	private Map<InputVariableList<InputVariable>, Collection<Letter>> trainingData;

	@Before
	public void setUp() throws Exception {
		builder = PerceptronNetwork.<InputVariable, WeightedSumPerceptron<InputVariable>, Letter> builder();

		builder.setInsideLayers(7, 5);
		builder.setClasses(Arrays.asList(Letter.values()));
		builder.setFunction(new SigmoidalFunction());
		builder.setPerceptronFactory(WeightedSumPerceptron.factory());
		builder.setVariablesCount(400);

		learner = new BackPropagationWeightLearner<>(builder.build());
		TrainingDataFactory<Letter> factory = new LettersTrainingDataFactory();

		trainingData = factory.factory();
	}

	@Test
	public void testLearn() {
		learner.setLearningRate(0.12);
		BackPropagationTestUtil.performTest(learner, trainingData, 0.2);

		ImageFactory factory = new ImageFactory();
		
		try {
			InputVariableList<InputVariable> a = factory.factory(new File("./a.png"));
			InputVariableList<InputVariable> a2 = factory.factory(new File("./a2.png"));
			InputVariableList<InputVariable> c = factory.factory(new File("./c.png"));
			assertTrue(CollectionUtils.isEqualCollection(learner.getNetwork().classify(a2), Arrays.asList(Letter.A)));
			assertTrue(CollectionUtils.isEqualCollection(learner.getNetwork().classify(a), Arrays.asList(Letter.A)));
			assertTrue(CollectionUtils.isEqualCollection(learner.getNetwork().classify(c), Arrays.asList(Letter.C)));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

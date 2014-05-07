package pl.mwojcik.mio.percepton.learning;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import pl.mwojcik.mio.classes.Point;
import pl.mwojcik.mio.percepton.PerceptronNetwork;
import pl.mwojcik.mio.percepton.WeightedSumPerceptron;
import pl.mwojcik.mio.percepton.PerceptronNetwork.Builder;
import pl.mwojcik.mio.percepton.functions.SigmoidalFunction;
import pl.mwojcik.mio.percepton.learning.datafactory.BackTrainingDataFactory;
import pl.mwojcik.mio.percepton.learning.datafactory.TrainingDataFactory;
import pl.mwojcik.mio.percepton.variables.InputVariable;
import pl.mwojcik.mio.percepton.variables.InputVariableList;

public class WeightsPersistsenceUtilTest {

	BackPropagationWeightLearner<Point> learner;

	private Builder<InputVariable, WeightedSumPerceptron<InputVariable>, Point> builder;

	private Map<InputVariableList<InputVariable>, Collection<Point>> trainingData;

	@Before
	public void setUp() throws Exception {
		builder = PerceptronNetwork.<InputVariable, WeightedSumPerceptron<InputVariable>, Point> builder();

		builder.setInsideLayers(7, 5);
		builder.setClasses(Arrays.asList(Point.values()));
		builder.setFunction(new SigmoidalFunction());
		builder.setPerceptronFactory(WeightedSumPerceptron.factory());
		builder.setVariablesCount(2);

		learner = new BackPropagationWeightLearner<>(builder.build());
		TrainingDataFactory<Point> factory = new BackTrainingDataFactory();

		trainingData = factory.factory();
	}

	@Test
	public void test() {
		WeightsPersistsenceUtil.saveWeights(learner, new File("./weights/test"));
		List<List<List<Double>>> weights = learner.getWeights();
		List<List<List<Double>>> weights2 = new ArrayList<>(weights);
		weights2.get(0).get(0).set(0, 2.);
		learner.setWeights(weights2);
		WeightsPersistsenceUtil.loadWeights(learner, new File("./weights/test"));
		assertEquals(weights, learner.getWeights());
		
		
	}

}

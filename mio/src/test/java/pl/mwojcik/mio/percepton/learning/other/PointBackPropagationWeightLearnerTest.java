package pl.mwojcik.mio.percepton.learning.other;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;

import pl.mwojcik.mio.classes.Point;
import pl.mwojcik.mio.percepton.PerceptronNetwork;
import pl.mwojcik.mio.percepton.PerceptronNetwork.Builder;
import pl.mwojcik.mio.percepton.WeightedSumPerceptron;
import pl.mwojcik.mio.percepton.functions.SigmoidalFunction;
import pl.mwojcik.mio.percepton.learning.BackPropagationWeightLearner;
import pl.mwojcik.mio.percepton.learning.datafactory.TrainingDataFactory;
import pl.mwojcik.mio.percepton.variables.InputVariable;
import pl.mwojcik.mio.percepton.variables.InputVariableList;

public class PointBackPropagationWeightLearnerTest{

	BackPropagationWeightLearner<Point> learner;

	private Builder<InputVariable, WeightedSumPerceptron<InputVariable>, Point> builder;

	private Map<InputVariableList<InputVariable>, Collection<Point>> trainingData;
	
	@Before
	public void setUp() throws Exception {
		
		
		builder = PerceptronNetwork
				.<InputVariable, WeightedSumPerceptron<InputVariable>, Point> builder();
		
		builder.setInsideLayers(7, 5);
		builder.setClasses(Arrays.asList(Point.values()));
		builder.setFunction(new SigmoidalFunction());
		builder.setPerceptronFactory(WeightedSumPerceptron.factory());
		builder.setVariablesCount(2);
		
		learner = new BackPropagationWeightLearner<>(builder.build());
		TrainingDataFactory<Point> factory = new PointTrainingDataFactory();
		
		trainingData = factory.factory();
	}

	@Test
	public void testLearn() {
		learner.setLearningRate(0.1);
		learner.learn(trainingData, 100000, 0.05);
		System.out.println(learner.getWeights());
		
		for(InputVariableList<InputVariable> input: trainingData.keySet()) {
			Collection<Point> result = learner.getNetwork().classify(input);
			
			assertTrue(CollectionUtils.isEqualCollection(result, trainingData.get(input)));
		}
				
	}

}

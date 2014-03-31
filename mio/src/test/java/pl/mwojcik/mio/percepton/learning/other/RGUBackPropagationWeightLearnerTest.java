package pl.mwojcik.mio.percepton.learning.other;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;

import pl.mwojcik.mio.classes.Point;
import pl.mwojcik.mio.percepton.Perceptron;
import pl.mwojcik.mio.percepton.PerceptronLayer;
import pl.mwojcik.mio.percepton.PerceptronNetwork;
import pl.mwojcik.mio.percepton.PerceptronNetwork.Builder;
import pl.mwojcik.mio.percepton.WeightedSumPerceptron;
import pl.mwojcik.mio.percepton.functions.SigmoidalFunction;
import pl.mwojcik.mio.percepton.learning.BackPropagationWeightLearner;
import pl.mwojcik.mio.percepton.learning.datafactory.TrainingDataFactory;
import pl.mwojcik.mio.percepton.variables.InputVariable;
import pl.mwojcik.mio.percepton.variables.InputVariableList;

public class RGUBackPropagationWeightLearnerTest{

	BackPropagationWeightLearner<Point> learner;

	private Builder<InputVariable, WeightedSumPerceptron<InputVariable>, Point> builder;

	private Map<InputVariableList<InputVariable>, Collection<Point>> trainingData;
	
	@Before
	public void setUp() throws Exception {
		
		
		builder = PerceptronNetwork
				.<InputVariable, WeightedSumPerceptron<InputVariable>, Point> builder();
		
		builder.setInsideLayers(2);
		builder.setClasses(Arrays.asList(Point.A));
		builder.setFunction(new SigmoidalFunction());
		builder.setPerceptronFactory(WeightedSumPerceptron.factory());
		builder.setVariablesCount(2);
		
		learner = new BackPropagationWeightLearner<Point>(builder.build()) {
			protected void initilizeWeights() {
				int layerIndex = 0;
				int perceptronIndex = 0;
				for (PerceptronLayer<InputVariable, WeightedSumPerceptron<InputVariable>> layer : layers) {
					for (WeightedSumPerceptron<InputVariable> perceptron : layer.getPerceptrons()) {
						for (int i = 0; i < perceptron.getWeights().size(); ++i) {
							double weight = 0;
							switch(layerIndex) {
							case 0: 
									switch(perceptronIndex) {
									case 0:
										switch(i) {
										case 1: 
											weight = 0.1; break;
										case 2: 
											weight  = 0.8; break;
										}
										break;
									case 1:
										switch(i) {
										case 1: 
											weight = 0.4; break;
										case 2: 
											weight  = 0.6; break;
										}
										break;
									}
									break;
							case 1:
								switch(i) {
								case 1: weight = 0.3; break;
								case 2: weight = 0.9; break;
								}
								break;
							}
							perceptron.getWeights().set(i, i == 0 ? 0 : weight);
						}
						perceptronIndex++;
					}
					layerIndex++;
				}
			}
			
			@Override
			protected Double getExpectedValue(Perceptron<InputVariable> perceptron, Collection<Point> classes) {
				if (classes.contains(network.getOutsideLayer().getPerceptronsMap().get(perceptron))) {
					return 0.5;
				} else {
					return 0.;
				}
			}

		};
		TrainingDataFactory factory = new RGUTrainingDataFactory();		
		trainingData = factory.factory();
	}

	@Test
	public void testLearn() {
		learner.learn(trainingData, 100000, 0.05);
		
		for(InputVariableList<InputVariable> input: trainingData.keySet()) {
			Collection<Point> result = learner.getNetwork().classify(input);
			
			assertTrue(CollectionUtils.isEqualCollection(result, trainingData.get(input)));
		}
				
	}

}

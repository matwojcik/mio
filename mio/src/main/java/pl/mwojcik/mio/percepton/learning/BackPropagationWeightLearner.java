package pl.mwojcik.mio.percepton.learning;

import java.util.HashMap;
import java.util.Map;

import pl.mwojcik.mio.percepton.Perceptron;
import pl.mwojcik.mio.percepton.PerceptronNetwork;

public class BackPropagationWeightLearner {
	private PerceptronNetwork<?, ?, ?> network;

	private Map<Perceptron<?>, Double> errors = new HashMap<Perceptron<?>, Double>();
	
	private double learningRate = 0.01;
	
	private double getWeightDelta(double error, double input) {
		return learningRate * error * input;
	}
	
	private double getErrorForOutsideLayer(Perceptron<?> perceptron, double expectedValue, double actualValue) {
		return (expectedValue - actualValue) * perceptron.getFunction().runDerivative(actualValue);
	}
}

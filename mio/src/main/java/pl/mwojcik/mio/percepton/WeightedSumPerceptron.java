package pl.mwojcik.mio.percepton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.mwojcik.mio.percepton.functions.Function;
import pl.mwojcik.mio.percepton.variables.InputVariable;
import pl.mwojcik.mio.percepton.variables.InputVariableList;

public class WeightedSumPerceptron<T extends InputVariable> extends Perceptron<T> {

	private List<Double> weights;

	public WeightedSumPerceptron(Function function, int variablesCount, List<Double> weights, double activationThreshold) {
		super(function, variablesCount, activationThreshold);

		setWeights(weights);

	}

	public WeightedSumPerceptron(Function function, int variablesCount, double activationThreshold) {
		super(function, variablesCount, activationThreshold);

		setWeights(new ArrayList<>(Collections.nCopies(variablesCount + 1, 1.)));
	}

	public List<Double> getWeights() {
		return weights;
	}

	public void setWeights(List<Double> weights) {
		if (variablesCount != weights.size() - 1)
			throw new IllegalArgumentException("Given " + weights.size() + "weights, expected " + (variablesCount + 1));

		this.weights = weights;
	}

	@Override
	public double getResult(InputVariableList<?> variables) {
		double sum = weights.get(0);

		for (int i = 1; i <= variablesCount; ++i) {
			sum += weights.get(i) * variables.getVariables().get(i - 1).getValue();
		}

		return function.run(sum);
	}

	public static <V extends InputVariable> Factory<V> factory(){
		return new Factory<>();
	}
	
	private static class Factory<V extends InputVariable> implements PerceptronFactory<V, WeightedSumPerceptron<V>> {

		@Override
		public WeightedSumPerceptron<V> factory(int variablesCount, Function function, double activationThreshold) {
			return new WeightedSumPerceptron<V>(function, variablesCount, new ArrayList<>(Collections.nCopies(
					variablesCount + 1, 1.)), activationThreshold);
		}

	}
}

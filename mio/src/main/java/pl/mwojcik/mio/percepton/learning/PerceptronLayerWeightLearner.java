package pl.mwojcik.mio.percepton.learning;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import pl.mwojcik.mio.classes.PerceptronClass;
import pl.mwojcik.mio.percepton.PerceptronLayer;
import pl.mwojcik.mio.percepton.PerceptronLayer.Builder;
import pl.mwojcik.mio.percepton.WeightedSumPerceptron;
import pl.mwojcik.mio.percepton.functions.Function;
import pl.mwojcik.mio.percepton.variables.InputVariable;
import pl.mwojcik.mio.percepton.variables.InputVariableList;

public class PerceptronLayerWeightLearner<V extends InputVariable, C extends PerceptronClass> {

	private PerceptronLayer<V, WeightedSumPerceptron<V>, C> perceptronLayer;

	private Map<InputVariableList<V>, C> data = new HashMap<>();

	public PerceptronLayerWeightLearner(Collection<C> classes, Function function, int variablesCount,
			double activationThreshold) {
		super();

		Builder<V, WeightedSumPerceptron<V>, C> builder = PerceptronLayer.<V, WeightedSumPerceptron<V>, C> builder();

		for (C perceptronClass : classes) {
			builder.addPerceptron(new WeightedSumPerceptron<V>(function, variablesCount, activationThreshold),
					perceptronClass);
		}

		this.perceptronLayer = builder.build();
	}

	public PerceptronLayerWeightLearner<V, C> addData(InputVariableList<V> input, C perceptronClass) {
		data.put(input, perceptronClass);
		return this;
	}

	public void learn() {
		for(WeightedSumPerceptron<V> perceptron: perceptronLayer.getPerceptrons().keySet()) {
			learnPerceptron(perceptron);
		}
	}

	private Map<InputVariableList<V>, Boolean> getInputDataForClass(C perceptronClass) {
		Map<InputVariableList<V>, Boolean> result = new HashMap<>();

		for (InputVariableList<V> singleInput : data.keySet()) {
			boolean isThisClass = data.get(singleInput).equals(perceptronClass);
			result.put(singleInput, isThisClass);
		}

		return result;
	}

	private void learnPerceptron(WeightedSumPerceptron<V> perceptron) {
		WeightLearner<V> weightLearner = new WeightLearner<>(perceptron, getInputDataForClass(perceptronLayer
				.getPerceptrons().get(perceptron)));
		
		weightLearner.learn();
	}

	public PerceptronLayer<V, WeightedSumPerceptron<V>, C> getPerceptronLayer() {
		return perceptronLayer;
	}
}

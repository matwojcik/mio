package pl.mwojcik.mio.percepton;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import pl.mwojcik.mio.classes.PerceptronClass;
import pl.mwojcik.mio.percepton.variables.InputVariable;
import pl.mwojcik.mio.percepton.variables.InputVariableList;

public class PerceptronLayer<V extends InputVariable, P extends Perceptron<V>, C extends PerceptronClass> {
	Map<P, C> perceptrons = new HashMap<>();

	private PerceptronLayer() {

	}

	public C classify(InputVariableList<V> input) {
		for (P perceptron : perceptrons.keySet()) {
			if (perceptron.isActive(input))
				return perceptrons.get(perceptron);
		}
		
		throw new NoSuchElementException("No class for input: " + input);
	}

	public Collection<C> multipleClassify(InputVariableList<V> input) {
		Set<C> result = new HashSet<>();
		
		for (P perceptron : perceptrons.keySet()) {
			if (perceptron.isActive(input))
				result.add(perceptrons.get(perceptron));
		}
		
		return result;
	}

	public static <V extends InputVariable, P extends Perceptron<V>, C extends PerceptronClass> Builder<V, P, C> builder() {
		return new Builder<>();
	}

	public static class Builder<V extends InputVariable, P extends Perceptron<V>, C extends PerceptronClass> {
		private PerceptronLayer<V, P, C> perceptronLayer = new PerceptronLayer<>();

		public Builder<V, P, C> addPerceptron(P perceptron, C perceptronClass) {
			perceptronLayer.perceptrons.put(perceptron, perceptronClass);
			return this;
		}

		public PerceptronLayer<V, P, C> build() {
			return perceptronLayer;
		}

	}

	public Map<P, C> getPerceptrons() {
		return perceptrons;
	}

	public void putPerceptron(P perceptron, C perceptronClass) {
		perceptrons.put(perceptron, perceptronClass);
	}
}

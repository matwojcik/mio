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

public class PerceptronOutsideLayer<V extends InputVariable, P extends Perceptron<V>, C extends PerceptronClass>
		extends PerceptronLayer<V, P> {
	private Map<P, C> perceptronsMap = new HashMap<>();

	private PerceptronOutsideLayer() {

	}

	public C classify(InputVariableList<V> input) {
		for (P perceptron : perceptronsMap.keySet()) {
			if (perceptron.isActive(input))
				return perceptronsMap.get(perceptron);
		}

		throw new NoSuchElementException("No class for input: " + input);
	}

	public Collection<C> multipleClassify(InputVariableList<V> input) {
		Set<C> result = new HashSet<>();

		for (P perceptron : perceptronsMap.keySet()) {
			if (perceptron.isActive(input))
				result.add(perceptronsMap.get(perceptron));
		}

		return result;
	}

	public static <V extends InputVariable, P extends Perceptron<V>, C extends PerceptronClass> Builder<V, P, C> builder() {
		return new Builder<>();
	}

	public static class Builder<V extends InputVariable, P extends Perceptron<V>, C extends PerceptronClass> {
		private PerceptronOutsideLayer<V, P, C> perceptronLayer = new PerceptronOutsideLayer<>();

		public Builder<V, P, C> addPerceptron(P perceptron, C perceptronClass) {
			perceptronLayer.putPerceptron(perceptron, perceptronClass);
			return this;
		}

		public PerceptronOutsideLayer<V, P, C> build() {
			return perceptronLayer;
		}

	}

	public Map<P, C> getPerceptronsMap() {
		return perceptronsMap;
	}

	public void putPerceptron(P perceptron, C perceptronClass) {
		add(perceptron);
		perceptronsMap.put(perceptron, perceptronClass);
	}
}

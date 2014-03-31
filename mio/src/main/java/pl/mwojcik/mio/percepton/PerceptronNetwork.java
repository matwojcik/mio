package pl.mwojcik.mio.percepton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import pl.mwojcik.mio.classes.PerceptronClass;
import pl.mwojcik.mio.percepton.functions.Function;
import pl.mwojcik.mio.percepton.variables.InputVariable;
import pl.mwojcik.mio.percepton.variables.InputVariableImpl;
import pl.mwojcik.mio.percepton.variables.InputVariableList;

public class PerceptronNetwork<V extends InputVariable, P extends Perceptron<V>, C extends PerceptronClass> {

	private List<PerceptronLayer<V, P>> layers = new ArrayList<>();

	private PerceptronNetwork() {
		
	}
	
	public int getLayersCount() {
		return layers.size();
	}
	
	public Collection<C> classify(InputVariableList<V> input) {
		PerceptronNetworkVariables variables = calculateVariables(input);

		return getOutsideLayer().multipleClassify(variables.getInputVariables(layers.size() - 1));
	}

	public PerceptronNetworkVariables calculateVariables(InputVariableList<V> input) {
		if (input.getLength() != getInputVariableCount()) {
			throw new IllegalArgumentException("Incorrect variable count, expected " + getInputVariableCount()
					+ ", given " + input.getLength());
		}

		PerceptronNetworkVariables variables = new PerceptronNetworkVariables();

		fillInputVariables(input, variables);

		fillLayerVariables(variables);
		return variables;
	}

	public void fillLayerVariables(PerceptronNetworkVariables variables) {
		for (int layerIndex = 0; layerIndex < layers.size(); ++layerIndex) {
			PerceptronLayer<V, P> layer = layers.get(layerIndex);
			for (int perceptronIndex = 0; perceptronIndex < layer.getPerceptrons().size(); ++perceptronIndex) {
				P perceptron = layer.getPerceptrons().get(perceptronIndex);
				Double result = perceptron.getResult(variables.getInputVariables(layerIndex));
				
//				if(layerIndex == layers.size() - 1) {
//					result = perceptron.getActivationThreshold() <= result ? 1. : 0.;
//				}
				variables.setVariable(layerIndex + 1, perceptronIndex, new InputVariableImpl(result));
			}
		}
	}

	protected void fillInputVariables(InputVariableList<V> input, PerceptronNetworkVariables results) {
		for (int i = 0; i < input.getLength(); ++i) {
			results.setVariable(0, i, input.getVariables().get(i));
		}
	}

	public class PerceptronNetworkVariables {
		private List<List<InputVariable>> variables = new ArrayList<>();

		private PerceptronNetworkVariables() {
			int resultsLength = layers.size() + 1;

			for (int i = 0; i < resultsLength; ++i) {
				int variablesInLayerCount = 0;
				if (i == resultsLength - 1) {
					variablesInLayerCount = layers.get(i - 1).getPerceptrons().size();
				} else {
					variablesInLayerCount = layers.get(i).getPerceptrons().get(0).getVariablesCount();
				}
				ArrayList<InputVariable> variablesInLayer = new ArrayList<InputVariable>(
						Collections.<InputVariable> nCopies(variablesInLayerCount, null));
				variables.add(variablesInLayer);
			}
		}

		public InputVariableList<InputVariable> getInputVariables(int layer) {
			return new InputVariableList<InputVariable>(variables.get(layer));
		}
		
		public InputVariable getVariable(int outputLayer, int index) {
			return variables.get(outputLayer).get(index);
		}

		public void setVariable(int outputLayer, int index, InputVariable variable) {
			variables.get(outputLayer).set(index, variable);
		}

		@Override
		public String toString() {
			return "PerceptronNetworkVariables [variables=" + variables + "]";
		}
		
		
	}

	public int getInputVariableCount() {
		return layers.get(0).getPerceptrons().get(0).getVariablesCount();
	}

	public static class Builder<V extends InputVariable, P extends Perceptron<V>, C extends PerceptronClass> {
		private Function function;
		private int[] layers;
		private Collection<C> classes;
		private PerceptronFactory<V, P> perceptronFactory;
		private Integer variablesCount;

		public Builder() {

		}

		public void setVariablesCount(int variablesCount) {
			this.variablesCount = variablesCount;
		}

		public Builder<V, P, C> setFunction(Function function) {
			this.function = function;
			return this;
		}

		/**
		 * Creates network with as many inside layers as arguments is passed.
		 * 
		 * @param layersPerceptronsAmount
		 *            Each argument equals amount of perceptrons in responding
		 *            layer
		 * @return
		 */
		public Builder<V, P, C> setInsideLayers(int... layersPerceptronsAmount) {
			for (int i : layersPerceptronsAmount) {
				if (i <= 0)
					throw new IllegalArgumentException("Illegal perceptrons amount: " + i);
			}
			layers = layersPerceptronsAmount;
			return this;
		}

		public Builder<V, P, C> setClasses(Collection<C> classes) {
			this.classes = classes;
			return this;
		}

		public void setPerceptronFactory(PerceptronFactory<V, P> perceptronFactory) {
			this.perceptronFactory = perceptronFactory;
		}

		public PerceptronNetwork<V, P, C> build() {
			PerceptronNetwork<V, P, C> network = new PerceptronNetwork<>();

			if (perceptronFactory == null || variablesCount == null || classes == null || function == null) {
				throw new IllegalArgumentException();
			}

			int previousCount = 0;
			if (layers != null) {
				for (int layerPerceptronsAmount : layers) {
					PerceptronLayer<V, P> layer = new PerceptronLayer<>();
					int variables = previousCount == 0 ? variablesCount : previousCount;

					for (int i = 0; i < layerPerceptronsAmount; ++i) {

						layer.add(perceptronFactory.factory(variables, function,
								function.getDefaultActivationThreshold()));

					}

					network.layers.add(layer);
					previousCount = layerPerceptronsAmount;
				}
			}

			pl.mwojcik.mio.percepton.PerceptronOutsideLayer.Builder<V, P, C> builder = PerceptronOutsideLayer
					.<V, P, C> builder();

			int variables = previousCount == 0 ? variablesCount : previousCount;
			for (C perceptronClass : classes) {
				builder.addPerceptron(
						perceptronFactory.factory(variables, function, function.getDefaultActivationThreshold()),
						perceptronClass);
			}

			network.layers.add(builder.build());

			return network;
		}

	}

	public static <V extends InputVariable, P extends Perceptron<V>, C extends PerceptronClass> Builder<V, P, C> builder() {
		return new Builder<>();
	}

	@SuppressWarnings("unchecked")
	public PerceptronOutsideLayer<InputVariable, Perceptron<InputVariable>, C> getOutsideLayer() {
		return (PerceptronOutsideLayer<InputVariable, Perceptron<InputVariable>, C>) layers.get(layers.size() - 1);
	}

	public List<PerceptronLayer<V, P>> getLayers() {
		return Collections.unmodifiableList(layers);
	}
	
}

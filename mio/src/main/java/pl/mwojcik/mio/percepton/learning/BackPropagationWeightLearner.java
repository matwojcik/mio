package pl.mwojcik.mio.percepton.learning;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import pl.mwojcik.mio.classes.PerceptronClass;
import pl.mwojcik.mio.percepton.Perceptron;
import pl.mwojcik.mio.percepton.PerceptronLayer;
import pl.mwojcik.mio.percepton.PerceptronNetwork;
import pl.mwojcik.mio.percepton.PerceptronNetwork.PerceptronNetworkVariables;
import pl.mwojcik.mio.percepton.WeightedSumPerceptron;
import pl.mwojcik.mio.percepton.variables.InputVariable;
import pl.mwojcik.mio.percepton.variables.InputVariableList;

public class BackPropagationWeightLearner<C extends PerceptronClass> {
	protected PerceptronNetwork<InputVariable, WeightedSumPerceptron<InputVariable>, C> network;

	protected double learningRate = 0.2;

	protected List<PerceptronLayer<InputVariable, WeightedSumPerceptron<InputVariable>>> layers;

	public BackPropagationWeightLearner(
			PerceptronNetwork<InputVariable, WeightedSumPerceptron<InputVariable>, C> network) {
		super();
		this.network = network;
	}

	protected void initilizeWeights() {
		for (PerceptronLayer<InputVariable, WeightedSumPerceptron<InputVariable>> layer : layers) {
			for (WeightedSumPerceptron<InputVariable> perceptron : layer.getPerceptrons()) {
				for (int i = 0; i < perceptron.getWeights().size(); ++i) {
					perceptron.getWeights().set(i, i == 0 ? 1 : Math.random() * 2 - 1);
				}
			}
		}
	}

	public void learn(Map<InputVariableList<InputVariable>, Collection<C>> data) {
		learn(data, 100000, 0.05);
	}

	public void learn(Map<InputVariableList<InputVariable>, Collection<C>> data, int iterationCount, double maxError) {

		layers = network.getLayers();

		initilizeWeights();

		Double totalError = 0.;
		int iterationCounter = 0;
		do {
			totalError = 0.;
			int learned = 0;

			for (InputVariableList<InputVariable> input : data.keySet()) {
				PerceptronNetworkVariables variables = network.calculateVariables(input);

				List<Double> errorsForOutsideLayer = getErrorsForOutsideLayer(variables, data.get(input));
				modifyWeightsForOutsideLayer(variables, errorsForOutsideLayer);

				modifyWeightsForInsideLayers(variables, errorsForOutsideLayer);
				if (isCorrectlyClassified(data, input)) {
					learned++;
				}

				totalError = calculateErrorAfterLearningProcess(data, totalError, input);
			}

			logLearningInfo(data, totalError, learned);
			// changeLearningRate(counter);
		} while (++iterationCounter < iterationCount && totalError > maxError);

	}

	private void modifyWeightsForInsideLayers(PerceptronNetworkVariables variables, List<Double> errorsForOutsideLayer) {

		List<Double> errorsOfFollowingtLayer = errorsForOutsideLayer;
		for (int layerIndex = layers.size() - 2; layerIndex >= 0; layerIndex--) {
			List<Double> errorsForCurrentLayer = getErrorsForInsideLayer(layerIndex, variables, errorsOfFollowingtLayer);
			modifyWeightsForLayer(layerIndex, variables, errorsForCurrentLayer);

			errorsOfFollowingtLayer = errorsForCurrentLayer;

		}
	}

	private void logLearningInfo(Map<InputVariableList<InputVariable>, Collection<C>> data, Double totalError,
			int learned) {
		System.out.println("Total error: " + totalError + ", learned = " + learned / (1. * data.size()));
	}

	private Double calculateErrorAfterLearningProcess(Map<InputVariableList<InputVariable>, Collection<C>> data,
			Double totalError, InputVariableList<InputVariable> input) {
		PerceptronNetworkVariables variablesAfterLearning = network.calculateVariables(input);

		for (Double error : getErrorsForOutsideLayer(variablesAfterLearning, data.get(input))) {
			totalError += Math.abs(error);
		}
		return totalError;
	}

	private boolean isCorrectlyClassified(Map<InputVariableList<InputVariable>, Collection<C>> data,
			InputVariableList<InputVariable> input) {
		return CollectionUtils.isEqualCollection(network.classify(input), data.get(input));
	}

	private void changeLearningRate(int counter) {
		switch (counter) {
		case 100:
			learningRate = 0.3;
			break;
		case 1000:
			learningRate = 4;
			break;
		case 10000:
			learningRate = 8;
			break;
		case 100000:
			learningRate = 0.5;
			break;
		case 1000000:
			learningRate = 0.25;
			break;
		case 10000000:
			learningRate = 0.05;
			break;
		}
	}

	public void modifyWeightsForLayer(int layerIndex, PerceptronNetworkVariables variables, List<Double> errors) {
		List<WeightedSumPerceptron<InputVariable>> perceptronsOfLayer = layers.get(layerIndex).getPerceptrons();

		for (int perceptronIndex = 0; perceptronIndex < perceptronsOfLayer.size(); ++perceptronIndex) {
			WeightedSumPerceptron<InputVariable> perceptron = perceptronsOfLayer.get(perceptronIndex);
			modifyWeights(perceptron, errors.get(perceptronIndex), variables.getInputVariables(layerIndex));
		}
	}

	public void modifyWeightsForOutsideLayer(PerceptronNetworkVariables variables, List<Double> errorsForOutsideLayer) {
		for (int i = 0; i < network.getOutsideLayer().getPerceptrons().size(); ++i) {
			WeightedSumPerceptron<InputVariable> perceptron = (WeightedSumPerceptron<InputVariable>) network
					.getOutsideLayer().getPerceptrons().get(i);

			modifyWeights(perceptron, errorsForOutsideLayer.get(i), variables.getInputVariables(layers.size() - 1));
		}
	}

	private void modifyWeights(WeightedSumPerceptron<InputVariable> perceptron, double error,
			InputVariableList<InputVariable> inputVariables) {
		for (int i = 0; i < inputVariables.getVariables().size(); i++) {
			double weightDelta = getWeightDelta(error, inputVariables.getVariables().get(i).getValue());
			double currentWeight = perceptron.getWeights().get(i + 1);
			perceptron.getWeights().set(i + 1, currentWeight + weightDelta);
		}
	}

	private double getWeightDelta(double error, double input) {
		return learningRate * error * input;
	}

	private List<Double> getErrorsForOutsideLayer(PerceptronNetworkVariables variables, Collection<C> classes) {
		List<Double> errors = new ArrayList<>();
		for (int i = 0; i < network.getOutsideLayer().getPerceptrons().size(); ++i) {
			Perceptron<InputVariable> perceptron = network.getOutsideLayer().getPerceptrons().get(i);

			double outputValue = variables.getVariable(network.getLayersCount(), i).getValue();
			errors.add(getErrorForOutsideLayerPerceptron(perceptron, getExpectedValue(perceptron, classes), outputValue));
		}
		return errors;
	}

	protected Double getExpectedValue(Perceptron<InputVariable> perceptron, Collection<C> classes) {
		if (classes.contains(network.getOutsideLayer().getPerceptronsMap().get(perceptron))) {
			return 1.;
		} else {
			return 0.;
		}
	}

	private double getErrorForOutsideLayerPerceptron(Perceptron<?> perceptron, double expectedValue, double outputValue) {
		return (expectedValue - outputValue) * perceptron.getFunction().runDerivative(outputValue);
	}

	/**
	 * Returns weights from layer's perceptrons for given perceptron index
	 * 
	 * @param layer
	 * @param perceptronIndex
	 * @return
	 */
	private List<Double> getWeights(PerceptronLayer<InputVariable, WeightedSumPerceptron<InputVariable>> layer,
			int perceptronIndex) {
		List<Double> result = new ArrayList<>();

		for (WeightedSumPerceptron<InputVariable> perceptron : layer.getPerceptrons()) {
			result.add(perceptron.getWeights().get(perceptronIndex + 1));
		}

		return result;
	}

	/**
	 * 
	 * @param layerIndex
	 * @param variables
	 * @param errors
	 *            Errors for layer layerIndex+1
	 * @return
	 */
	private List<Double> getErrorsForInsideLayer(int layerIndex, PerceptronNetworkVariables variables,
			List<Double> errors) {
		List<Double> result = new ArrayList<>();
		PerceptronLayer<InputVariable, WeightedSumPerceptron<InputVariable>> layer = layers.get(layerIndex);

		for (int i = 0; i < layer.getPerceptrons().size(); ++i) {
			WeightedSumPerceptron<InputVariable> perceptron = layer.getPerceptrons().get(i);
			double outputValue = variables.getVariable(layerIndex + 1, i).getValue();
			List<Double> weights = getWeights(layers.get(layerIndex + 1), i);
			result.add(getErrorForInsideLayerPerceptron(perceptron, outputValue, errors, weights));
		}

		return result;
	}

	/**
	 * 
	 * @param perceptron
	 * @param outputValue
	 *            Value passed to next layer
	 * @param errors
	 *            Errors from next layer
	 * @param weights
	 *            Weights from next layer
	 * @return
	 */
	private double getErrorForInsideLayerPerceptron(Perceptron<InputVariable> perceptron, double outputValue,
			List<Double> errors, List<Double> weights) {
		double derivative = perceptron.getFunction().runDerivative(outputValue);

		double sum = 0;

		for (int i = 0; i < errors.size(); i++) {
			sum += errors.get(i) * weights.get(i);
		}

		return derivative * sum;
	}

	public PerceptronNetwork<InputVariable, WeightedSumPerceptron<InputVariable>, C> getNetwork() {
		return network;
	}

	public void setLearningRate(double learningRate) {
		this.learningRate = learningRate;
	}

	public List<List<List<Double>>> getWeights() {
		List<List<List<Double>>> result = new ArrayList<>();

		for (PerceptronLayer<InputVariable, WeightedSumPerceptron<InputVariable>> layer : layers) {
			List<List<Double>> perceptronWeights = new ArrayList<>();

			for (WeightedSumPerceptron<InputVariable> perceptron : layer.getPerceptrons()) {
				perceptronWeights.add(perceptron.getWeights());
			}
			result.add(perceptronWeights);
		}

		return result;
	}

}

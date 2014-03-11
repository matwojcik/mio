package pl.mwojcik.mio.percepton.learning;

import java.util.List;
import java.util.Map;

import pl.mwojcik.mio.percepton.WeightedSumPerceptron;
import pl.mwojcik.mio.percepton.variables.InputVariable;
import pl.mwojcik.mio.percepton.variables.InputVariableList;

public class WeightLearner<T extends InputVariable> {
	private WeightedSumPerceptron<T> perceptron;
	private Map<InputVariableList<T>, Boolean> inputData;
	
	public WeightLearner(WeightedSumPerceptron<T> perceptron, Map<InputVariableList<T>, Boolean> inputData) {
		super();
		this.perceptron = perceptron;
		this.inputData = inputData;
	}
	
	public void learn() {
		List<Double> weights = perceptron.getWeights();
		
		for(int i = 0; i<weights.size(); ++i) {
			weights.set(i, i==0?0:1.);
		}
		
		boolean allWeigthsSet = false;
		
		while(!allWeigthsSet) {
			allWeigthsSet = true;
			for(InputVariableList<T> element: inputData.keySet()) {
		
				boolean isActive = perceptron.isActive(element);
				boolean shouldActivate = inputData.get(element);
				if(!isActive && shouldActivate) {
					modifyWeights(element, 1.1);
					allWeigthsSet = false;
					break;
				}
				else if(isActive && !shouldActivate) {
					modifyWeights(element, 0.9);
					allWeigthsSet = false;
					break;
				}
			}
		}
	}

	private void modifyWeights(InputVariableList<T> element, double multiplier) {
		for(int i = 0; i < element.getLength(); ++i) {
			if(element.getVariables().get(i).getValue() != 0) {
				perceptron.getWeights().set(i+1, perceptron.getWeights().get(i+1)*multiplier);
				
			}
		}
	}	
	
}

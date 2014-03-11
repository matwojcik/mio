package pl.mwojcik.mio.percepton;

import pl.mwojcik.mio.percepton.functions.Function;
import pl.mwojcik.mio.percepton.variables.InputVariable;
import pl.mwojcik.mio.percepton.variables.InputVariableList;

public abstract class Perceptron<T extends InputVariable> {
	protected double activationThreshold;
	protected Function function;
	protected int variablesCount;
	
	public Perceptron(Function function, int variablesCount, double activationThreshold) {
		super();
		this.activationThreshold = activationThreshold;
		this.function = function;
		this.variablesCount = variablesCount;
	}

	public double getActivationThreshold() {
		return activationThreshold;
	}

	public Function getFunction() {
		return function;
	}

	public boolean isActive(InputVariableList<T> variables) {
		if(variablesCount != variables.getLength())
			throw new IllegalArgumentException("Given " + variables.getLength() + "variables, expected " + variablesCount);
		
		return getResult(variables) >= activationThreshold;
	}
	
	public abstract double getResult(InputVariableList<?> variables);
	
}

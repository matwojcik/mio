package pl.mwojcik.mio.percepton;

import pl.mwojcik.mio.percepton.functions.Function;
import pl.mwojcik.mio.percepton.variables.InputVariable;

public interface PerceptronFactory<V extends InputVariable, P extends Perceptron<V>> {
	public P factory(int variablesCount, Function function, double activationThreshold);
}

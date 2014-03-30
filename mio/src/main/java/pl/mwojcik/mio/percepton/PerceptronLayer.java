package pl.mwojcik.mio.percepton;

import java.util.ArrayList;
import java.util.List;

import pl.mwojcik.mio.percepton.variables.InputVariable;

public class PerceptronLayer<V extends InputVariable, P extends Perceptron<V>> {

	private List<P> perceptrons = new ArrayList<>();

	public PerceptronLayer() {

	}

	public PerceptronLayer(List<P> perceptrons) {
		super();
		this.perceptrons = perceptrons;
	}

	public PerceptronLayer<V, P> add(P perceptron) {
		if (!perceptrons.contains(perceptron))
			perceptrons.add(perceptron);
		return this;
	}

	public List<P> getPerceptrons() {
		return perceptrons;
	}

	public void setPerceptrons(List<P> perceptrons) {
		this.perceptrons = perceptrons;
	}

}

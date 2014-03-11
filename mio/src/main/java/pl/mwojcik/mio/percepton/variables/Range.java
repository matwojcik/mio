package pl.mwojcik.mio.percepton.variables;

import java.util.Set;

public class Range {
	protected Set<Double> acceptableValues;

	public Range(Set<Double> acceptableValues) {
		super();
		this.acceptableValues = acceptableValues;
	}

	public Set<Double> getAcceptableValues() {
		return acceptableValues;
	}
	
	public boolean isCorrectValue(Double value) {
		return acceptableValues.contains(value);
	}
}

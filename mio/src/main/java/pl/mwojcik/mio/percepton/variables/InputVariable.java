package pl.mwojcik.mio.percepton.variables;

public interface InputVariable {
	public void setValue(double value) throws IllegalArgumentException;
	public double getValue();
}

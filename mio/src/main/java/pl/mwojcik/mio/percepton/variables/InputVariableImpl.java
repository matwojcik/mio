package pl.mwojcik.mio.percepton.variables;

public class InputVariableImpl implements InputVariable {

	private double value;
	
	public InputVariableImpl(double value) {
		super();
		this.value = value;
	}
	
	public static InputVariable factory(double value) {
		return new InputVariableImpl(value);
	}

	@Override
	public void setValue(double value) throws IllegalArgumentException {
		this.value = value;
	}

	@Override
	public double getValue() {
		return value;
	}

}

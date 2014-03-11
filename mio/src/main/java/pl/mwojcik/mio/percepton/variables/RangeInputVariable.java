package pl.mwojcik.mio.percepton.variables;

public class RangeInputVariable<T extends Range> extends InputVariableImpl implements InputVariable {

	protected T range;
	
	public RangeInputVariable(T range, double value) {
		super(value);
		this.range = range;
		setValue(value);
	}

	@Override
	public void setValue(double value) throws IllegalArgumentException {
		if(range.isCorrectValue(value))
			super.setValue(value);
		else 
			throw new IllegalArgumentException("Illegal input variable: " + value);
	}


}

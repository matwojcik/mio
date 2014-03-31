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

	public static InputVariableList<InputVariable> asList(double... variables) {
		 InputVariableList<InputVariable> result =  new InputVariableList<>(variables.length);
		 
		 for(int i = 0; i < variables.length; ++i) {
			 result.setVariable(i, new InputVariableImpl(variables[i]));
		 }
		 
		 return result;
	}
	
	@Override
	public void setValue(double value) throws IllegalArgumentException {
		this.value = value;
	}

	@Override
	public double getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "InputVariableImpl [value=" + value + "]";
	}

}

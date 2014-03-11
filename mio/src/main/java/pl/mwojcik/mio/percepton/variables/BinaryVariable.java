package pl.mwojcik.mio.percepton.variables;

public class BinaryVariable extends RangeInputVariable<BinaryRange> {

	public BinaryVariable(double value) {
		super(BinaryRange.getInstance(), value);
	}
	
	public static BinaryVariable factory(double value) {
		return new BinaryVariable(value); 
	}

}

package pl.mwojcik.mio.percepton.functions;

public class LineFunction implements Function {

	private double a, b;
	
	public LineFunction(double a, double b) {
		super();
		this.a = a;
		this.b = b;
	}

	@Override
	public double run(double input) {
		return a * input + b;
	}

}

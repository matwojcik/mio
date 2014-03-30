package pl.mwojcik.mio.percepton.functions;

public class SigmoidalFunction implements Function {

	@Override
	public double run(double input) {
		 return 1/( 1 + Math.pow(Math.E,(-1*input)));
	}

	@Override
	public double getDefaultActivationThreshold() {
		return 0.55;
	}

	@Override
	public double runDerivative(double input) {
		return input * (1 - input);
	}

}

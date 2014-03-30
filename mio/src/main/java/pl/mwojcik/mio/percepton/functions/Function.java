package pl.mwojcik.mio.percepton.functions;

public interface Function {
	public double run(double input);
	public double runDerivative(double input);
	public double getDefaultActivationThreshold();
}

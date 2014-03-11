package pl.mwojcik.mio.percepton.variables;

import java.util.HashSet;

public class BinaryRange extends Range {

	private static BinaryRange instance;
	
	public static BinaryRange getInstance() {
		if(instance == null) {
			instance = new BinaryRange();
		}
		return instance;
	}



	private BinaryRange() {
		super(new HashSet<Double>());
		acceptableValues.add(0.);
		acceptableValues.add(1.);
	}

}

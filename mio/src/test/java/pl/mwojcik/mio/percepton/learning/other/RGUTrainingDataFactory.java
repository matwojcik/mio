package pl.mwojcik.mio.percepton.learning.other;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import pl.mwojcik.mio.classes.Point;
import pl.mwojcik.mio.percepton.learning.datafactory.TrainingDataFactory;
import pl.mwojcik.mio.percepton.variables.InputVariable;
import pl.mwojcik.mio.percepton.variables.InputVariableImpl;
import pl.mwojcik.mio.percepton.variables.InputVariableList;

public class RGUTrainingDataFactory implements TrainingDataFactory {

	/* (non-Javadoc)
	 * @see pl.mwojcik.mio.percepton.learning.TrainingDataFactory#factory()
	 */
	@Override
	public Map<InputVariableList<InputVariable>, Collection<Point>> factory() {
		
		Map<InputVariableList<InputVariable>, Collection<Point>> trainingData = new HashMap<>();
		
		trainingData.put(InputVariableImpl.asList(0.35, 0.9), Arrays.asList(Point.A));
		trainingData.put(InputVariableImpl.asList(1, 1), Arrays.asList(Point.A));
		
		return trainingData;
	}

}
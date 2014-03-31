package pl.mwojcik.mio.percepton.learning.datafactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import pl.mwojcik.mio.classes.Point;
import pl.mwojcik.mio.percepton.variables.InputVariable;
import pl.mwojcik.mio.percepton.variables.InputVariableImpl;
import pl.mwojcik.mio.percepton.variables.InputVariableList;

public class SquareTrainingDataFactory implements TrainingDataFactory {

	/* (non-Javadoc)
	 * @see pl.mwojcik.mio.percepton.learning.TrainingDataFactory#factory()
	 */
	@Override
	public Map<InputVariableList<InputVariable>, Collection<Point>> factory() {
		
		Map<InputVariableList<InputVariable>, Collection<Point>> trainingData = new HashMap<>();
		
		trainingData.put(InputVariableImpl.asList(0, 1, 1, 0), Arrays.asList(Point.B));
		trainingData.put(InputVariableImpl.asList(1, 0, 0, 1), Arrays.asList(Point.A));
		trainingData.put(InputVariableImpl.asList(1, 1, 0, 0), Arrays.asList(Point.A, Point.B));
		
		return trainingData;
	}

}
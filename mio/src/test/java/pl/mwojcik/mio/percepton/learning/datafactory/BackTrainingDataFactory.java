package pl.mwojcik.mio.percepton.learning.datafactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import pl.mwojcik.mio.classes.Point;
import pl.mwojcik.mio.percepton.variables.InputVariable;
import pl.mwojcik.mio.percepton.variables.InputVariableImpl;
import pl.mwojcik.mio.percepton.variables.InputVariableList;

public class BackTrainingDataFactory implements TrainingDataFactory<Point> {

	/* (non-Javadoc)
	 * @see pl.mwojcik.mio.percepton.learning.TrainingDataFactory#factory()
	 */
	@Override
	public Map<InputVariableList<InputVariable>, Collection<Point>> factory() {
		
		Map<InputVariableList<InputVariable>, Collection<Point>> trainingData = new HashMap<>();
		
		trainingData.put(InputVariableImpl.asList(2, 2), Arrays.asList(Point.A));
		trainingData.put(InputVariableImpl.asList(4, 4), Arrays.asList(Point.A));
		trainingData.put(InputVariableImpl.asList(6, 6), Arrays.asList(Point.A));

		trainingData.put(InputVariableImpl.asList(4, 3), Arrays.asList(Point.B));
		trainingData.put(InputVariableImpl.asList(2, 5), Arrays.asList(Point.B));
		trainingData.put(InputVariableImpl.asList(5, 1), Arrays.asList(Point.B));
		

		trainingData.put(InputVariableImpl.asList(1, 3), Arrays.asList(Point.C));
		trainingData.put(InputVariableImpl.asList(8, 4), Arrays.asList(Point.C));
		trainingData.put(InputVariableImpl.asList(4, 7), Arrays.asList(Point.C));
		
		return trainingData;
	}

}
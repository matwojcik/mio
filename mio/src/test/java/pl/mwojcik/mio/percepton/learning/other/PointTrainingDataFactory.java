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

public class PointTrainingDataFactory implements TrainingDataFactory {

	/* (non-Javadoc)
	 * @see pl.mwojcik.mio.percepton.learning.TrainingDataFactory#factory()
	 */
	@Override
	public Map<InputVariableList<InputVariable>, Collection<Point>> factory() {
		
		Map<InputVariableList<InputVariable>, Collection<Point>> trainingData = new HashMap<>();
		
		trainingData.put(InputVariableImpl.asList(0, 0), Arrays.asList(Point.A));
		trainingData.put(InputVariableImpl.asList(5, 5), Arrays.asList(Point.A));
		trainingData.put(InputVariableImpl.asList(5, 15), Arrays.asList(Point.A));
		trainingData.put(InputVariableImpl.asList(15, 5), Arrays.asList(Point.A));
	//	trainingData.put(InputVariableImpl.asList(15, 20), Arrays.asList(Point.A));

		trainingData.put(InputVariableImpl.asList(25, 25), Arrays.asList(Point.B));
		trainingData.put(InputVariableImpl.asList(35, 5), Arrays.asList(Point.B));
		trainingData.put(InputVariableImpl.asList(40, 20), Arrays.asList(Point.B));
		trainingData.put(InputVariableImpl.asList(70, 25), Arrays.asList(Point.B));
		

		trainingData.put(InputVariableImpl.asList(20, 45), Arrays.asList(Point.C));
		trainingData.put(InputVariableImpl.asList(20, 50), Arrays.asList(Point.C));
		trainingData.put(InputVariableImpl.asList(50, 40), Arrays.asList(Point.C));
		trainingData.put(InputVariableImpl.asList(60, 50), Arrays.asList(Point.C));
		
		return trainingData;
	}

}
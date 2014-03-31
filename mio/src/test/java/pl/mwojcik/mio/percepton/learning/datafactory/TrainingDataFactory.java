package pl.mwojcik.mio.percepton.learning.datafactory;

import java.util.Collection;
import java.util.Map;

import pl.mwojcik.mio.classes.Point;
import pl.mwojcik.mio.percepton.variables.InputVariable;
import pl.mwojcik.mio.percepton.variables.InputVariableList;

public interface TrainingDataFactory {

	Map<InputVariableList<InputVariable>, Collection<Point>> factory();

}
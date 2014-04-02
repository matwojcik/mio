package pl.mwojcik.mio.percepton.learning.datafactory;

import java.util.Collection;
import java.util.Map;

import pl.mwojcik.mio.classes.PerceptronClass;
import pl.mwojcik.mio.percepton.variables.InputVariable;
import pl.mwojcik.mio.percepton.variables.InputVariableList;

public interface TrainingDataFactory<C extends PerceptronClass> {

	Map<InputVariableList<InputVariable>, Collection<C>> factory();

}
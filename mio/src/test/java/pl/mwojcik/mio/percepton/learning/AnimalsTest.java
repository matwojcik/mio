package pl.mwojcik.mio.percepton.learning;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import pl.mwojcik.mio.classes.Animal;
import pl.mwojcik.mio.percepton.variables.BinaryVariable;
import pl.mwojcik.mio.percepton.variables.InputVariable;
import pl.mwojcik.mio.percepton.variables.InputVariableList;

abstract public class AnimalsTest {

	protected InputVariableList<InputVariable> cat;
	protected InputVariableList<InputVariable> dog;
	protected InputVariableList<InputVariable> monkey;
	protected Map<InputVariableList<InputVariable>, Collection<Animal>> learnData;

	public AnimalsTest() {
		super();
	}

	public void initilizeAnimals() {
		cat = new InputVariableList<>(3);

		cat.setVariable(0, BinaryVariable.factory(1));
		cat.setVariable(1, BinaryVariable.factory(0));
		cat.setVariable(2, BinaryVariable.factory(1));

		dog = new InputVariableList<>(3);

		dog.setVariable(0, BinaryVariable.factory(1));
		dog.setVariable(1, BinaryVariable.factory(1));
		dog.setVariable(2, BinaryVariable.factory(0));

		monkey = new InputVariableList<>(3);

		monkey.setVariable(0, BinaryVariable.factory(1));
		monkey.setVariable(1, BinaryVariable.factory(1));
		monkey.setVariable(2, BinaryVariable.factory(1));

		learnData = new HashMap<>();

		learnData.put(cat, Arrays.<Animal>asList(Animal.CAT));
		learnData.put(dog, Arrays.<Animal>asList(Animal.DOG));
		learnData.put(monkey, Arrays.<Animal>asList(Animal.MONKEY));
	}

}
package pl.mwojcik.mio.percepton.learning.datafactory;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import pl.mwojcik.mio.classes.Letter;
import pl.mwojcik.mio.classes.Point;
import pl.mwojcik.mio.image.ImageFactory;
import pl.mwojcik.mio.percepton.variables.InputVariable;
import pl.mwojcik.mio.percepton.variables.InputVariableImpl;
import pl.mwojcik.mio.percepton.variables.InputVariableList;

public class LettersTrainingDataFactory implements TrainingDataFactory<Letter> {

	
	
	protected final String PATH = "./letters";

	/* (non-Javadoc)
	 * @see pl.mwojcik.mio.percepton.learning.TrainingDataFactory#factory()
	 */
	@Override
	public Map<InputVariableList<InputVariable>, Collection<Letter>> factory() {
		
		Map<InputVariableList<InputVariable>, Collection<Letter>> trainingData = new HashMap<>();
		ImageFactory factory = new ImageFactory();
		File directory = new File(PATH);
		
		for(File file: directory.listFiles()) {
			if(!file.isDirectory()) {
				Letter letter = Letter.valueOf(file.getName().substring(0, 1).toUpperCase());
				try {
					InputVariableList<InputVariable> variables = factory.factory(file);
					trainingData.put(variables, Arrays.asList(letter));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		return trainingData;
	}

}
package pl.mwojcik.mio.percepton.learning;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.List;

public class WeightsPersistsenceUtil {

	public static void saveWeights(BackPropagationWeightLearner<?> learner, File file) {
		OutputStream outputStream = null;
		ObjectOutputStream output = null;
		try {
			file.createNewFile();
			outputStream = new FileOutputStream(file);
			output = new ObjectOutputStream(new BufferedOutputStream(outputStream));

			output.writeObject(learner.getWeights());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (output != null) {
					output.close();
					outputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static void loadWeights(BackPropagationWeightLearner<?> learner, File file) {
		InputStream inputStream = null;
		ObjectInput input = null;

		try {
			inputStream = new FileInputStream(file);
			input = new ObjectInputStream(new BufferedInputStream(inputStream));

			List<List<List<Double>>> weights = (List<List<List<Double>>>) input.readObject();
			
			learner.setWeights(weights);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (input != null) {
					input.close();
					inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}

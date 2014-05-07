package pl.mwojcik.mio.percepton.learning;

import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import pl.mwojcik.mio.classes.PerceptronClass;
import pl.mwojcik.mio.percepton.variables.InputVariable;
import pl.mwojcik.mio.percepton.variables.InputVariableList;

public class BackPropagationTestUtil {

	private BackPropagationTestUtil() {
	}

	/**
	 * Runs learning process, prints learned weights and checks if all training data is correct recognized
	 * @param learner
	 * @param trainingData
	 */
	public static <C extends PerceptronClass> void performTest(BackPropagationWeightLearner<C> learner,
			Map<InputVariableList<InputVariable>, Collection<C>> trainingData) {
				performTest(learner, trainingData, 0.05);
			}

	/**
	 * Runs learning process, prints learned weights and checks if all training data is correct recognized
	 * @param learner
	 * @param trainingData
	 * @param maxError TODO
	 */
	public static <C extends PerceptronClass> void performTest(BackPropagationWeightLearner<C> learner,
			Map<InputVariableList<InputVariable>, Collection<C>> trainingData, double maxError) {
		learner.learn(trainingData, 1000000, maxError);
		System.out.println(learner.getWeights());
		
		testLearnerOnData(learner, trainingData);
	}

	public static <C extends PerceptronClass> void testLearnerOnData(BackPropagationWeightLearner<C> learner,
			Map<InputVariableList<InputVariable>, Collection<C>> trainingData) {
		for(InputVariableList<InputVariable> input: trainingData.keySet()) {
			Collection<C> result = learner.getNetwork().classify(input);
			
			assertTrue(CollectionUtils.isEqualCollection(result, trainingData.get(input)));
		}
	}
}

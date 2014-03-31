package pl.mwojcik.mio.percepton;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import pl.mwojcik.mio.classes.Animal;
import pl.mwojcik.mio.percepton.PerceptronNetwork.Builder;
import pl.mwojcik.mio.percepton.functions.SigmoidalFunction;
import pl.mwojcik.mio.percepton.variables.BinaryVariable;
import pl.mwojcik.mio.percepton.variables.InputVariable;
import pl.mwojcik.mio.percepton.variables.InputVariableList;

public class PerceptronNetworkTest {

	private Builder<InputVariable, WeightedSumPerceptron<InputVariable>, Animal> builder;

	private InputVariableList<InputVariable> cat;
	
	@Before
	public void setUp() throws Exception {

		builder = PerceptronNetwork
				.<InputVariable, WeightedSumPerceptron<InputVariable>, Animal> builder();
		
		builder.setInsideLayers(2, 4);
		builder.setClasses(Arrays.asList(Animal.values()));
		builder.setFunction(new SigmoidalFunction());
		builder.setPerceptronFactory(WeightedSumPerceptron.factory());
		builder.setVariablesCount(3);
		

		cat = new InputVariableList<>(3);

		cat.setVariable(0, BinaryVariable.factory(1));
		cat.setVariable(1, BinaryVariable.factory(0));
		cat.setVariable(2, BinaryVariable.factory(1));
	}

	@Test
	public void testBuilder() {
		PerceptronNetwork<InputVariable, WeightedSumPerceptron<InputVariable>, Animal> network = builder.build();
		
		List<PerceptronLayer<InputVariable, WeightedSumPerceptron<InputVariable>>> layers = network.getLayers();
		
		assertEquals(3, layers.size());
		
		PerceptronLayer<InputVariable, WeightedSumPerceptron<InputVariable>> firstLayer = layers.get(0);
		assertEquals(2, firstLayer.getPerceptrons().size());
		assertEquals(3, firstLayer.getPerceptrons().get(0).variablesCount);
		
		
		PerceptronLayer<InputVariable, WeightedSumPerceptron<InputVariable>> secondLayer = layers.get(1);
		assertEquals(4, secondLayer.getPerceptrons().size());
		assertEquals(2, secondLayer.getPerceptrons().get(0).variablesCount);
		

		assertTrue(layers.get(2) instanceof PerceptronOutsideLayer<?,?,?>);
		
		@SuppressWarnings("unchecked")
		PerceptronOutsideLayer<InputVariable, WeightedSumPerceptron<InputVariable>, Animal> outsideLayer = 
				(PerceptronOutsideLayer<InputVariable, WeightedSumPerceptron<InputVariable>, Animal>) layers.get(2);
		

		assertEquals(3, outsideLayer.getPerceptrons().size());
		assertEquals(4, outsideLayer.getPerceptrons().get(0).variablesCount);
		
		Collection<Animal> result = network.classify(cat);
		assertNotNull(result);
	}

}

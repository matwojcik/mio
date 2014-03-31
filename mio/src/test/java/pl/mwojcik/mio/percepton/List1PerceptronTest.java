package pl.mwojcik.mio.percepton;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import pl.mwojcik.mio.percepton.functions.Function;
import pl.mwojcik.mio.percepton.functions.SigmoidalFunction;
import pl.mwojcik.mio.percepton.variables.InputVariable;
import pl.mwojcik.mio.percepton.variables.InputVariableImpl;
import pl.mwojcik.mio.percepton.variables.InputVariableList;

public class List1PerceptronTest {

	WeightedSumPerceptron<InputVariable> perceptron;
	
	@Before
	public void setUp() throws Exception {
		Function function = new SigmoidalFunction();

		List<Double> weights = new ArrayList<>();
		
		weights.add(-12170.);
		weights.add(0.00001); // waga
		weights.add(0.1); // długość
		weights.add(.1); // szerokość
		weights.add(.1); // ilość kół
		weights.add(.1); // ilosc skrzydeł
		weights.add(.2); // ilosc miejsc
		weights.add(.1); // ilosc kierowcow/pilotow
		weights.add(0.1); // moc silnika
		weights.add(0.1); // predkosc max
		weights.add(1.);// ilosc drzwi

		perceptron = new WeightedSumPerceptron<>(
				function, 10, weights, 0.9);
	}

	@Test
	public void testIsActivePlane() {

		InputVariableList<InputVariable> test = new InputVariableList<>(10);
		
		test.setVariable(0, InputVariableImpl.factory(200_000_000)); // waga
		test.setVariable(1, InputVariableImpl.factory(40)); // długość
		test.setVariable(2, InputVariableImpl.factory(20)); // szerokość
		test.setVariable(3, InputVariableImpl.factory(6)); // ilość kół
		test.setVariable(4, InputVariableImpl.factory(2)); // ilosc skrzydeł
		test.setVariable(5, InputVariableImpl.factory(300)); // ilosc miejsc
		test.setVariable(6, InputVariableImpl.factory(2));  // ilosc kierowcow/pilotow
		test.setVariable(7, InputVariableImpl.factory(100_000)); // moc silnika
		test.setVariable(8, InputVariableImpl.factory(1300)); // predkosc max
		test.setVariable(9, InputVariableImpl.factory(2));// ilosc drzwi
		
		assertTrue("Plane should activate perceptor", perceptron.isActive(test));
	}

	@Test
	public void testIsActiveCar() {

		InputVariableList<InputVariable> test = new InputVariableList<>(10);
		
		test.setVariable(0, InputVariableImpl.factory(1200)); // waga
		test.setVariable(1, InputVariableImpl.factory(4)); // długość
		test.setVariable(2, InputVariableImpl.factory(1.6)); // szerokość
		test.setVariable(3, InputVariableImpl.factory(4)); // ilość kół
		test.setVariable(4, InputVariableImpl.factory(0)); // ilosc skrzydeł
		test.setVariable(5, InputVariableImpl.factory(5)); // ilosc miejsc
		test.setVariable(6, InputVariableImpl.factory(1));  // ilosc kierowcow/pilotow
		test.setVariable(7, InputVariableImpl.factory(100)); // moc silnika
		test.setVariable(8, InputVariableImpl.factory(180)); // predkosc max
		test.setVariable(9, InputVariableImpl.factory(4));// ilosc drzwi
		
		assertFalse("Car shouldn't activate perceptor", perceptron.isActive(test));
	}
	
	
	@Test
	public void testIsActiveDron() {

		InputVariableList<InputVariable> test = new InputVariableList<>(10);
		
		test.setVariable(0, InputVariableImpl.factory(1000)); // waga
		test.setVariable(1, InputVariableImpl.factory(2)); // długość
		test.setVariable(2, InputVariableImpl.factory(3)); // szerokość
		test.setVariable(3, InputVariableImpl.factory(4)); // ilość kół
		test.setVariable(4, InputVariableImpl.factory(2)); // ilosc skrzydeł
		test.setVariable(5, InputVariableImpl.factory(0)); // ilosc miejsc
		test.setVariable(6, InputVariableImpl.factory(0));  // ilosc kierowcow/pilotow
		test.setVariable(7, InputVariableImpl.factory(1000)); // moc silnika
		test.setVariable(8, InputVariableImpl.factory(100)); // predkosc max
		test.setVariable(9, InputVariableImpl.factory(0));// ilosc drzwi
		
		assertFalse("Dron shouldn't activate perceptor", perceptron.isActive(test));
	}
	
	@Test
	public void testIsActiveHelicopter() {

		InputVariableList<InputVariable> test = new InputVariableList<>(10);
		
		test.setVariable(0, InputVariableImpl.factory(8000)); // waga
		test.setVariable(1, InputVariableImpl.factory(8)); // długość
		test.setVariable(2, InputVariableImpl.factory(3)); // szerokość
		test.setVariable(3, InputVariableImpl.factory(6)); // ilość kół
		test.setVariable(4, InputVariableImpl.factory(2)); // ilosc skrzydeł
		test.setVariable(5, InputVariableImpl.factory(6)); // ilosc miejsc
		test.setVariable(6, InputVariableImpl.factory(1));  // ilosc kierowcow/pilotow
		test.setVariable(7, InputVariableImpl.factory(1000)); // moc silnika
		test.setVariable(8, InputVariableImpl.factory(250)); // predkosc max
		test.setVariable(9, InputVariableImpl.factory(2));// ilosc drzwi
		
		assertFalse("Helicopter shouldn't activate perceptor", perceptron.isActive(test));
	}
	

	@Test
	public void testIsActiveWarPlane() {

		InputVariableList<InputVariable> test = new InputVariableList<>(10);
		
		test.setVariable(0, InputVariableImpl.factory(200_000_000)); // waga
		test.setVariable(1, InputVariableImpl.factory(40)); // długość
		test.setVariable(2, InputVariableImpl.factory(20)); // szerokość
		test.setVariable(3, InputVariableImpl.factory(6)); // ilość kół
		test.setVariable(4, InputVariableImpl.factory(2)); // ilosc skrzydeł
		test.setVariable(5, InputVariableImpl.factory(100)); // ilosc miejsc
		test.setVariable(6, InputVariableImpl.factory(2));  // ilosc kierowcow/pilotow
		test.setVariable(7, InputVariableImpl.factory(100_000)); // moc silnika
		test.setVariable(8, InputVariableImpl.factory(1000)); // predkosc max
		test.setVariable(9, InputVariableImpl.factory(1));// ilosc drzwi
		
		assertFalse("Plane should activate perceptor", perceptron.isActive(test));
	}
}

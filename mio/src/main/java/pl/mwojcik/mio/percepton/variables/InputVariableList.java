package pl.mwojcik.mio.percepton.variables;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InputVariableList<T extends InputVariable> {
	private int length;
	private List<T> variables;
	
	@SuppressWarnings("unchecked")
	public InputVariableList(int length) {
		super();
		this.length = length;
		variables = (List<T>) new ArrayList<>(Collections.nCopies(length, null));
	}

	public int getLength() {
		return length;
	}

	public List<T> getVariables() {
		return Collections.unmodifiableList(variables);
	}
	
	public InputVariableList<T> setVariable(int i, T variable) {
		if(i < 0 || i >= length)
			throw new IndexOutOfBoundsException("Variables length: " + length + ", trial of access to: " + i + " element");
			
		variables.set(i, variable);
		
		return this;
	}
	
}

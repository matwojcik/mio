package pl.mwojcik.mio.image;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import pl.mwojcik.mio.percepton.variables.InputVariable;
import pl.mwojcik.mio.percepton.variables.InputVariableImpl;
import pl.mwojcik.mio.percepton.variables.InputVariableList;

public class ImageFactory {

	
	public InputVariableList<InputVariable> factory(File file) throws IOException{
		
		BufferedImage image = ImageIO.read(file);
		
		int size = image.getWidth() * image.getHeight();
		
		InputVariableList<InputVariable> variables = new InputVariableList<>(size);
		
		int i = 0;
		
		for(int x = 0; x < image.getWidth(); ++x) {
			for(int y = 0; y < image.getHeight(); y++) {
				double value = image.getRGB(x, y) == Color.white.getRGB() ? 0 : 1;
				
				variables.setVariable(i++, InputVariableImpl.factory(value));
			}
		}
		
		return variables;
	}

}

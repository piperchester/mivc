package mivc.System;

import java.awt.Component;
import java.awt.Graphics;

public interface IStudyImage {

	public void showImage(Component c, Graphics g, int x, int y, int width, 
			int height);
	public String getPath();
	
}

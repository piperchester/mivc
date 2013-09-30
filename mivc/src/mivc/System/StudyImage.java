package mivc.System;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

import mivc.System.IO.ImageDAO;

public class StudyImage implements IStudyImage {

	private String path;
	private Image img;
	
	public StudyImage(String path) {
		this.path = path;
		//loadImage();
	}
	
	public void loadImage() {
		// Now we read the image from the network
		img = ImageDAO.getInstance().read(path);
	}
	
	public void showImage(final Component c, final Graphics g, final int x, 
			final int y, final int width, final int height) {
		if (img == null) {
			g.drawString("Loading Image", x + width/2-30, y + height/2-5);
			// Load the image in a separate thread so as not to tie up the GUI, 
			// when finished it will draw the image and call repaint
			new Thread() {
				@Override
				public void run() {
					Random rand = new Random();
					// Input a random delay for loading of image (just for show)
					// We will choose a random time between 0 and 3 seconds
					final long loadTime = rand.nextInt(1000);
					try {
						Thread.sleep(loadTime);
					} catch (InterruptedException e) { }
					loadImage();
					// Make sure this happens on the event thread (EDT)
			        javax.swing.SwingUtilities.invokeLater(new Runnable() {
			            public void run() {
			            	System.out.println("Image loaded in (" + 
			            			loadTime/1000.0 + ") seconds, calling repaint");
							c.repaint();
			            }
			        });
	
				}
			}.start();
		} else {
	        javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	            	//System.out.println("Drawing image now");
	            	c.getGraphics().drawImage(img, x, y, width, height, null);
	            }
	        });
		}
	}

	@Override
	public String getPath() {
		return path;
	}
	
}

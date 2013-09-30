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
			g.drawString("Loading Image", width/2-30, height/2-5);
			// Load the image in a separate thread so as not to tie up the GUI, 
			// when finished it will draw the image and call repaint
			new Thread() {
				@Override
				public void run() {
					Random rand = new Random();
					loadImage();
					// Input a random delay for loading of image (just for show)
					// We will choose a random time between 0 and 3 seconds
					try {
						Thread.sleep(rand.nextInt(3000));
					} catch (InterruptedException e) { }
					// Make sure this happens on the event thread (EDT)
			        javax.swing.SwingUtilities.invokeLater(new Runnable() {
			            public void run() {
			            	System.out.println("Drawing image now");
			            	c.getGraphics().drawImage(img, x, y, width, height, null);
							c.repaint();
			            }
			        });
	
				}
			}.start();
		} else {
	        javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
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

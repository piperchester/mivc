package mivc.UI;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import mivc.System.IStudyImage;

@SuppressWarnings("serial")
public class QuadView extends JPanel {

	private IStudyImage[] images = new IStudyImage[4];
	
	/*
	 * Creates a display for viewing four images
	 */
	public QuadView() {
		setLayout(new GridLayout(2,2));
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		initializeComponents();
		layoutComponents();
	}
	
	/*
	 * Initialize components for the GUI
	 */
	private void initializeComponents() {

	}
	
	/*
	 * Lay the components on the GUI
	 */
	private void layoutComponents() {

	}
	
	/**
	 * Set the images to view on the display
	 * @param img1 first image to display
	 * @param img2 second image to display
	 * @param img3 third image to display
	 * @param img4 fourth image to display
	 */
	protected void setImages(IStudyImage... images) {
		
		for (int i = 0; i < images.length; i++) {
			try {
				this.images[i] = images[i];
			} catch (NullPointerException ex) {
				// ignore
			}
		}
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (images == null) {
			return;
		}
		int smSize = Math.min(getHeight()/2, getWidth()/2);
		if (images[0] != null) {
			int x = (int) Math.floor((getWidth()-smSize*2) / 2);
			int y = (int) Math.floor((getHeight()-smSize*2) / 2);
			images[0].showImage(this, g, x, y, smSize, smSize);
		}
		if (images[1] != null) {
			int x = (int) Math.floor((getWidth()-smSize*2) / 2)+smSize;
			int y = (int) (Math.floor((getHeight()-smSize*2) / 2));
			images[1].showImage(this, g, x, y, smSize, smSize);
		}
		if (images[2] != null) {
			int x = (int) Math.floor((getWidth()-smSize*2) / 2);
			int y = (int) (Math.floor((getHeight()-smSize*2) / 2)  + smSize);
			images[2].showImage(this, g, x, y, smSize, smSize);
		}
		if (images[3] != null) {
			int x = (int) Math.floor((getWidth()-smSize*2) / 2)+smSize;
			int y = (int) (Math.floor((getHeight()-smSize*2) / 2)  + smSize);
			images[3].showImage(this, g, x, y, smSize, smSize);
		}
	}
	
	
	/**
	 * Fancy way of making a square image whilst keeping the aspect ratio.
	 * @param img	the image to be made square
	 * @param dim	the desired size of the image
	 * @return	a square image representation of the passed image using transparency
	 * @throws IOException 
	 */
	public static Image getSquareImage(BufferedImage img, int dim) {
		
		int height = img.getHeight(null);
		int width = img.getWidth(null);
		
		// Find the largest factor
		int maxFactor = height > width ? height : width;
		
		// Create a blank image of the desired square size
		BufferedImage newImg = new BufferedImage(maxFactor, maxFactor, 
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D newG = newImg.createGraphics();
		
		// Compute the translation
        double translateX = (maxFactor - width)/2;
        double translateY = (maxFactor - height)/2;
        
        // Translate the image
        AffineTransform imgTransform = 
        		AffineTransform.getTranslateInstance(
        				translateX, translateY);
        // Draw on the new image's graphics with the translation
        newG.drawRenderedImage((RenderedImage) img, imgTransform);
        
        // Clean up
        newG.dispose();

        return newImg;
	}
	
}

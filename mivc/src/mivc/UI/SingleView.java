package mivc.UI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SingleView extends JPanel {

	private BufferedImage image;
	
	/**
	 * A single image display for the GUI
	 */
	public SingleView() {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		initializeComponents();
		layoutComponents();
	}
	
	/**
	 * Initialize the components for the GUI
	 */
	private void initializeComponents() {
		setPreferredSize(new Dimension(400, 400));
	}
	
	/**
	 * Layout the components on the GUI
	 */
	private void layoutComponents() {
//		add(lblImage, BorderLayout.CENTER);
	}
	
	/**
	 * Set the image to be displayed
	 * @param img the image to be displayed
	 */
	protected void setImage(BufferedImage img) {
		this.image = img;
		this.repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int smSize = Math.min(getHeight(), getWidth());
		if (image != null) {
			int x = (int) Math.floor((getWidth()-smSize) / 2);
			int y = (int) Math.floor((getHeight()-smSize) / 2);
			g.drawImage(getSquareImage(image, smSize), x, y, smSize, smSize, null);
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

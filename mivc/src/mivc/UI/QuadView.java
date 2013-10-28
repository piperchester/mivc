package mivc.UI;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class QuadView extends JPanel {

	private BufferedImage[] images = new BufferedImage[4];
	
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
	 * @param images 0 to many images to be displayed
	 */
	protected void setImages(BufferedImage... images) {
		
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
			g.drawImage(images[0], x, y, smSize, smSize, null);
		}
		if (images[1] != null) {
			int x = (int) Math.floor((getWidth()-smSize*2) / 2)+smSize;
			int y = (int) (Math.floor((getHeight()-smSize*2) / 2));
			g.drawImage(images[1], x, y, smSize, smSize, null);
		}
		if (images[2] != null) {
			int x = (int) Math.floor((getWidth()-smSize*2) / 2);
			int y = (int) (Math.floor((getHeight()-smSize*2) / 2)  + smSize);
			g.drawImage(images[2], x, y, smSize, smSize, null);
		}
		if (images[3] != null) {
			int x = (int) Math.floor((getWidth()-smSize*2) / 2)+smSize;
			int y = (int) (Math.floor((getHeight()-smSize*2) / 2)  + smSize);
			g.drawImage(images[3], x, y, smSize, smSize, null);
		}
	}
}

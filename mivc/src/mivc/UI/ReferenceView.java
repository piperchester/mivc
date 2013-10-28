package mivc.UI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class ReferenceView extends JPanel {

	private BufferedImage image;
	
	/**
	 * A single image display for the GUI
	 */
	public ReferenceView() {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		initializeComponents();
		layoutComponents();
	}
	
	/**
	 * Initialize the components for the GUI
	 */
	private void initializeComponents() {
		setPreferredSize(new Dimension(100, 100));
		setBorder(new EmptyBorder(10,10,10,10));
	}
	
	/**
	 * Layout the components on the GUI
	 */
	private void layoutComponents() {
//		add(lblImage, BorderLayout.CENTER);
	}
	
	/**
	 * Set the image to be displayed
	 * @param imgs 0 to many images to be displayed
	 */
	public void setImage(BufferedImage img) {
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
			g.drawImage(image, x, y, smSize, smSize, null);
		}
	}
	
}

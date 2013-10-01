package mivc.UI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

import mivc.System.IStudyImage;

@SuppressWarnings("serial")
public class SingleView extends JPanel {

	private IStudyImage image;
	
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
	 * @param imgs 0 to many images to be displayed
	 */
	public void setImages(IStudyImage... imgs) {
		this.image = imgs[0];
		this.repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int smSize = Math.min(getHeight(), getWidth());
		if (image != null) {
			int x = (int) Math.floor((getWidth()-smSize) / 2);
			int y = (int) Math.floor((getHeight()-smSize) / 2);
			image.showImage(this, g, x, y, smSize, smSize);
			//g.drawImage(getSquareImage(image, smSize), x, y, smSize, smSize, null);
		}
	}
	
}

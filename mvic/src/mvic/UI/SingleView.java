package mvic.UI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sun.java.swing.plaf.windows.WindowsBorders.DashedBorder;

@SuppressWarnings("serial")
public class SingleView extends JPanel {

	private JLabel lblImage;
	
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
		lblImage = new JLabel("Image Goes Here", JLabel.CENTER);
		lblImage.setPreferredSize(new Dimension(400,400));
		lblImage.setBorder(DashedBorder.createBlackLineBorder());
	}
	
	/**
	 * Layout the components on the GUI
	 */
	private void layoutComponents() {
		add(lblImage, BorderLayout.CENTER);
	}
	
	/**
	 * Set the image to be displayed
	 * @param img the image to be displayed
	 */
	protected void setImage(BufferedImage img) {
		lblImage.setIcon(new ImageIcon(img));
	}
	
}

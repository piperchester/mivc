package view;

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
	
	public SingleView() {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		initializeComponents();
		layoutComponents();
	}
	
	private void initializeComponents() {
		lblImage = new JLabel("Image Goes Here", JLabel.CENTER);
		lblImage.setPreferredSize(new Dimension(400,400));
		lblImage.setBorder(DashedBorder.createBlackLineBorder());
	}
	
	private void layoutComponents() {
		add(lblImage, BorderLayout.CENTER);
	}
	
	protected void setImage(BufferedImage img) {
		lblImage.setIcon(new ImageIcon(img));
	}
	
}

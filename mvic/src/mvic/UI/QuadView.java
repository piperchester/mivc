package mvic.UI;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sun.java.swing.plaf.windows.WindowsBorders.DashedBorder;

@SuppressWarnings("serial")
public class QuadView extends JPanel {

	private JLabel lblImage1;
	private JLabel lblImage2;
	private JLabel lblImage3;
	private JLabel lblImage4;
	
	public QuadView() {
		setLayout(new GridLayout(2,2));
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		initializeComponents();
		layoutComponents();
	}
	
	private void initializeComponents() {
		lblImage1 = new JLabel("Image Goes Here", JLabel.CENTER);
		lblImage1.setPreferredSize(new Dimension(100,100));
		lblImage1.setBorder(DashedBorder.createBlackLineBorder());
		lblImage2 = new JLabel("Image Goes Here", JLabel.CENTER);
		lblImage2.setPreferredSize(new Dimension(100,100));
		lblImage2.setBorder(DashedBorder.createBlackLineBorder());
		lblImage3 = new JLabel("Image Goes Here", JLabel.CENTER);
		lblImage3.setPreferredSize(new Dimension(100,100));
		lblImage3.setBorder(DashedBorder.createBlackLineBorder());
		lblImage4 = new JLabel("Image Goes Here", JLabel.CENTER);
		lblImage4.setPreferredSize(new Dimension(100,100));
		lblImage4.setBorder(DashedBorder.createBlackLineBorder());
	}
	
	private void layoutComponents() {
		add(lblImage1);
		add(lblImage2);
		add(lblImage3);
		add(lblImage4);
	}
	
	protected void setImage(BufferedImage img1, BufferedImage img2, 
			BufferedImage img3, BufferedImage img4) {
		lblImage1.setIcon(new ImageIcon(img1));
		lblImage2.setIcon(new ImageIcon(img2));
		lblImage3.setIcon(new ImageIcon(img3));
		lblImage4.setIcon(new ImageIcon(img4));
	}
	
}

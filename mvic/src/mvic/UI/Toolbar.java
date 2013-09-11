package mvic.UI;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

/*
 * Toolbar.java
 * 
 */

@SuppressWarnings("serial")
public class Toolbar extends JPanel {

	private JButton btnOpen;
	private JButton btnSaveView;
	private JButton btnSaveStudy;
	private JButton btnChangeView;
	private JButton btnNext;
	private JButton btnPrev;
	private JLabel lblStatus; // This is updated by the MainView
	
	public Toolbar() {
		setLayout(new MigLayout());
		initializeComponents();
		layoutComponents();
	}
	
	private void initializeComponents() {
		btnOpen = new JButton("Open");
		btnSaveView = new JButton("Save View");
		btnSaveStudy = new JButton("Save Study");
		btnChangeView = new JButton("View");
		btnNext = new JButton("next >");
		btnNext.setPreferredSize(new Dimension(40, 25));
		btnPrev = new JButton("< prev");
		btnPrev.setPreferredSize(new Dimension(40, 25));
		lblStatus = new JLabel("Viewing image(s) n of n");
//		btnOpen.addActionListener(controller);
//		btnSaveView.addActionListener(controller);
//		btnSaveStudy.addActionListener(controller);
//		btnChangeView.addActionListener(controller);
//		btnNext.addActionListener(controller);
//		btnPrev.addActionListener(controller);
	}
	
	private void layoutComponents() {
		add(btnOpen, "gap unrelated");
		add(btnSaveView);
		add(btnSaveStudy);
		add(btnChangeView, "wrap, push");
		add(btnPrev, "west, grow");
		add(lblStatus, "span 2, gap unrelated");
		add(btnNext, "east, gap unrelated");
	}
	
	protected void setStatus(String currentImage, String totalImages) {
		lblStatus.setText("Viewing image(s) " + currentImage 
				+ " of " + totalImages);
	}

	protected void addOpenListener(ActionListener al) {
		btnOpen.addActionListener(al);
	}
	
	protected void addViewListener(ActionListener al) {
		btnChangeView.addActionListener(al);
	}
	
	protected void addSaveStudyListener(ActionListener al) {
		btnSaveStudy.addActionListener(al);
	}
	
	protected void addSaveViewListener(ActionListener al) {
		btnSaveView.addActionListener(al);
	}
	
	protected void addPrevListener(ActionListener al) {
		btnPrev.addActionListener(al);
	}
	
	protected void addNextListener(ActionListener al) {
		btnNext.addActionListener(al);
	}
	
}

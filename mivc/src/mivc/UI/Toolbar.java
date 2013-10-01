package mivc.UI;

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
	
	/**
	 * A toolbar for the GUI which contains multiple action buttons
	 */
	public Toolbar() {
		setLayout(new MigLayout());
		initializeComponents();
		layoutComponents();
	}
	
	/**
	 * Initialize components for the GUI
	 */
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
	}
	
	/**
	 * Layout the components on the GUI
	 */
	private void layoutComponents() {
		add(btnOpen, "gap unrelated");
		add(btnSaveView);
		add(btnSaveStudy);
		add(btnChangeView, "wrap, push");
		add(btnPrev, "west, grow");
		add(lblStatus, "span 2, gap unrelated");
		add(btnNext, "east, gap unrelated");
	}
	
	/**
	 * Update the status displayed on the GUI
	 * @param currentImage the current image number
	 * @param totalImages the total number of images in the study
	 */
	protected void setStatus(String value) {
		lblStatus.setText(value);
	}

	/**
	 * Add a listener to the open event
	 * @param al the listener to register for this event
	 */
	protected void addOpenListener(ActionListener al) {
		btnOpen.addActionListener(al);
	}
	
	/**
	 * Add a listener to the view toggle event
	 * @param al the listener to register for this event
	 */
	protected void addViewListener(ActionListener al) {
		btnChangeView.addActionListener(al);
	}
	
	/**
	 * Add a listener to the Save Study event
	 * @param al the listener to register for this event
	 */
	protected void addSaveStudyListener(ActionListener al) {
		btnSaveStudy.addActionListener(al);
	}
	
	/**
	 * Add a listener to the Save View event
	 * @param al the listener to register for this event
	 */
	protected void addSaveViewListener(ActionListener al) {
		btnSaveView.addActionListener(al);
	}
	
	/**
	 * Add a listener to the previous event
	 * @param al the listener to register for this event
	 */
	protected void addPrevListener(ActionListener al) {
		btnPrev.addActionListener(al);
	}
	
	/**
	 * Add a listener to the next event
	 * @param al the listener to register for this event
	 */
	protected void addNextListener(ActionListener al) {
		btnNext.addActionListener(al);
	}
	
}

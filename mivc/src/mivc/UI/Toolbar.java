package mivc.UI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import mivc.System.ChangeReconstructionCommand;
import mivc.System.ChangeViewCommand;
import mivc.System.ListStudiesCommand;
import mivc.System.CommandHandler;
import mivc.System.NextImageCommand;
import mivc.System.PrevImageCommand;
import mivc.System.SaveStudyCommand;
import mivc.System.SaveViewCommand;
import net.miginfocom.swing.MigLayout;

/*
 * Toolbar.java
 * 
 */

@SuppressWarnings("serial")
public class Toolbar extends JPanel implements ActionListener {

	private JButton btnOpen;
	private JButton btnSaveView;
	private JButton btnSaveStudy;
	private JButton btnChangeView;
	private JButton btnChangeViewType;
	private JButton btnNext;
	private JButton btnPrev;
	private JLabel lblStatus; // This is updated by the MainView
	private JTextField tfMinWindow;
	private JTextField tfMaxWindow;
	private StudyView parent;
	private CommandHandler invoker;
	
	/**
	 * A toolbar for the GUI which contains multiple action buttons
	 */
	public Toolbar(StudyView parent, CommandHandler invoker) {
		this.parent = parent;
		this.invoker = invoker;
		setLayout(new MigLayout());
		initializeComponents();
		layoutComponents();
	}
	
	/**
	 * Initialize components for the GUI
	 */
	private void initializeComponents() {
		btnOpen = new JButton("Open");
		btnOpen.addActionListener(this);
		btnSaveView = new JButton("Save View");
		btnSaveView.addActionListener(this);
		btnSaveStudy = new JButton("Save Study");
		btnSaveStudy.addActionListener(this);
		btnChangeView = new JButton("View");
		btnChangeView.addActionListener(this);
		btnChangeViewType = new JButton("Change Reconstruction");
		btnChangeViewType.addActionListener(this);
		btnNext = new JButton("next >");
		btnNext.addActionListener(this);
		btnNext.setPreferredSize(new Dimension(40, 25));
		btnPrev = new JButton("< prev");
		btnPrev.addActionListener(this);
		btnPrev.setPreferredSize(new Dimension(40, 25));
		lblStatus = new JLabel("Viewing image(s) n of n");
		lblStatus.setPreferredSize(new Dimension(300, 30));
		tfMinWindow = new JTextField();
		tfMinWindow.setPreferredSize(new Dimension(50, 30));
		tfMaxWindow = new JTextField();
		tfMaxWindow.setPreferredSize(new Dimension(50, 30));
	}
	
	/**
	 * Layout the components on the GUI
	 */
	private void layoutComponents() {
		add(btnOpen, "gap unrelated");
		add(btnSaveView);
		add(btnSaveStudy);
		add(btnChangeViewType);
		add(btnChangeView, "wrap, push");
		add(btnPrev, "west, grow");
		add(lblStatus, "span 2, gap unrelated");
		add(new JLabel("Windowing: "), "skip 1, split 2");
		add(tfMinWindow);
		add(tfMaxWindow);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if (src == btnChangeView) {
			invoker.addCommand(new ChangeViewCommand(parent));
		} else if (src == btnOpen) {
			invoker.addCommand(new ListStudiesCommand(parent));
		} else if (src == btnPrev) {
			invoker.addCommand(new PrevImageCommand(parent));
		} else if (src == btnNext) {
			invoker.addCommand(new NextImageCommand(parent));
		} else if (src == btnSaveView) {
			invoker.addCommand(new SaveViewCommand(parent));
		} else if (src == btnSaveStudy) {
			invoker.addCommand(new SaveStudyCommand(parent));
		} else if (src == btnChangeViewType) {
			invoker.addCommand(new ChangeReconstructionCommand(parent));
		}
	}
	
	protected int getMinWindow() {
		if (tfMinWindow.getText().equals("")) {
			return 0;
		}
		return Integer.parseInt(tfMinWindow.getText());
	}

	protected int getMaxWindow() {
		if (tfMaxWindow.getText().equals("")) {
			return 255;
		}
		return Integer.parseInt(tfMaxWindow.getText());
	}

}

package mvic.System;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class StudyList extends JFrame {

	private JList<String> studiesList;
	private JCheckBox cbDefault;
	private String[] studies;
	
	public StudyList(String[] studies) {
		this.studies = studies;
		setLayout(new BorderLayout());
		initializeComponents();
		layoutComponents();
		
		// Setup JFrame deets.
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pack(); // Pack before setting location (this determines size)
		
		// Get the current screen's size
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		// Compute and set the location so the frame is centered
		int x = screen.width/2-getSize().width/2;
		int y = screen.height/2-getSize().height/2;
		setLocation(x, y);
		setTitle("Choose a study...");
		
		setVisible(true);
	}
	
	private void initializeComponents() {
		studiesList = new JList<String>(studies);
		cbDefault = new JCheckBox("Use as default study?");
	}
	
	private void layoutComponents() {
		add(new JScrollPane(studiesList), BorderLayout.CENTER);
		add(cbDefault, BorderLayout.SOUTH);
	}
	
	// Probably never used...
	protected void setList(List<String> studies) {
		DefaultListModel<String> dlm = (DefaultListModel<String>) studiesList.getModel();
		dlm.removeAllElements();
		for(String s : studies) {
			dlm.addElement(s);
		}
	}
	
}

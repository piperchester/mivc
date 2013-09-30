package mivc.UI;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class StudyList extends JFrame implements ActionListener {

	private JList<String> studiesList;
	private JCheckBox cbDefault;
	private JButton btnOpen;
	
	public StudyList() {
		setLayout(new MigLayout("fill", "", ""));
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
		
	}
	
	private void initializeComponents() {
		studiesList = new JList<String>(new DefaultListModel<String>());
		cbDefault = new JCheckBox("Use as default study?");
		btnOpen = new JButton("Open");
		btnOpen.addActionListener(this);
	}
	
	private void layoutComponents() {
		JScrollPane sp = new JScrollPane(studiesList);
		sp.setMinimumSize(new Dimension(200, 300));
		add(sp, "span 2, grow, wrap");
		add(cbDefault, "split, h 5%");
		add(btnOpen, "gapleft push, h 5%");
	}
	
	protected void updateList(String[] studies) {
		studiesList.setListData(studies);
		// uncheck the default
		cbDefault.setSelected(false);
	}
	
	protected boolean isDefaultSelected() {
		return cbDefault.isSelected();
	}
	
	protected String getSelectedStudy() {
		return studiesList.getSelectedValue();
	}
	
	protected void addSelectionListener(ActionListener al) {
		btnOpen.addActionListener(al);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Hide the window when open is pressed.
		if (e.getSource() == btnOpen) {
			setVisible(false);
		}
	}
	
}

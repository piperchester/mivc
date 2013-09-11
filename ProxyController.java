package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProxyController {

	private StudyView view;
	//private Study currentStudy;
	
	public ProxyController(StudyView view) {
		this.view = view;
		view.addViewListener(new ViewListener());
		view.addOpenListener(new OpenListener());
		view.addSaveStudyListener(new SaveStudyListener());
		view.addSaveViewListener(new SaveViewListener());
		view.addNextListener(new NextListener());
		view.addPrevListener(new PrevListener());
	}
	
	
	class ViewListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			view.toggleView();
		}
	}
	
	class OpenListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Call to open a new study");
			view.showList(new String[] { "Study 1", "Study 2", "Study 3", "  ...  " });
			// Display the Study List window
		}
	}
	
	class SaveStudyListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Call to save a study");
			// Check to see if that study exists already
			// If it does, warn that they will overwrite
			// If it does not, save and continue
		}
	}
	
	class SaveViewListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Call save the view");
			// Call to the SettingsManager to save the current view and images
		}
	}
	
	class PrevListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Call to move to previous image(s)");
			// Send the appropriate images to the view
		}
	}
	
	class NextListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Call to move to next image(s)");
			// Send the appropriate images to the view
		}
	}

}

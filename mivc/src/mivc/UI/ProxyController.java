package mivc.UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import mivc.System.Study;


public class ProxyController {

	private StudyView view;
	private Study currentStudy;
	
	
	/**
	 * Proxy controller is the brains for the GUI.  Currently it is designed
	 * to work with a Java GUI but could be extended.
	 * @param view The view that this controller will be controlling.
	 */
	public ProxyController(StudyView view) {
		this.view = view;
		view.addViewListener(new ViewListener());
		view.addOpenListener(new OpenListener());
		view.addSaveStudyListener(new SaveStudyListener());
		view.addSaveViewListener(new SaveViewListener());
		view.addNextListener(new NextListener());
		view.addPrevListener(new PrevListener());
	}
	
	/**
	 * Calls to the view to change the current view
	 *
	 */
	class ViewListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			view.toggleView();
		}
	}
	
	/**
	 * Calls to the view to show the list of studies for the user to make a
	 * selection
	 *
	 */
	class OpenListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Call to open a new study");
			view.showList(new String[] { "Study 1", "Study 2", "Study 3", "  ...  " });
			// Display the Study List window
		}
	}
	
	/**
	 * Saves a new study.
	 *
	 */
	class SaveStudyListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Call to save a study");
			// Check to see if that study exists already
			// If it does, warn that they will overwrite
			// If it does not, save and continue
		}
	}
	
	/**
	 * Gathers the pertinent information from the view in order to save it.
	 *
	 */
	class SaveViewListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Call save the view");
			// Call to the SettingsManager to save the current view and images
		}
	}
	
	/**
	 * Moves to the previous image
	 *
	 */
	class PrevListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Call to move to previous image(s)");
			// Send the appropriate images to the view
		}
	}
	
	/**
	 * Moves to the next image
	 *
	 */
	class NextListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Call to move to next image(s)");
			// Send the appropriate images to the view
		}
	}

}
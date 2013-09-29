package mivc.UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.util.List;

import mivc.System.Study;
import mivc.System.IO.StudyManager;


public class ProxyController {

	private StudyView view;
	private Study currentStudy;
	public StudyManager studyManager;
	private List<Study> studies;
	
	
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
		view.addStudySelectionListener(new StudySelectionListener());	
		
		studyManager = new StudyManager();
	}
	
	public static void loadStudies()
	{
		//method here to load studies
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
		public void actionPerformed(ActionEvent e) {
			System.out.println("Call to open a new study");
			
			studyManager.scan("studies");
			
			int studyCounter = 0;
			String[] studies = new String[studyManager.studyCount];
			
			for (String study : studyManager.listStudies()){
				studies[studyCounter] = study;
				studyCounter++;
			}
			
			view.showList(studies);  // Display the Study List window
		}
	}
	
	/**
	 * Saves a new study.
	 *
	 */
	class SaveStudyListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
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
		public void actionPerformed(ActionEvent e) {
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
		public void actionPerformed(ActionEvent e) {
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
		public void actionPerformed(ActionEvent e) {
			System.out.println("Call to move to next image(s)");
			// Send the appropriate images to the view
		}
	}
	
	/**
	 * Gets the selected study and whether it was set as the default
	 *
	 */
	class StudySelectionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("You selected " + view.getSelectedStudy());
			System.out.println("Is this the default study? " + 
						view.isDefaultSelected());
			// Load the selected Study, save default if it was selected
			
			
			BufferedImage image;
			try {
				image = ImageIO.read(new File("studies/lung/lung034.jpg"));
				view.setImages(image);
			} catch (IOException ex) {
				System.out.println("Couldn't read image...");
			}
			
			
			
		}
	}

}

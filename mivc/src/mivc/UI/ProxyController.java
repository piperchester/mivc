package mivc.UI;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import mivc.System.Study;
import mivc.System.IO.StudyDAO;
import mivc.UI.StudyView.ViewType;


public class ProxyController {

	private StudyView view;
	private Study currentStudy;
	private Image[] currentImages = new Image[4];
	private HashMap<String, Study> studies;
	private int imageIndex = 0;
	
	
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
	}
	
	public void loadStudies(boolean forceReload) {
		if (studies == null || forceReload) {
			studies = new HashMap<String, Study>();
			List<Study> tmp = StudyDAO.getInstance().listStudies();
			for (Study s : tmp) {
				studies.put(s.getName(), s);
			}
		}
	}
	
	private void updateViewStatus() {
		String status = "";
		int totalImages = currentStudy.getImageCount();
		
		if (view.getCurrentView() == ViewType.SINGLE_VIEW) {
			status = "Viewing " + currentStudy.getName() + 
					" image " + (imageIndex + 1) + " of " + 
					totalImages;
		} else if (view.getCurrentView() == ViewType.QUAD_VIEW) {
			int lastImage = 0;
			if (totalImages - imageIndex <= 4) {
				//System.out.println(totalImages + " and " + imageIndex);
				lastImage = totalImages - 1;
			} else {
				lastImage = imageIndex + 4;
			}
			status = "Viewing " + currentStudy.getName() + 
					" images (" + (imageIndex + 1) + " - " + lastImage + ") of " + 
					currentStudy.getImageCount();
		}
		view.updateStatusBar(status);
	}
	
	/**
	 * Calls to the view to change the current view
	 *
	 */
	class ViewListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			view.toggleView();
			updateViewStatus();
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
			// Ensure studies are loaded
			loadStudies(false);
			
			// if no studies, skip
			if (studies == null) {
				return;
			}
			
			// Convert studies to a string array
			String[] studyNames = new String[studies.size()];
			int i = 0;
			for (Entry<String, Study> entry : studies.entrySet()) {
				studyNames[i++] = entry.getValue().getName();
			}
			
			view.showList(studyNames);  // Display the Study List window
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
			System.out.println("The current view is " + view.getCurrentView());
			System.out.println("The current images are " + currentImages);
			System.out.println("The current study is " + currentStudy);
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
			
			// Get total number of images
			int totalImages = currentStudy.getImageCount();
			// Get current view (send either one or four images)
			ViewType curView = view.getCurrentView();
			if (curView == ViewType.SINGLE_VIEW) {
				// If we've reached the beginning just return, otherwise, decrement
				if (imageIndex - 1 < 0) {
					return;
				} else {
					view.setImages(currentStudy.getImage(--imageIndex));
				}
			} else if (curView == ViewType.QUAD_VIEW) {
				// If we are at the beginning, don't decrement
				if (imageIndex <= 0) {
					return;
				}
				// Load the images
				imageIndex -= 4;
				for (int i = 0; i < 4; i++) {
					
					if (imageIndex + (i+1) >= totalImages) {
						currentImages[i] = null;
					} else {
						currentImages[i] = currentStudy.getImage(
								imageIndex + (i + 1));
					}
				}
				view.setImages(currentImages);
			}
			updateViewStatus();
		}
	}
	
	/**
	 * Moves to the next image
	 *
	 */
	class NextListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO When changing to quad view from one of the last three images, the program shows random images
			// Get total number of images
			int totalImages = currentStudy.getImageCount();
			// Get current view (send either one or four images)
			ViewType curView = view.getCurrentView();
			if (curView == ViewType.SINGLE_VIEW) {
				// If we've reached the end just return, otherwise, increment
				if (imageIndex + 1 >= totalImages) {
					return;
				} else {
					view.setImages(currentStudy.getImage(++imageIndex));
				}
			} else if (curView == ViewType.QUAD_VIEW) {
				// If there aren't more than four images left, don't increment
				if (totalImages - imageIndex <= 4) {
					return;
				}
				// Load the images
				imageIndex += 4;
				for (int i = 0; i < 4; i++) {
					// If the next image exceeds total images, leave it null
					if (imageIndex + (i+1) >= totalImages) {
						currentImages[i] = null;
					} else {
						currentImages[i] = currentStudy.getImage(
								imageIndex + (i + 1));
					}
				}
				view.setImages(currentImages);
			}
			updateViewStatus();
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
			String studyName = view.getSelectedStudy();
			if (studyName == null) {
				return;
			}
			if (studyName.equals("")) {
				return;
			}
			// Set the current study
			currentStudy = studies.get(studyName);
			if (view.isDefaultSelected()) {
				// TODO Save the default study with the settings manager
			}
			
			// Get the first images
			for (int i = 0; i < currentImages.length; i++) {
				currentImages[i] = currentStudy.getImage(i);
			}
			view.setImages(currentImages);
			updateViewStatus();
		}
	}

}

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
	private int imageInterval = 0;
	private int singleViewIndex = 0;
	
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
	
	/**
	 * updates the status bar on the view, should be called any time the 
	 * view, study or images are changed.
	 */
	private void updateViewStatus() {
		// Get current view (send either one or four images)
		ViewType curView = view.getCurrentView();
		int totalImages = currentStudy.getImageCount();
		String status = "";
		
		if (curView == ViewType.SINGLE_VIEW) {
			int imgIndex = imageInterval*4 + singleViewIndex;
			// Update the status
			status = "Viewing " + currentStudy.getName() + " image " + 
					(imgIndex + 1) + " of " + totalImages;
		} else if (curView == ViewType.QUAD_VIEW) {
			// Update the status
			int lastImage = 0;
			if (totalImages - imageInterval*4 <= 4) {
				lastImage = totalImages;
			} else {
				lastImage = imageInterval*4 + 4;
			}
			status = "Viewing " + currentStudy.getName() + 
					" images (" + (imageInterval*4 + 1) + " - " + lastImage + 
					") of " + currentStudy.getImageCount();
		}

		// update the status bar
		view.updateStatusBar(status);
	}
	
	/**
	 * updates the images on the GUI, should be called any time imageInterval
	 * or singleViewIndex are changed.
	 * @precondition imageInterval and singleViewIndex must be set previously
	 */
	private void updateViewImages() {
		
		// Only load images if singleViewIndex is 0 or 3, otherwise, keep using
		// the current interval of images already loaded.
		if (singleViewIndex == 0 || singleViewIndex == 3) {
			int totalImages = currentStudy.getImageCount();
			for (int i = 0; i < 4; i++) {
				int imgIndex = imageInterval*4 + i;
				if (imgIndex >= totalImages) {
					currentImages[i] = null;
				} else {
					currentImages[i] = currentStudy.getImage(imgIndex);
				}
			}
		}
		
		// Get current view (send either one or four images)
		ViewType curView = view.getCurrentView();
		
		if (curView == ViewType.SINGLE_VIEW) {
			// Update the image(s)
			view.setImages(currentImages[singleViewIndex]);
		} else if (curView == ViewType.QUAD_VIEW) {
			// Update the image(s)
			view.setImages(currentImages);
		}

		updateViewStatus();
	}

	
	/**
	 * Calls to the view to change the current view
	 *
	 */
	class ViewListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			view.toggleView();
			updateViewImages();
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
			int imgIndex = imageInterval*4 + singleViewIndex;
			
			// Get current view (send either one or four images)
			ViewType curView = view.getCurrentView();
			if (curView == ViewType.SINGLE_VIEW) {
				// If we've reached the beginning just return
				if (imgIndex == 0) {
					return;
				}
				
				// if we've reached the first single image in this interval
				// reset singleViewIndex and increment the interval
				if (singleViewIndex == 0) {
					imageInterval -= 1;
					singleViewIndex = 3;
				} else {
					// Otherwise just decrement the index
					singleViewIndex -= 1;
				}
			} else if (curView == ViewType.QUAD_VIEW) {
				// If we are at the beginning don't decrement
				if (imageInterval == 0) {
					return;
				}
				
				// increment imageInterval and reset singleViewIndex
				singleViewIndex = 3;
				imageInterval -= 1;
			}
			updateViewImages();
		}
	}
	
	/**
	 * Moves to the next image
	 *
	 */
	class NextListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			
			int totalImages = currentStudy.getImageCount();
			int imgIndex = imageInterval*4 + singleViewIndex;
			
			// Get current view (send either one or four images)
			ViewType curView = view.getCurrentView();
			if (curView == ViewType.SINGLE_VIEW) {
				// If we've reached the end just return
				if (imgIndex == totalImages) {
					return;
				}
				
				// if we've reached the last single image in this interval
				// reset singleViewIndex and increment the interval
				if (singleViewIndex == 3) {
					imageInterval += 1;
					singleViewIndex = 0;
				} else {
					// Otherwise just increment the index
					singleViewIndex += 1;
				}
			} else if (curView == ViewType.QUAD_VIEW) {
				// If there aren't more than four images left, we reached then 
				// end,, don't increment
				if (totalImages - imageInterval*4 <= 4) {
					return;
				}
				
				// increment imageInterval and reset singleViewIndex
				singleViewIndex = 0;
				imageInterval += 1;
			}
			updateViewImages();

		}
	}
	
	/**
	 * Gets the selected study and whether it was set as the default
	 *
	 */
	class StudySelectionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			
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
			imageInterval = 0;
			singleViewIndex = 0;
			updateViewImages();;
		}
	}

}

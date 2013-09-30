package mivc.System;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

import mivc.System.IO.StudyDAO;
import mivc.UI.StudyView;
import mivc.UI.StudyView.ViewType;


/**
 * @author Colin?
 * @author Fill me
 * @author act4122
 */
public class Controller {

	private StudyView view;
	private Study currentStudy;
	private IStudyImage[] currentImages = new IStudyImage[4];
	private HashMap<String, Study> studies;
	private int imageInterval = 0;
	private int singleViewIndex = 0;
	private static final String SETTINGS_PATH = "settings.prop";
	private static final String DEFAULT_STUDY_KEY = "default_study";
	
	// The following properties are to be concatenated with the study name
	// ex. settings.set(currentStudy.getName() + IMAGE_INTERVAL_KEY, imageInterval)
	// The reasoning is that we need to be able to store view data for multiple
	// studies so the study name is the unique identifier for the key
	private static final String IMAGE_INTERVAL_KEY = "_image_interval";
	private static final String SINGLE_VIEW_INDEX_KEY = "_sv_index";
	private static final String VIEW_TYPE_KEY = "_view_type";
	private static final String DISPLAY_STATE_SAVED_KEY = "_display_state";
	
	/**
	 * Proxy controller is the brains for the GUI.  Currently it is designed
	 * to work with a Java GUI but could be extended.
	 * @param view The view that this controller will be controlling.
	 */
	public Controller(StudyView view) {
		this.view = view;
		view.addViewListener(new ViewListener());
		view.addOpenListener(new OpenListener());
		view.addSaveStudyListener(new SaveStudyListener());
		view.addSaveViewListener(new SaveViewListener());
		view.addNextListener(new NextListener());
		view.addPrevListener(new PrevListener());
		view.addStudySelectionListener(new StudySelectionListener());	
		
		initialize();
	}
	
	/**
	 * Sets up the initial system by loading studies, settings and the default
	 * study if there is one.
	 */
	private void initialize() {
		// Load studies
		loadStudies(true);
		
		// Load settings
		LocalSettingsManager sMan = LocalSettingsManager.getInstance();
		sMan.Load(SETTINGS_PATH);
		
		// Load default study if there is one, otherwise, show the list
		String studyName = sMan.getString(DEFAULT_STUDY_KEY);
		if (studyName == null) {
			showStudiesListView();
		} else {
			updateCurrentStudy(studyName);
		}
	}
	
	private void showStudiesListView() {
		// Convert studies to a string array
		String[] studyNames = new String[studies.size()];
		int i = 0;
		for (Entry<String, Study> entry : studies.entrySet()) {
			studyNames[i++] = entry.getValue().getName();
		}
		
		view.showList(studyNames);  // Display the Study List window
	}
	
	/**
	 * Loads the studies from persistent source, if the studies are already
	 * loaded nothing will change unless forceReload is set to true.  However,
	 * if the studies have not been loaded it will load regardless of forceReload
	 * @param forceReload whether or not to force reloading studies
	 */
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
	 * @param forceUpdate should the update of images be forced
	 * @precondition imageInterval and singleViewIndex must be set previously
	 */
	private void updateViewImages(boolean forceUpdate) {
		
		// Only load images if singleViewIndex is 0 or 3, otherwise, keep using
		// the current interval of images already loaded.
		if (singleViewIndex == 0 || singleViewIndex == 3 || forceUpdate) {
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
	 * Ensures the study attempting to be chosen is valid and if so, sets that
	 * study while also resetting the image cursors to the first image.  If the
	 * name passed in .equals("") or == null the system remains status quo.
	 * @param studyName the name of the desired study
	 */
	private void updateCurrentStudy(String studyName) {
		if (studyName == null) {
			return;
		}
		if (studyName.equals("")) {
			return;
		}
		loadStudies(false);
		// Set the current study
		currentStudy = studies.get(studyName);
		
		// Check if there is a persisted study display state, if there is, load
		// the persisted view and images for that study but if not, just load
		// the first images
		LocalSettingsManager sMan = LocalSettingsManager.getInstance();
		if (sMan.getBoolean(currentStudy.getName() + DISPLAY_STATE_SAVED_KEY)) {
			// Get the saved display state info
			
			// Get the view type
			ViewType vType = ViewType.valueOf(sMan.getString(
					studyName + VIEW_TYPE_KEY));
			// If the view type is different, toggle the view so it isn't
			if (vType != view.getCurrentView()) {
				view.toggleView();
			}
			
			// Get the image variable data
			imageInterval = sMan.getInt(studyName + IMAGE_INTERVAL_KEY);
			singleViewIndex = sMan.getInt(studyName + SINGLE_VIEW_INDEX_KEY);
			updateViewImages(true);
		} else {
			// Get the first images
			imageInterval = 0;
			singleViewIndex = 0;
			updateViewImages(false);
		}
		
	}

	
	/**
	 * Calls to the view to change the current view
	 *
	 */
	class ViewListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			view.toggleView();
			updateViewImages(false);
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
			
			showStudiesListView();
		}
	}
	
	/**
	 * Saves a new study.
	 *
	 */
	class SaveStudyListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			
			// Local variable to see if the study is to be saved.
			boolean s_create = false;
			
			String newStudy = view.getInput("Please enter a study name");
			
			// Don't do anything if they didn't enter a name
			if (newStudy == null || newStudy.equals("")) {
				return;
			}
			
			if (new java.io.File("./studies/" + newStudy).exists()) {
				s_create = view.getWarningConfirmation(
						"Would you like to overwrite?");
			} else { // Create if there is no study found already
				s_create = true;
			}
			
			// If the creation succeeds then save the study
			if (s_create) {
				StudyDAO.getInstance().saveStudy(
						currentStudy.getName(), 
						newStudy);
				// update the list of studies
				loadStudies(true);
				// Set the current study to the new study
				updateCurrentStudy(newStudy);
			}
		}
	}
	
	/**
	 * Gathers the pertinent information from the view in order to save it.
	 *
	 */
	class SaveViewListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// Don't bother if they havn't selected a study
			if (currentStudy == null) {
				return;
			}
			LocalSettingsManager sMan = LocalSettingsManager.getInstance();
			sMan.set(currentStudy.getName() + IMAGE_INTERVAL_KEY, 
					imageInterval);
			sMan.set(currentStudy.getName() + SINGLE_VIEW_INDEX_KEY, 
					singleViewIndex);
			// This next value will need to be recreated as a ViewType 
			// using ViewType.valueOf(...)
			sMan.set(currentStudy.getName() + VIEW_TYPE_KEY, 
					view.getCurrentView().toString());
			sMan.set(currentStudy.getName() + DISPLAY_STATE_SAVED_KEY, true);
			// Save the settings
			sMan.Save(SETTINGS_PATH);
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
			updateViewImages(false);
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
				if (imgIndex >= totalImages - 1) {
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
			updateViewImages(false);

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
			updateCurrentStudy(studyName);
			
			// Set it as default if so desired by the GUI
			if (view.isDefaultSelected()) {
				LocalSettingsManager sMan = LocalSettingsManager.getInstance();
				sMan.set(DEFAULT_STUDY_KEY, studyName);
				sMan.Save(SETTINGS_PATH);
			}
		}
	}

}

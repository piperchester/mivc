package mivc.System;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import mivc.System.IO.StudyDAO;


/**
 * @author Colin?
 * @author Fill me
 * @author act4122
 */
public class CommandHandler {

	private HashMap<String, Study> studies;
	private Stack<IUndoableCommand> undoOperations;
	private Stack<IUndoableCommand> redoOperations;
	
	
	/**
	 * CommandHandler...
	 */
	public CommandHandler() {

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
	
	// TODO Add Javadoc.
	public void addCommand(ICommand command) {
		// If this command is undoable, store if for possible undoing later.
		if (command instanceof IUndoableCommand) {
			if (undoOperations == null) {
				undoOperations = new Stack<IUndoableCommand>();
			}
			// Once a new command is pushed to undo we no longer need redos
			redoOperations = null;
			undoOperations.push((IUndoableCommand) command);
		} else {
			// If a command is run that is not undoable, clear the undo list
			undoOperations = null;
		}
		// Once a new command is executed we can empty the redo operations
		redoOperations = null;
		// Execute the command
		command.execute();
	}
	
	// TODO Add Javadoc
	public void undo() {
		if (undoOperations == null) {
			return;
		}
		if (undoOperations.size() <= 0) {
			return;
		}
		if (redoOperations == null) {
			redoOperations = new Stack<IUndoableCommand>();
		}
		
		IUndoableCommand cmd = undoOperations.pop();
		redoOperations.push(cmd);
		cmd.undo();
	}
	
	// TODO Add Javadoc
	public void redo() {
		if (redoOperations == null) {
			return;
		}
		if (redoOperations.size() <= 0) {
			return;
		}
		if (undoOperations == null) {
			undoOperations = new Stack<IUndoableCommand>();
		}
		
		IUndoableCommand cmd = redoOperations.pop();
		undoOperations.push(cmd);
		((ICommand)cmd).execute();
	}

	
//	/**
//	 * Ensures the study attempting to be chosen is valid and if so, sets that
//	 * study while also resetting the image cursors to the first image.  If the
//	 * name passed in .equals("") or == null the system remains status quo.
//	 * @param studyName the name of the desired study
//	 */
//	private void updateCurrentStudy(String studyName) {
//		if (studyName == null) {
//			return;
//		}
//		if (studyName.equals("")) {
//			return;
//		}
//		loadStudies(false);
//		// Set the current study
//		currentStudy = studies.get(studyName);
//		
//		// Check if there is a persisted study display state, if there is, load
//		// the persisted view and images for that study but if not, just load
//		// the first images
//		LocalSettingsManager sMan = LocalSettingsManager.getInstance();
//		if (sMan.getBoolean(currentStudy.getName() + DISPLAY_STATE_SAVED_KEY)) {
//			// Get the saved display state info
//			
//			// Get the view type
//			ViewType vType = ViewType.valueOf(sMan.getString(
//					studyName + VIEW_TYPE_KEY));
//			// If the view type is different, toggle the view so it isn't
//			if (vType != view.getCurrentView()) {
//				view.toggleView();
//			}
//			
//			// Get the image variable data
//			imageInterval = sMan.getInt(studyName + IMAGE_INTERVAL_KEY);
//			singleViewIndex = sMan.getInt(studyName + SINGLE_VIEW_INDEX_KEY);
//			updateViewImages(true);
//		} else {
//			// Get the first images
//			imageInterval = 0;
//			singleViewIndex = 0;
//			updateViewImages(false);
//		}
//	}
	

//	/**
//	 * Saves a new study.
//	 *
//	 */
//	class SaveStudyListener implements ActionListener {
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			
//			// Local variable to see if the study is to be saved.
//			boolean s_create = false;
//			
//			String newStudy = view.getInput("Please enter a study name");
//			
//			// Don't do anything if they didn't enter a name
//			if (newStudy == null || newStudy.equals("")) {
//				return;
//			}
//			
//			if (new java.io.File("./studies/" + newStudy).exists()) {
//				s_create = view.getWarningConfirmation(
//						"Would you like to overwrite?");
//			} else { // Create if there is no study found already
//				s_create = true;
//			}
//			
//			// If the creation succeeds then save the study
//			if (s_create) {
//				StudyDAO.getInstance().saveStudy(
//						currentStudy.getName(), 
//						newStudy);
//				// update the list of studies
//				loadStudies(true);
//				// Set the current study to the new study
//				updateCurrentStudy(newStudy);
//			}
//		}
//	}
//	
//	/**
//	 * Gathers the pertinent information from the view in order to save it.
//	 *
//	 */
//	class SaveViewListener implements ActionListener {
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			// Don't bother if they havn't selected a study
//			if (currentStudy == null) {
//				return;
//			}
//			LocalSettingsManager sMan = LocalSettingsManager.getInstance();
//			sMan.set(currentStudy.getName() + IMAGE_INTERVAL_KEY, 
//					imageInterval);
//			sMan.set(currentStudy.getName() + SINGLE_VIEW_INDEX_KEY, 
//					singleViewIndex);
//			// This next value will need to be recreated as a ViewType 
//			// using ViewType.valueOf(...)
//			sMan.set(currentStudy.getName() + VIEW_TYPE_KEY, 
//					view.getCurrentView().toString());
//			sMan.set(currentStudy.getName() + DISPLAY_STATE_SAVED_KEY, true);
//			// Save the settings
//			sMan.Save(SETTINGS_PATH);
//		}
//	}
//	

}

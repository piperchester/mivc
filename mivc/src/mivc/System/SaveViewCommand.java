package mivc.System;

import mivc.System.IO.LocalSettings;
import mivc.UI.StudyView;

/**
 * The responsibility of this class is to save the necessary information 
 * to bring up a default view state when a specific view is selected.  
 * This class does this by saving the GUI’s current view state (single or 
 * quad), the current image index, and the current study being viewed.
 * 
 * @author berlgeof
 */
public class SaveViewCommand implements ICommand {

	private StudyView receiver;
	
	/**
	 * Single Arg constructor provides the reciever for the command
	 * @param receiver the recipient for this command's actions.
	 */
	public SaveViewCommand(StudyView receiver) {
		this.receiver = receiver;
	}
	
	/*
	 * (non-Javadoc)
	 * @see mivc.System.ICommand#execute()
	 */
	@Override
	public void execute() {
		Study currentStudy = receiver.getCurrentStudy();
		// Don't bother if they havn't selected a study
		if (currentStudy == null) {
			return;
		}
		LocalSettings.getInstance().set(currentStudy.getName() + 
				StudyView.IMAGE_INTERVAL_KEY, 
				receiver.getImageInterval());
		LocalSettings.getInstance().set(currentStudy.getName() + 
				StudyView.SINGLE_VIEW_INDEX_KEY, 
				receiver.getSingleViewIndex());
		// This next value will need to be recreated as a ViewType 
		// using ViewType.valueOf(...)
		LocalSettings.getInstance().set(currentStudy.getName() + 
				StudyView.VIEW_TYPE_KEY, 
				receiver.getCurrentView().toString());
		LocalSettings.getInstance().set(currentStudy.getName() + 
				StudyView.DISPLAY_STATE_SAVED_KEY, true);
		// Save the settings
		LocalSettings.getInstance().Save(LocalSettings.SETTINGS_PATH);
	}

}

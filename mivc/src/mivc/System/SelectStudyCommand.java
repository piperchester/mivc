package mivc.System;

import mivc.UI.StudyView;
import mivc.UI.StudyView.ViewType;

public class SelectStudyCommand implements ICommand {

	private StudyView receiver;
	private String studyName;
	
	public SelectStudyCommand(StudyView receiver, String sName) {
		this.receiver = receiver;
		this.studyName = sName;
	}
	
	@Override
	public void execute() {
		if (studyName == null) {
			return;
		}
		if (studyName.equals("")) {
			return;
		}
		// Set the current study
		receiver.setCurrentStudy(studyName);
		
		// Check if there is a persisted study display state, if there is, load
		// the persisted view and images for that study but if not, just load
		// the first images
		
		if (LocalSettings.getInstance().getBoolean(studyName + 
				StudyView.DISPLAY_STATE_SAVED_KEY)) {
			// Get the saved display state info
			
			// Get the view type
			ViewType vType = ViewType.valueOf(LocalSettings.getInstance().getString(
					studyName + StudyView.VIEW_TYPE_KEY));
			// If the view type is different, toggle the view so it isn't
			if (vType != receiver.getCurrentView()) {
				receiver.toggleView();
			}
			
			// Get the image variable data
			receiver.setImageInterval(LocalSettings.getInstance().getInt(studyName + 
					StudyView.IMAGE_INTERVAL_KEY));
			receiver.setSingleViewIndex(LocalSettings.getInstance().getInt(studyName + 
					StudyView.SINGLE_VIEW_INDEX_KEY));
		} else {
			// Get the first images
			receiver.setImageInterval(0);
			receiver.setSingleViewIndex(0);
		}
	}

}

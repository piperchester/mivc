package mivc.System;

import mivc.UI.StudyView;

public class SaveViewCommand implements ICommand {

	private StudyView receiver;
	
	public SaveViewCommand(StudyView receiver) {
		this.receiver = receiver;
	}
	
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

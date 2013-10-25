package mivc.System;

import mivc.System.IO.StudyDAO;
import mivc.UI.StudyView;

public class StartUpCommand implements ICommand {

	private StudyView receiver;
	
	public StartUpCommand(StudyView receiver) {
		this.receiver = receiver;
	}
	
	@Override
	public void execute() {
		// Load settings
		LocalSettings.getInstance().Load(CommandHandler.SETTINGS_PATH);
		
		// Get default study name if there is one
		String studyName = LocalSettings.getInstance()
						.getString(StudyView.DEFAULT_STUDY_KEY);
		
		if (studyName != null) {
			// Update the studies list
			receiver.updateStudies(StudyDAO.getInstance().listStudies());
			// Set the default study to the current study by running the SelectStudyCommand
			new SelectStudyCommand(receiver, studyName, false).execute();
		}
	}

}

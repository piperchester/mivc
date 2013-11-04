package mivc.System;

import mivc.System.IO.LocalSettings;
import mivc.System.IO.StudyDAO;
import mivc.UI.StudyView;
import mivc.UI.StudyView.ReconstructionType;

public class StartUpCommand implements ICommand {

	private StudyView receiver;
	
	public StartUpCommand(StudyView receiver) {
		this.receiver = receiver;
	}
	
	@Override
	public void execute() {
		// Default to the Axial view
		receiver.updateImageType(ReconstructionType.AXIAL);
		receiver.updateImageProcurator(new AxialImageGetter());
		
		// Load settings
		LocalSettings.getInstance().Load(LocalSettings.SETTINGS_PATH);
		
		// Get default study name if there is one
		String studyName = LocalSettings.getInstance()
						.getString(StudyView.DEFAULT_STUDY_KEY);
		
		// Update the studies list
		receiver.updateStudies(StudyDAO.getInstance().listStudies());
		if (studyName != null) {
			// Set the default study to the current study by running the SelectStudyCommand
			new SelectStudyCommand(receiver, studyName, false).execute();
		} else {
			receiver.showList();
		}
	}

}

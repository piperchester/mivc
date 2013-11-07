package mivc.System;

import mivc.System.IO.LocalSettings;
import mivc.System.IO.StudyDAO;
import mivc.UI.StudyView;
import mivc.UI.StudyView.ReconstructionType;

/**
 * This command is called when the system is initialized for the first 
 * time. Its main responsibility is to load the studies from persistence 
 * and provide them to the GUI.  If there is a default study, that study 
 * will be sent to the GUI, otherwise, the GUI will be prompted to show a 
 * list of studies for selection.  This class is also responsibile for 
 * loading any previous settings from LocalSettings.
 * 
 * @author berlgeof
 */

public class StartUpCommand implements ICommand {

	private StudyView receiver;
	
	/**
	 * Single Arg constructor provides the reciever for the command
	 * @param receiver the recipient for this command's actions.
	 */
	public StartUpCommand(StudyView receiver) {
		this.receiver = receiver;
	}
	
	/*
	 * (non-Javadoc)
	 * @see mivc.System.ICommand#execute()
	 */
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

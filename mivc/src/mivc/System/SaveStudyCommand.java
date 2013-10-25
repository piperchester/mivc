package mivc.System;

import mivc.System.IO.StudyDAO;
import mivc.UI.StudyView;

public class SaveStudyCommand implements ICommand {

	private StudyView receiver;
	
	public SaveStudyCommand(StudyView receiver) {
		this.receiver = receiver;
	}
	
	@Override
	public void execute() {
		// Local variable to see if the study is to be saved.
		boolean s_create = false;
		
		String newStudy = receiver.getInput("Please enter a study name");
		
		// Don't do anything if they didn't enter a name
		if (newStudy == null || newStudy.equals("")) {
			return;
		}
		
		if (new java.io.File("./studies/" + newStudy).exists()) {
			s_create = receiver.getWarningConfirmation(
					"Would you like to overwrite?");
		} else { // Create if there is no study found already
			s_create = true;
		}
		
		// If the creation succeeds then save the study
		if (s_create) {
			StudyDAO.getInstance().saveStudy(
					receiver.getCurrentStudy().getName(), 
					newStudy);
			// update the list of studies
			receiver.updateStudies(StudyDAO.getInstance().listStudies());
			// Set the current study to the new study
			new SelectStudyCommand(receiver, newStudy, false);
		}
	}

}

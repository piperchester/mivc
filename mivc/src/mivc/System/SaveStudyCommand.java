package mivc.System;

import mivc.System.IO.StudyDAO;
import mivc.UI.StudyView;

/**
 * The responsibility of this class is to provide functionality for saving 
 * a study as a new name or saving over an existing study.  If a new name 
 * is chosen, no messages are provided however, if the same name is chosen, 
 * a warning confirmation message appears to confirm the overwrite.  This 
 * class will also trigger the GUI to open the newly saved study.
 * 
 * @author berlgeof
 */
public class SaveStudyCommand implements ICommand {

	private StudyView receiver;
	
	/**
	 * Single Arg constructor provides the reciever for the command
	 * @param receiver the recipient for this command's actions.
	 */
	public SaveStudyCommand(StudyView receiver) {
		this.receiver = receiver;
	}
	
	/*
	 * (non-Javadoc)
	 * @see mivc.System.ICommand#execute()
	 */
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

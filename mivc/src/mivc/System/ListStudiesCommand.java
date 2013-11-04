package mivc.System;

import mivc.System.IO.StudyDAO;
import mivc.UI.StudyView;

/**
 * The responsibility of this class is to provide an updated list of studies 
 * to the GUI and notify the GUI to display a list of studies for the user 
 * to select.
 * 
 * @author berlgeof
 */
public class ListStudiesCommand implements ICommand {

	private StudyView receiver;
	
	/**
	 * Single Arg constructor provides the reciever for the command
	 * @param receiver the recipient for this command's actions.
	 */
	public ListStudiesCommand(StudyView receiver) {
		this.receiver = receiver;
	}
	
	/*
	 * (non-Javadoc)
	 * @see mivc.System.ICommand#execute()
	 */
	@Override
	public void execute() {
		// Update the studies list
		receiver.updateStudies(StudyDAO.getInstance().listStudies());
		// Display the Study List window
		receiver.showList();  
	}
}

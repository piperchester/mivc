package mivc.System;

import mivc.UI.StudyView;

/**
 * The sole responsibilty of this class is to notify the view to update 
 * its display as well as update the view’s status bar to indicate the 
 * user is viewing either four or one single image.
 * 
 * @author berlgeof
 */
public class ChangeViewCommand implements ICommand, IUndoableCommand {

	private StudyView receiver;
	
	/**
	 * Single Arg constructor provides the reciever for the command
	 * @param receiver the recipient for this command's actions.
	 */
	public ChangeViewCommand(StudyView receiver) {
		this.receiver = receiver;
	}
	
	/*
	 * (non-Javadoc)
	 * @see mivc.System.ICommand#execute()
	 */
	@Override
	public void execute() {
		receiver.toggleView();
		// TODO Need to change the images and the status bar somewhere
	}

	/*
	 * (non-Javadoc)
	 * @see mivc.System.IUndoableCommand#undo()
	 */
	@Override
	public void undo() {
		receiver.toggleView();
	}

}

package mivc.System;

import mivc.UI.StudyView;

public class ChangeViewCommand implements ICommand, IUndoableCommand {

	private StudyView receiver;
	
	public ChangeViewCommand(StudyView receiver) {
		this.receiver = receiver;
	}
	
	@Override
	public void execute() {
		receiver.toggleView();
		// TODO Need to change the images and the status bar somewhere
	}

	@Override
	public void undo() {
		receiver.toggleView();
	}

}

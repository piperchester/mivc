package mivc.System;

import mivc.UI.StudyView;

public class SaveViewCommand implements ICommand {

	private StudyView receiver;
	
	public SaveViewCommand(StudyView receiver) {
		this.receiver = receiver;
	}
	
	@Override
	public void execute() {

	}

}

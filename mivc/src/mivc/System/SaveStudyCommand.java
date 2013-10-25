package mivc.System;

import mivc.UI.StudyView;

public class SaveStudyCommand implements ICommand {

	private StudyView receiver;
	
	public SaveStudyCommand(StudyView receiver) {
		this.receiver = receiver;
	}
	
	@Override
	public void execute() {

	}

}

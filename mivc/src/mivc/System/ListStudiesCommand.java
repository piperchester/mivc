package mivc.System;

import mivc.System.IO.StudyDAO;
import mivc.UI.StudyView;

public class ListStudiesCommand implements ICommand {

	private StudyView receiver;
	
	public ListStudiesCommand(StudyView receiver) {
		this.receiver = receiver;
	}
	
	@Override
	public void execute() {
		// Display the Study List window
		receiver.showList(StudyDAO.getInstance().listStudies());  
	}
}

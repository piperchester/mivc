package mivc.System;

import mivc.UI.StudyView;
import mivc.UI.StudyView.ReconstructionType;

public class ChangeReconstructionCommand implements ICommand {

	private StudyView receiver;
	
	public ChangeReconstructionCommand(StudyView receiver) {
		this.receiver = receiver;
	}
	
	@Override
	public void execute() {
		ReconstructionType imageType = receiver.getCurrentImageType();
		
		// depending on the current type (Could avoid this with State Pattern)
		// Update the ImageProcurator strategy
		if (imageType == ReconstructionType.AXIAL) {
			receiver.updateImageProcurator(new SagitalProcurator());
			receiver.updateImageType(ReconstructionType.SAGITAL);
		} else if (imageType == ReconstructionType.SAGITAL) {
			receiver.updateImageProcurator(new CoronalProcurator());
			receiver.updateImageType(ReconstructionType.CORONAL);
		} else if (imageType == ReconstructionType.CORONAL) {
			receiver.updateImageProcurator(new AxialProcurator());
			receiver.updateImageType(ReconstructionType.AXIAL);
		}
		// Set the images back to 0 for safe processing.
		receiver.setImageIndexing(0, 0);
		
	}


}

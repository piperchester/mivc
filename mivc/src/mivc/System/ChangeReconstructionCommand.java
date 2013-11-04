package mivc.System;

import mivc.UI.StudyView;
import mivc.UI.StudyView.ReconstructionType;

/**
 * The main responsibility of this class is to provide the receiver with 
 * the appropriate strategy for obtaining images based on the selected 
 * reconstruction.  Once the strategy is updated, the imaging indexes are 
 * set to zero which means when a new reconstruction is selected, the 
 * images will always start back at the beginning of the study.
 * 
 * @author berlgeof
 */
public class ChangeReconstructionCommand implements ICommand {

	private StudyView receiver;
	
	/**
	 * Single Arg constructor provides the reciever for the command
	 * @param receiver the recipient for this command's actions.
	 */
	public ChangeReconstructionCommand(StudyView receiver) {
		this.receiver = receiver;
	}
	
	/*
	 * (non-Javadoc)
	 * @see mivc.System.ICommand#execute()
	 */
	@Override
	public void execute() {
		ReconstructionType imageType = receiver.getCurrentImageType();
		
		// depending on the current type (Could avoid this with State Pattern)
		// Update the ImageProcurator strategy
		if (imageType == ReconstructionType.AXIAL) {
			receiver.updateImageProcurator(new SagitalImageGetter());
			receiver.updateImageType(ReconstructionType.SAGITAL);
		} else if (imageType == ReconstructionType.SAGITAL) {
			receiver.updateImageProcurator(new CoronalImageGetter());
			receiver.updateImageType(ReconstructionType.CORONAL);
		} else if (imageType == ReconstructionType.CORONAL) {
			receiver.updateImageProcurator(new AxialImageGetter());
			receiver.updateImageType(ReconstructionType.AXIAL);
		}
		// Set the images back to 0 for safe processing.
		receiver.setImageIndexing(0, 0);
		
	}


}

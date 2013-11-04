package mivc.System;

import mivc.UI.StudyView;
import mivc.UI.StudyView.ViewType;

/**
 * The responsibility of this class is to provide the GUI with the 
 * necessary data to acquire the previous image in the study.  If the 
 * beginning of the list of images has already been reached, the 
 * command stops execution before notifying the receiver.  This class 
 * also uses the GUI’s current view to determine whether to move by 
 * increments of one or four.
 * 
 * @author berlgeof
 */
public class PrevImageCommand implements ICommand, IUndoableCommand {

	private StudyView receiver;
	
	/**
	 * Single Arg constructor provides the reciever for the command
	 * @param receiver the recipient for this command's actions.
	 */
	public PrevImageCommand(StudyView receiver) {
		this.receiver = receiver;
	}
	
	/*
	 * (non-Javadoc)
	 * @see mivc.System.ICommand#execute()
	 */
	@Override
	public void execute() {
		int interval = receiver.getImageInterval();
		int index = receiver.getSingleViewIndex();
		ViewType currentView = receiver.getCurrentView();
		
		int imgIndex = interval*4 + index;
		
		// Get current view (send either one or four images)
		if (currentView == ViewType.SINGLE_VIEW) {
			// If we've reached the beginning just return
			if (imgIndex == 0) {
				return;
			}
			
			// if we've reached the first single image in this interval
			// reset singleViewIndex and increment the interval
			if (index == 0) {
				interval -= 1;
				index = 3;
			} else {
				// Otherwise just decrement the index
				index -= 1;
			}
		} else if (currentView == ViewType.QUAD_VIEW) {
			// If we are at the beginning don't decrement
			if (interval == 0) {
				return;
			}
			
			// increment imageInterval and reset singleViewIndex
			index = 3;
			interval -= 1;
		}
		
		// Update the receiver with the new values
		receiver.setImageIndexing(interval, index);
	}
	
	/*
	 * (non-Javadoc)
	 * @see mivc.System.IUndoableCommand#undo()
	 */
	@Override
	public void undo() {
		int interval = receiver.getImageInterval();
		int index = receiver.getSingleViewIndex();
		ViewType currentView = receiver.getCurrentView();
		
		
		int totalImages = receiver.getCurrentStudy().getImageCount();
		int imgIndex = interval*4 + index;
		
		// Get current view (send either one or four images)
		if (currentView == ViewType.SINGLE_VIEW) {
			// If we've reached the end just return
			if (imgIndex >= totalImages - 1) {
				return;
			}
			
			// if we've reached the last single image in this interval
			// reset singleViewIndex and increment the interval
			if (index == 3) {
				interval += 1;
				index = 0;
			} else {
				// Otherwise just increment the index
				index += 1;
			}
		} else if (currentView == ViewType.QUAD_VIEW) {
			// If there aren't more than four images left, we reached then 
			// end,, don't increment
			if (totalImages - interval*4 <= 4) {
				return;
			}
			
			// increment imageInterval and reset singleViewIndex
			index = 0;
			interval += 1;
		}

		// Update the receiver with the new values
		receiver.setImageIndexing(interval, index);
	}

}

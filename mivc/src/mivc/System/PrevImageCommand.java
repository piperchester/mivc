package mivc.System;

import mivc.UI.StudyView;
import mivc.UI.StudyView.ViewType;

public class PrevImageCommand implements ICommand, IUndoableCommand {

	private StudyView receiver;
	
	public PrevImageCommand(StudyView receiver) {
		this.receiver = receiver;
	}
	
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

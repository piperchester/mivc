package mivc.System;

import java.awt.image.BufferedImage;

/**
 * The main responsibility of this interface is for our strategy pattern.  
 * It is used to govern the methods implemented by each concrete strategy. 
 * Specifically, the strategies involved pertain to acquiring images for 
 * a reconstruction of a set of images.
 * 
 * @author berlgeof
 */
public interface ImageGetter {

	/**
	 * This method will return the appropriate image for the current view.  
	 * Depending on which view is currently displayed there may be several ways
	 * to display an image.
	 * @param index the index of the image to display
	 * @param study the study from which to acquire the image
	 * @param min a minimum windowing value
	 * @param max a maximum windowing value
	 * @return the appropriate image with windowing applied if necessary
	 */
	public BufferedImage getReconstructedImage(int index, Study study, int min, 
			int max);
	
	/**
	 * This method will return the first indexed image from the Axial view but
	 * the image will have a line overlayed on it to depict a reference for the
	 * reconstructed image.  If the axial view is being displayed, the same
	 * axial image will be return by this method since no reconstruction was
	 * done.
	 * @param index the current reconstructed image being viewed
	 * @param study the current study viewing the images from
	 * @return an image with a reference overlay.
	 */
	public BufferedImage getReferenceImage(int index, Study study);
	
}

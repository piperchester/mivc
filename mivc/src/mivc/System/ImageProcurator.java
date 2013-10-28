package mivc.System;

import java.awt.image.BufferedImage;

public interface ImageProcurator {

	public BufferedImage getReconstructedImage(int index, Study study, int min, 
			int max);
	
	/**
	 * This method will return the first indexed image from the Axial view but
	 * the image will have a line overlayed on it to depict a reference for the
	 * reconstructed image.
	 * @param index the current reconstructed image being viewed
	 * @param study the current study viewing the images from
	 * @return an image with a reference overlay.
	 */
	public BufferedImage getReferenceImage(int index, Study study);
	
}

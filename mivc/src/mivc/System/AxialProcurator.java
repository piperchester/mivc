package mivc.System;

import java.awt.image.BufferedImage;

public class AxialProcurator implements ImageProcurator {

	@Override
	public BufferedImage getReconstructedImage(int index, Study study, int min, int max) {
		BufferedImage retVal = new BufferedImage(study.getMaxX(), 
				study.getMaxY(),
				BufferedImage.TYPE_INT_RGB);
		
    	for (int x = 0; x < retVal.getWidth(); x++) {
    		for (int y = 0; y < retVal.getHeight(); y++) {
    			// Store the current x, y data into the array
    			retVal.setRGB(x, y, study.getPixel(x, y, index));
    		}
    	}
    	
    	if (min > 0 || max < 255) {
    		return ImageUtil.windowImage(retVal, min, max);
    	} else {
    		return retVal;
    	}
	}

	/*
	 * (non-Javadoc)
	 * @see mivc.System.ImageProcurator#getReferenceImage(int, mivc.System.Study)
	 */
	@Override
	public BufferedImage getReferenceImage(int index, Study study) {
		BufferedImage retVal = new BufferedImage(study.getMaxX(), 
				study.getMaxY(),
				BufferedImage.TYPE_INT_RGB);
		
    	for (int x = 0; x < retVal.getWidth(); x++) {
    		for (int y = 0; y < retVal.getHeight(); y++) {
    			// Store the current x, y data into the array
    			retVal.setRGB(x, y, study.getPixel(x, y, index));
    		}
    	}
    	
    	return retVal;
	}

}

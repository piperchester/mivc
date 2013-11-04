package mivc.System;

import java.awt.image.BufferedImage;

/**
 * The main responsibility of this class is to get image reconstructions 
 * from an axial perspective.  This class contains two methods which are 
 * called from the client.  One method returns an image based on a given 
 * index.  The other method returns a modified reference image.  This 
 * image is always an axial image and has a yellow reference line painted 
 * over it to show a reference of the reconstruction when compared to an 
 * axial image.  In the case of this class specifically, the method 
 * returns the same image as the one being viewed since Axial is the 
 * default and there is now reconstruction done.
 * 
 * @author berlgeof
 */
public class AxialImageGetter implements ImageGetter {

	/*
	 * (non-Javadoc)
	 * @see mivc.System.ImageGetter#getReconstructedImage(int, mivc.System.Study, int, int)
	 */
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

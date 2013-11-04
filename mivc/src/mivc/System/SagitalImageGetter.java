package mivc.System;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/**
 * The main responsibility of this class is to get image reconstructions 
 * from an axial perspective.  This class contains two methods which are 
 * called from the client.  One method returns an image based on a given 
 * index.  The other method returns a modified reference image.  This 
 * image is always an axial image and has a yellow reference line painted 
 * over it to show a reference of the reconstruction when compared to an 
 * axial image.
 * 
 * @author berlgeof
 */
public class SagitalImageGetter implements ImageGetter {

	@Override
	public BufferedImage getReconstructedImage(int index, Study study, int min, int max) {
		// Make an image that is as tall as our normal images but only as wide as
		// the number of images in our layered stack.
		BufferedImage retVal = new BufferedImage(study.getMaxZ(), 
				study.getMaxY(), BufferedImage.TYPE_INT_RGB);
		

		// For each image in the list, grab that column of y values
		for (int z = 0; z < study.getMaxZ(); z++) {
			for (int y = 0; y < retVal.getHeight(); y++) {
				retVal.setRGB(z, y, study.getPixel(index, y, z));
			}
		}
		
		
		int newHeight = retVal.getHeight();
		int newWidth = retVal.getWidth()*study.getMaxY()/study.getMaxZ();
		BufferedImage resized = new BufferedImage(newWidth, newHeight, 
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = resized.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2.drawImage(retVal, 0, 0, newWidth, newHeight, null);
		g2.dispose();
		
		// Translate the image (flip it)
		AffineTransform rotate = new AffineTransform();
		rotate.rotate(Math.toRadians(-90.0), newWidth/2, newHeight/2);
		AffineTransformOp op = new AffineTransformOp(rotate, 
				AffineTransformOp.TYPE_BILINEAR);
		resized = op.filter(resized, null);
		
    	if (min > 0 || max < 255) {
    		return ImageUtil.windowImage(resized, min, max);
    	} else {
    		return resized;
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
    			retVal.setRGB(x, y, study.getPixel(x, y, 0));
    		}
    	}
    	
    	Graphics2D g2 = retVal.createGraphics();
    	g2.setColor(Color.yellow);
    	Stroke brush = new BasicStroke(4);
    	g2.setStroke(brush);
    	g2.drawLine(index, 0, index, retVal.getHeight());
    	g2.dispose();
    	
    	return retVal;
	}

}

package mivc.System;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class SagitalProcurator implements ImageProcurator {

	@Override
	public BufferedImage getImage(int index, Study study, int min, int max) {
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

}

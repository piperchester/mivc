package mivc.System;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class CoronalProcurator implements ImageProcurator {

	@Override
	public BufferedImage getImage(int index, Study study, int min, int max) {
		// Make an image that is as wide as our normal images but only as tall as
		// the number of images in our layered stack.
		BufferedImage retVal = new BufferedImage(study.getMaxZ(), 
				study.getMaxX(), BufferedImage.TYPE_INT_RGB);
		

		// For each image in the list, grab that row of x values
		for (int z = 0; z < study.getMaxZ(); z++) {
			for (int x = 0; x < retVal.getWidth(); x++) {
				retVal.setRGB(z, x, study.getPixel(x, index, z));
			}
		}
		
		
		int newHeight = retVal.getHeight()*study.getMaxX()/study.getMaxZ();
		int newWidth = retVal.getWidth();
		BufferedImage resized = new BufferedImage(newWidth, newHeight, 
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = resized.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2.drawImage(retVal, 0, 0, newWidth, newHeight, null);
		g2.dispose();
		
		// Translate the image (flip it)
		AffineTransform flip = AffineTransform.getScaleInstance(1.0, -1.0);
		flip.translate(0, -newHeight);
		AffineTransformOp op = new AffineTransformOp(flip, 
				AffineTransformOp.TYPE_BILINEAR);
		resized = op.filter(resized, null);
		
		
    	if (min > 0 || max < 255) {
    		return ImageUtil.windowImage(resized, min, max);
    	} else {
    		return resized;
    	}
	}

}

package mivc.System;

import java.awt.image.BufferedImage;

public class ImageUtil {

	/**
	 * Sets pixels outside of a provided min and max to a black value.
	 * Therefore, only those pixels within the window of min/max values
	 * provided will be shown on the resulting image.  This is a non-destructive
	 * method, that is, it does not alter the original image.
	 * @param img the image to use for calculations
	 * @param min the minimum value pixel to show
	 * @param max the maximum value pixel to show
	 * @return a new image with the windowing applied.
	 */
	public static BufferedImage windowImage(BufferedImage img, int min, int max) {
		int width = img.getWidth();
		int height = img.getHeight();
		
		// The returned image
		BufferedImage retVal = new BufferedImage(width, height, 
				BufferedImage.TYPE_INT_RGB);
		
		// Loop through and get the RGB data
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				// Only need one value since we are dealing with black and white
				int value = img.getRGB(x, y) & 0x000000ff;
				
				// Reconstruct a new RGB Hex valuue
				String newValue = String.format("%02x%02x%02x%02x", 0, value, value, value);
				if (value < min || value > max) {
					newValue = String.format("%02x%02x%02x%02x", 0, 0, 0, 0);
				}

				// Update the new RGB value
				retVal.setRGB(x, y, Integer.parseInt(newValue, 16));
			}
		}
		return retVal;
	}
	
	
}

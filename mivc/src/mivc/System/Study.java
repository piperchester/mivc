/**
 * Study.java
 */
package mivc.System;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import mivc.System.IO.ImageDAO;

/**
 * Holds images related to what is considered to be a study.
 * 
 * @author Ty
 * 
 */
public class Study {

    private String name;
    private IStudyImage[] images;
	
	public Study(String name) {
		this.name = name;
		images = ImageDAO.getInstance().listAll(name);
	}

	public BufferedImage getLineImage(int p_y, ArrayList<int[][]> p_imageDatas)
	{
		BufferedImage s_bufImage = new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB);
		for (int i = 0; i < 256; ++i)
		{
			// For every vertical line, grab/set the horizontal line
			for (int j = 0; j < 256; ++j)
			{
				int s_rgb = 0;
				// Loop through and get all of the horizontal lines add them up by color then divide by the count
				for (int k = 0; k < p_imageDatas.size(); ++k)
				{
					int[][] s_currentImage = p_imageDatas.get(k);
					s_rgb += s_currentImage[i][j];
				}
				s_rgb = s_rgb / p_imageDatas.size();
				
				s_bufImage.setRGB(j, i, s_rgb);
			}
		}
		return s_bufImage;
	}
	
    public String getName() {
        return this.name;
    }

    public IStudyImage getImage(int index) {
        return images[index];
    }
    
    public String getImagePath(int index) {
    	return images[index].getPath();
    }
    
    public int getImageCount() {
        return images.length;
    }
    
}

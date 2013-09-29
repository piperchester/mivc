/**
 * Study.java
 */
package mivc.System;

import java.awt.Image;
import mivc.System.IO.ImageDAO;

/**
 * Holds images related to what is considered to be a study.
 * 
 * @author Ty
 * 
 */
public class Study {

    private String name;
    private String[] imageNames;
	
	public Study(String name) {
		this.name = name;
		imageNames = ImageDAO.getInstance().listAll(name);
	}
	
    public Study(String name, String[] imagePaths) {
        this.name = name;
        this.imageNames = imagePaths;
    }

    public String getName() {
        return this.name;
    }

    public Image getImage(int index) {
        return ImageDAO.getInstance().read(getName() + "/" + imageNames[index]);
    }
    
    public String getImageName(int index) {
    	return imageNames[index];
    }
    
    public int getImageCount() {
        return imageNames.length;
    }
    
}

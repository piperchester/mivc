/**
 * Study.java
 */
package mivc.System;

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

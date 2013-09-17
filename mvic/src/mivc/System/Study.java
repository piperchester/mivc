/**
 * Study.java
 */
package mivc.System;

import java.io.File;

/**
 * Holds images related to what is considered to be a study.
 * @author Ty
 *
 */
public class Study {

    public Study(String name, File[] imagePaths) {
	this.name = name;
	this.imagePaths = imagePaths;
    }
    
    public String getName() { 
	return this.name; 
    }
    
    public File getImage(int index) { 
	return this.imagePaths[index]; 
    }
    
    public File[] getImagePaths() { 
	return this.imagePaths; 
    }
    
    private final String name;
    private final File[] imagePaths;
}

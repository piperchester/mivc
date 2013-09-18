/**
 * ImageManager.java
 */
package mivc.System.IO;

import java.io.File;
import java.util.List;

/**
 * ImageManager handles basic IO operations related to images.
 * @author Ty
 *
 */
public class ImageManager implements DataManager, Scannable<File> {
    
    protected ImageManager() { }
    
    public static ImageManager getInstance() { 
	return instance; 
    }

    @Override
    public List<File> scan(String path) {
	// TODO Scan path and return a List of paths to images
	return null;
    }

    @Override
    public Object read(String path) {
	// TODO Read an image from a file
	return null;
    }

    @Override
    public void write(String path, Object obj) {
	// TODO write an image to a path.
	
    }

    private static ImageManager instance = new ImageManager();
}

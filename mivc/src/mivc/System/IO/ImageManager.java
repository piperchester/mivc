/**
 * ImageManager.java
 */
package mivc.System.IO;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * ImageManager handles basic IO operations related to images.
 * @author Ty
 *
 */
public class ImageManager implements DataManager, Scannable<File> {
    
    protected ImageManager() { }
    
    public static ImageManager getInstance() { 
        return INSTANCE; 
    }

    @Override
    public List<File> scan(String path) {
        List<File> images = new LinkedList<File>();
        File p = new File(path);
        for (String s : p.list()) {
            File f = (File) read(s);
            if (f != null) {
                images.add(f);
            }
        }
        return images;
    }

    @Override
    /**
     * Read a non-hidden image file and return.
     * 
     * @param path - path to file
     * @return File object related to the image
     */
    public Object read(String path) {
        File f = new File(path);
        if (f.isDirectory() || f.isHidden()) {
            return null;
        }
        
        String extension = path.substring(path.lastIndexOf('.')).toLowerCase();
        if (!isAcceptableExtension(extension)) {
            return null;
        }
        
        return f;
    }

    @Override
    public void write(String path, Object obj) {
	// TODO write an image to a path.
	
    }
    
    
    private boolean isAcceptableExtension(String extension) {
        for (String s : EXTENSIONS) {
            if (s.equals(extension)) {
                return true;
            }
        }
        
        return false;
    }

    private static final ImageManager INSTANCE = new ImageManager();
    private static final String[] EXTENSIONS = new String[] 
        {"jpeg", "jpg"};
}

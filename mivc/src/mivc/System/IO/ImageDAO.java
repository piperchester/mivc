/**
 * ImageManager.java
 */
package mivc.System.IO;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * ImageManager handles basic IO operations related to images.
 * @author Ty
 * @author act4122
 */
public class ImageDAO implements Scannable<File> {
    
    protected ImageDAO() { }
    
    public static ImageDAO getInstance() { 
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

    /**
     * Read a non-hidden image file and return.
     * 
     * @param path - path to file
     * @return File object related to the image
     */
    public File read(String path) {
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
    
    /**
     * readImage
     * @param File p_File - File handle that was opened with ImageManager.open
     * @return null if failed, handle to image if succeeded
     */
    public BufferedImage readImage(File p_File)
    {
    	// Check to see if the file handle is valid
    	if (p_File == null)
    		return null;
    	
    	// Create a new image
    	BufferedImage s_Image = null;
    	try
    	{
    		// Attempt to load the image
    		s_Image = ImageIO.read(p_File);
    	}
    	catch (Exception ex)
    	{
    		// Failed
    		ex.printStackTrace();
    		s_Image = null;
    	}
    	
    	// Return either null, or the image handle
    	return s_Image;
    }

    public void write(String path, Image image) throws IOException {
        // Not tested
        File f = new File(path);
        ImageIO.write((RenderedImage) image, f.getName().substring(f.getName().lastIndexOf('.')), f);
    }
    
    
    private boolean isAcceptableExtension(String extension) {
        for (String s : EXTENSIONS) {
            if (s.equals(extension)) {
                return true;
            }
        }
        
        return false;
    }

    private static final ImageDAO INSTANCE = new ImageDAO();
    private static final String[] EXTENSIONS = new String[] 
        {"jpeg", "jpg"};
}

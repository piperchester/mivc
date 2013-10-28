/**
 * ImageManager.java
 */
package mivc.System.IO;

import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * ImageManager handles basic IO operations related to images.
 * @author Ty
 * @author act4122
 */
public class ImageDAO {
    
	private String rootPath = "studies";
	
    protected ImageDAO() { }
    
    public static ImageDAO getInstance() { 
        return INSTANCE; 
    }
    
    /**
     * readImage
     * @param path - the path to the image (not including the root path)
     * @return null if failed, handle to image if succeeded
     */
    public Image read(String path)
    {
    	File file = new File(rootPath + "/" + path);
    	
    	// Parse out the extension
    	String extension = path.substring(path.lastIndexOf('.'))
        		.toLowerCase();
    	// Ensure it is a file and is not hidden and it is an acceptable ext.
        if (file.isDirectory() || file.isHidden() || 
        		!isAcceptableExtension(extension)) {
            return null;
        }
    	
    	// Create a new image
    	Image retVal = null;
    	try
    	{
    		// Attempt to load the image
    		retVal = ImageIO.read(file);
    	}
    	catch (Exception ex)
    	{
    		// Failed
    		ex.printStackTrace();
    		retVal = null;
    	}
    	
    	// Return either null, or the image handle
    	return retVal;
    }

    public void write(String path, Image image) throws IOException {
        // Not tested
        File f = new File(path);
        ImageIO.write((RenderedImage) image, f.getName().substring(f.getName().lastIndexOf('.')), f);
    }
    
    public String[] listAll(String path) {
    	// Create a file for the root folder
    	File file = new File(rootPath + "/" + path);
    	// Ensure it is a folder and is not hidden
        if (!file.isDirectory() || file.isHidden()) {
            return null;
        }
        
        // Create the return object use list because we are unsure of the size
        List<String> tmp = new ArrayList<String>();
        
        // List the files in the folder
        File[] children = file.listFiles();  // Array of pathnames 
        
        
        for (File child : children) {
        	String cName = child.getName();
        	String extension = cName.substring(cName.lastIndexOf('.'))
            		.toLowerCase();
        	
            if (isAcceptableExtension(extension)) {
            	
            	// Add the file to the list of images
            	tmp.add(rootPath + "/" + path + "/" + child.getName());
            	
            }
        }

        // Now that we know the true size, convert to an array
        String[] retVal = new String[tmp.size()];

        for (int i = 0; i < tmp.size(); i++) {
        	retVal[i] = tmp.get(i);
        }
        
    	// Return the list of images
    	return retVal;
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
        {".jpeg", ".jpg"};
}

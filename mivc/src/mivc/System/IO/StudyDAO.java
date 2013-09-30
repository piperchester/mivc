/**
 * StudyManager.java
 */

package mivc.System.IO;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.ArrayList;

import mivc.System.Study;


/**
 * StudyManager handles basic IO operations related to studies.
 * @author Ty
 * @author piperchester
 * @author Colin
 */
public class StudyDAO {
    
    private String rootPath = "studies";
    
    public StudyDAO() { }
    
    public static StudyDAO getInstance() {
    	return instance;
    }
    
    
    /**
     * Adds all studies within the files ArrayList to a String list of studies.
     * @return a String list of study names.
     */
    public List<Study> listStudies()
    {   

//    	System.out.println("Scanning: " + rootPath);
    	
    	List<Study> studies = new ArrayList<Study>();
    	try {
    		File file = new File(rootPath);
            File[] children = file.listFiles();  // Array of pathnames 
            if (children != null) {
                for (File child : children) {
        	    	if (child.isDirectory()){
//        	    		System.out.println("Adding dir: " + child.getName());
        	    		studies.add(new Study(child.getName()));
        	    	}
                }
            }
    	} catch (NullPointerException e){
    		e.printStackTrace();
    		System.out.println("The path was not scanned...");
    	}
 
    	
//    	System.out.println(studies.size());
//    	System.out.println(studies);

	    
		return studies;
    }
  

    public Object read(String path) {
    	// TODO Create a study based on a directory and its contents
    	return null;
    }

    public void saveStudy(String p_srcStudyName, String p_dstStudyName)
    {
    	// Check if the directory exists, if not then create the directory
    	if (new File(p_dstStudyName).exists() == false)
    	{
    		File s_dstDir = new File(rootPath + "/" + p_dstStudyName);
    		s_dstDir.mkdir();
    	}
    	
    	File s_srcStudyDir = new File(rootPath + "/" + p_srcStudyName);
    	if (s_srcStudyDir.exists())
    	{
    		File[] s_srcImages = s_srcStudyDir.listFiles();
    		for (File l_img : s_srcImages)
    		{
    			try
    			{
	    			Path s_srcImg = Paths.get(l_img.getPath());
	    			Path s_dstImg = Paths.get(rootPath + "/" + p_dstStudyName + "/" + l_img.getName());
	    			Files.copy(s_srcImg, s_dstImg, StandardCopyOption.REPLACE_EXISTING);
    			}
    			catch (Exception ex)
    			{
    				System.err.println("Could not copy image" + l_img.getName());
    			}
    		}
    	}
    }
    
    private static StudyDAO instance = new StudyDAO();
}

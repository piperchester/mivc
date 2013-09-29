/**
 * StudyManager.java
 */

package mivc.System.IO;

import java.io.File;
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
     * Recursively searches the directory, adding a new file/directory to the ArrayList.
     * @param file - Starting point for the search
     */
    public void addTree(List<File> files, File file) {
        File[] children = file.listFiles();  // Array of pathnames 
        if (children != null) {
            for (File child : children) {
            	
                files.add(child);  // Adds new pathname to the ArrayList
                addTree(files, child);
            }
        }
    }
    
    
    /**
     * Adds all studies within the files ArrayList to a String list of studies.
     * @return a String list of study names.
     */
    public List<Study> listStudies()
    {   
    	List<File>files = new ArrayList<File>();

    	System.out.println("Scanning: " + rootPath);
    	
    	List<Study> studies = new ArrayList<Study>();
    	try {
    		File file = new File(rootPath);
            File[] children = file.listFiles();  // Array of pathnames 
            if (children != null) {
                for (File child : children) {
        	    	if (child.isDirectory()){
        	    		System.out.println("Adding dir: " + child.getName());
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

    public void write(String path, Object obj) {
    	// TODO Write a study to a specific path
    }
    
    private static StudyDAO instance = new StudyDAO();
}

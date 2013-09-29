/**
 * StudyManager.java
 */

package mivc.System.IO;

import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.Hashtable;


/**
 * StudyManager handles basic IO operations related to studies.
 * @author Ty
 * @author piperchester
 * @author Colin
 */
public class StudyManager implements Scannable<File> {
    
    public static List<File> files;  // Holds pathnames of the directory //this will be deleted
    public static Hashtable<String, List<File>> studyFiles; //new data structure to hold path names for studies
    
    public int studyCount = 0;
    
    public StudyManager() { }
    
    public static StudyManager getInstance() {
    	return instance;
    }
    
    @Override
    /**
     * Scans through a directory or nested directory and adds pathnames
     * to the ArrayList, files.
     * @param path - the location of where the scan will begin
     */
    public List<File> scan(String path) {
    	
    	files = new ArrayList<File>();
    	studyCount = 0;
    	
    	
    	
    	System.out.println("Scanning: " + path);
    	
    	System.out.println("files" + files.size());
    	
    	try {
            this.addTree(new File(path));
//            System.out.println(files);
//            listFilesAndFilesSubDirectories(path);
    	} catch (NullPointerException e){
    		System.out.println("The path was not scanned...");
    	}
 
    	
    	System.out.println(files.size());
    	System.out.println(this.listStudies());
    	
    	return files;
    }
    
    /**
     * Recursively searches the directory, adding a new file/directory to the ArrayList.
     * @param file - Starting point for the search
     */
    public void addTree(File file) {
        File[] children = file.listFiles();  // Array of pathnames 
        if (children != null) {
            for (File child : children) {
            	
            	if (child.isDirectory())
            	{
            		System.out.println("study: " + child.getName());
            		
            		//add object to hash table with name and key
            		
            	}
            	
                files.add(child);  // Adds new pathname to the ArrayList
                addTree(child);
            }
        }
    }
    
    public void studyFiles(String studyName)
    {
    	//this method will return the files for a specific study given the study name
    	
    	
    	for(File f: files)
    	{
    		System.out.println(f.getName());
    	}
    	
    	
    }
    
    
    /**
     * Adds all studies within the files ArrayList to a String list of studies.
     * @return a String list of study names.
     */
    public List<String> listStudies()
    {   
	    List<String> studies = new ArrayList<String>();
	    
	    for (File directory : files){
	    	if (directory.isDirectory()){
	    		System.out.println("Adding dir: " + directory);
	    		studyCount++;
	    		studies.add((directory.getName()));
	    	}
	    }
	    
		return studies;
    }
  

    public Object read(String path) {
    	// TODO Create a study based on a directory and its contents
    	return null;
    }

    public void write(String path, Object obj) {
    	// TODO Write a study to a specific path
    }
    
    private static StudyManager instance = new StudyManager();
}

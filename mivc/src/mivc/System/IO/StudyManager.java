/**
 * StudyManager.java
 */

package mivc.System.IO;

import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.Hashtable;

import mivc.System.Study;


/**
 * StudyManager handles basic IO operations related to studies.
 * @author Ty
 * @author piperchester
 * @author Colin
 */
public class StudyManager implements Scannable<File> {
    
    public static List<File> files;  // Holds pathnames of the directory //this will be deleted
    public static Hashtable<String, File> studyFiles; //new data structure to hold path names for studies
    
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
            		
            		
            		
            		//studyFiles.put(child.getName(), value)
            		
            	}
            	
            	if(child.isFile())
        		{
        			System.out.println(child.getAbsolutePath());
        		}
            	
                files.add(child);  // Adds new pathname to the ArrayList
                addTree(child);
            }
        }
    }
    
    /**
     * Returns the files for a specific study given the study name
     * @param studyName
     */
    public void studyFiles(String studyName)
    {
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
  

    public Study read(String path) {
        // Because we're now using a Hashtable to store usable studies (I'm 
        // presuming), the path provided will be used against its keys; if it
        // cannot find a usable path, null will be returned.
        
        File f = studyFiles.get(path);
        List<File> l = new ImageManager().scan(path);
        
    	return new Study(f.getName(), l.toArray(new File[l.size()]));
    }

    public void write(String path, Study study) {
    	File f = new File(path);
    }
    
    private static StudyManager instance = new StudyManager();
}

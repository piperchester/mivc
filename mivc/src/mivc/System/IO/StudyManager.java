/**
 * StudyManager.java
 */

package mivc.System.IO;

import java.io.File;
import java.util.List;
import java.util.ArrayList;


/**
 * StudyManager handles basic IO operations related to studies.
 * @author Ty
 * @author piperchester
 */
public class StudyManager implements Scannable<File> {
    
    public static List<File> files = new ArrayList<File>();
    
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
    	System.out.println("Scanning: " + path);
    	
    	try {
            this.addTree(new File(path));
            System.out.println(files);
//            listFilesAndFilesSubDirectories(path);
    	} catch (NullPointerException e){
    		System.out.println("The path was not scanned...");
    	}
    	
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
                files.add(child);  // Adds new pathname to the ArrayList
                addTree(child);
            }
        }
    }
    
    private static void listFilesAndFilesSubDirectories(String directoryName){
 
        File directory = new File(directoryName);
 
        //get all the files from a directory
        File[] fList = directory.listFiles();
 
        for (File file : fList){
            if (file.isFile())
            {
                files.add(file);  
            } 
            else if (file.isDirectory()){
                listFilesAndFilesSubDirectories(file.getAbsolutePath());
           }
        }
    }
    
    public List<String> listStudies()
    {
	    
	    List<String> studies = new ArrayList<String>();
	    
	    File f = new File("resources/MedImageViewerStudies");
		File [] list = f.listFiles();
		
		for (File directory: list)
		{
			if (directory.isDirectory())
			{
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

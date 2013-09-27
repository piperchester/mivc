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
 */
public class StudyManager implements Scannable<File> {
    
    static List<File> files = new ArrayList<File>();
    
    public StudyManager() { }
    
    public static StudyManager getInstance() {
    	return instance;
    }
    
    @Override
    public List<File> scan(String path) {
	// TODO Scan path and return a List of directories usable as studies
	
    	listFilesAndFilesSubDirectories(path);
	
    	return files;
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

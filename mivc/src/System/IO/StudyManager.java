/**
 * StudyManager.java
 */
package mivc.System.IO;

import java.io.File;
import java.util.List;


/**
 * StudyManager handles basic IO operations related to studies.
 * @author Ty
 */
public class StudyManager implements DataManager, Scannable<File> {
    
    protected StudyManager() { }
    
    public static StudyManager getInstance() {
	return instance;
    }

    @Override
    public List<File> scan(String path) {
	// TODO Scan path and return a List of directories useable as studies
	return null;
    }

    @Override
    public Object read(String path) {
	// TODO Create a study based on a directory and its contents
	return null;
    }

    @Override
    public void write(String path, Object obj) {
	// TODO Write a study to a specific path
    }
    
    private static StudyManager instance = new StudyManager();
}

/**
 * Main.java
 */
import mivc.System.LocalSettingsManager;
import mivc.System.IO.StudyManager;

/**
 * @author act4122
 * @author piperchester
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("---MEDICAL IMAGE VIEWING CONSOLE---");
		
		StudyManager studyManager = StudyManager.getInstance();
		
		studyManager.scan("studies");	
		
		System.out.println("Closing system...");
	}

}

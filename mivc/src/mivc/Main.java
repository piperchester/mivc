/**
 * 
 */
package mivc;

import mivc.System.LocalSettingsManager;

// Import our settings manager
/**
 * @author act4122
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		LocalSettingsManager s_settings = new LocalSettingsManager("./settings.txt");
		s_settings.set("CurrentPageNumber", 0);
		s_settings.set("CurrentStudy", "MyStudy");
	}

}

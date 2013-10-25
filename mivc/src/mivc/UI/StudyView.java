package mivc.UI;

import java.awt.event.ActionListener;
import java.util.List;

import mivc.System.IStudyImage;
import mivc.System.Study;

public interface StudyView {
	
	// The following properties are to be concatenated with the study name
	// ex. settings.set(currentStudy.getName() + IMAGE_INTERVAL_KEY, imageInterval)
	// The reasoning is that we need to be able to store view data for multiple
	// studies so the study name is the unique identifier for the key
	public static final String DEFAULT_STUDY_KEY = "default_study";
	public static final String IMAGE_INTERVAL_KEY = "_image_interval";
	public static final String SINGLE_VIEW_INDEX_KEY = "_sv_index";
	public static final String VIEW_TYPE_KEY = "_view_type";
	public static final String DISPLAY_STATE_SAVED_KEY = "_display_state";

	public enum ViewType { SINGLE_VIEW, QUAD_VIEW };
	
	/**
	 * Add a listener for the save study command 
	 * @param al the object to listen to save study commands
	 */
	public void addSaveStudyListener(ActionListener al);
	
	/**
	 * Add a listener for the save view command
	 * @param al the object to listen to save view commands
	 */
	public void addSaveViewListener(ActionListener al);
	
	/**
	 * Add a listener for the previous image command
	 * @param al the object to listen to previous image commands
	 */
	public void addPrevListener(ActionListener al);
	
	/**
	 * Add a listener for the next image command
	 * @param al the object to listen to next image commands
	 */
	public void addNextListener(ActionListener al);
	
	/**
	 * Toggles the view between single and quad view
	 */
	public void toggleView();
	
	/**
	 * Set the image(s) to display on the GUI
	 * @param images the image(s) to be displayed
	 */
	public void setImages(IStudyImage... images);
	
	/**
	 * Show a list of studies for the user to select from
	 * @param studies the list of studies to show
	 */
	public void showList(List<Study> studies);
	
	/**
	 * Get the current view type being displayed on the view
	 * @return the current view type
	 */
	public ViewType getCurrentView();
	
	/**
	 * Get an input from the user of the application
	 * @param prompt the question to prompt to the user
	 * @return the value entered by the user
	 */
	public String getInput(String prompt);
	
	/**
	 * Get a confirmation for a warning from the user of the application
	 * @param prompt the warning to prompt the user with
	 * @return whether or not the user agrees with the prompt
	 */
	public boolean getWarningConfirmation(String prompt);
	
	public void setCurrentStudy(String studyName);
	
	
	// TODO simplify this method
	public void setImageIndexing(int interval, int index);
	
	public int getImageInterval();
	
	public int getSingleViewIndex();
	
	public Study getCurrentStudy();
	
}

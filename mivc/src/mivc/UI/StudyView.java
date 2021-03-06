package mivc.UI;

import java.awt.image.BufferedImage;
import java.util.List;

import mivc.System.ImageGetter;
import mivc.System.Study;

/**
 * Serves as an interface of the view of the Study. From this interface 
 * the user will be able to implement methods on study viewing such as
 * view toggling, image setting, etc
 * 
 * @author geofberl
 * 
 */
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
	public enum ReconstructionType {SAGITAL, CORONAL, AXIAL};
	
	/**
	 * Toggles the view between single and quad view
	 */
	public void toggleView();
	
	/**
	 * Set the image(s) to display on the GUI
	 * @param images the image(s) to be displayed
	 */
	public void setImages(BufferedImage... images);
	
	/**
	 * Show a list of studies for the user to select from
	 */
	public void showList();
	
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
	
	public void updateStudies(List<Study> studies);
	
	public void updateImageType(ReconstructionType type);
	
	public ReconstructionType getCurrentImageType();
	
	public void updateImageProcurator(ImageGetter procurator);
}

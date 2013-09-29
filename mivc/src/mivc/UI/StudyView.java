package mivc.UI;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public interface StudyView {

	/**
	 * Add a listener for the open command
	 * @param al the object to listen to open commands
	 */
	public void addOpenListener(ActionListener al);
	
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
	 * Add a listener for the view command
	 * @param al the object to listen to view commands
	 */
	public void addViewListener(ActionListener al);
	
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
	 * Add a listener for the selectin of a study
	 * @param al the object to listen to study selection
	 */
	public void addStudySelectionListener(ActionListener al);
	
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
	 * @param studies the list of studies to show
	 */
	public void showList(String[] studies);
	
	public String getSelectedStudy();
	
	public boolean isDefaultSelected();
	
}

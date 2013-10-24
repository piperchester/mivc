package mivc.UI;

import java.awt.event.ActionListener;

import mivc.System.IStudyImage;

public interface StudyView {

	public enum ViewType { SINGLE_VIEW, QUAD_VIEW };
	
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
	public void setImages(IStudyImage... images);
	
	/**
	 * Show a list of studies for the user to select from
	 * @param studies the list of studies to show
	 */
	public void showList(String[] studies);
	
	/**
	 * Get the selected study from the study selection window
	 * @return the selected study name
	 */
	public String getSelectedStudy();
	
	/**
	 * Find out if the default option was selected
	 * @return whether or not the default option was selected
	 */
	public boolean isDefaultSelected();
	
	/**
	 * Get the current view type being displayed on the view
	 * @return the current view type
	 */
	public ViewType getCurrentView();
	
	/**
	 * Update the status bar with a message
	 * @param value the message to show on the status bar
	 */
	public void updateStatusBar(String value);
	
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
	
}

package mvic.UI;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public interface StudyView {

	public void addOpenListener(ActionListener al);
	public void addSaveStudyListener(ActionListener al);
	public void addSaveViewListener(ActionListener al);
	public void addViewListener(ActionListener al);
	public void addPrevListener(ActionListener al);
	public void addNextListener(ActionListener al);
	public void toggleView();
	public void setImages(BufferedImage... images);
	public void showList(String[] studies);
	
}

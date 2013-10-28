package mivc.System;

import java.awt.image.BufferedImage;

public interface ImageProcurator {

	public BufferedImage getImage(int index, Study study, int min, int max);
	
}

/**
 * Study.java
 */
package mivc.System;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Holds images related to what is considered to be a study.
 * 
 * @author Ty
 * 
 */
public class Study {

    public Study(String name, File[] imagePaths) {
        this.name = name;
        this.images = new StudyImage[imagePaths.length];
        
        int index = 0;
        for (File f : imagePaths) {
            images[index++] = new StudyImage(f);
        }
    }

    public String getName() {
        return this.name;
    }

    public BufferedImage getImage(int index) throws IOException {
        return this.images[index].getImage();
    }
    
    public String getImageName(int index) {
	return this.images[index].getName();
    }

    private final String name;
    private final StudyImage[] images;
    
    /**
     * Handles images held by the study.
     * @author Ty
     *
     */
    private class StudyImage {
        private StudyImage(File path) {
            this.path = path;
        }
        
        private BufferedImage getImage() throws IOException {
            if (this.image == null) {
                loadImage();
            }
            
            return this.image;
        }
        
        private String getName() {
            return this.path.getName();
        }
        
        private void loadImage() throws IOException {
            this.image = ImageIO.read(this.path);
        }
        
        private final File path;
        private BufferedImage image = null;
    }
}

/**
 * Study.java
 */
package mivc.System;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;

import mivc.System.IO.ImageDAO;

/**
 * Model Object for Study. Holds images related to the study by 
 * encapsulating the study into its own class, we can isolate its data to 
 * one place in the system. The Study Class handles reading image data 
 * from the file system and storing them as a data structure.
 * 
 * @author Ty
 * 
 */
public class Study {

    private String name;
    private String[] imagePaths;
    private List<Integer[][]> boxData;
    private List<Study> subStudies;
	
    /**
     * Single arg constructor for naming this study
     * @param name the name of the study.
     */
	public Study(String name) {
		this.name = name;
		imagePaths = ImageDAO.getInstance().listAll(name);
	}
	
	/**
	 * Get a list of sub studies
	 * @return a list of sub studies
	 */
	public List<Study> getSubStudies()
	{
		return subStudies;
	}
	
	/**
	 * Set the sub studies
	 * @param p_studies the list of sub studies to set
	 */
	public void setSubStudies(List<Study> p_studies)
	{
		subStudies = p_studies;
	}

	/**
	 * Loads a three dimensional array of pixel information for gathering
	 * reconstructed views.  Should only be called when images are actually
	 * needed.  Doing so will save on resources
	 */
	public void loadImageData() {
		loadBoxDataMT();
	}
	
	/**
	 * Clear the pixel data for garbage collection
	 */
	public void purgeImageData() {
		boxData = new ArrayList<Integer[][]>();
	}
	
	/* Private method */
	private void loadBoxDataMT() {
		if (boxData == null) {
			boxData = new ArrayList<Integer[][]>();
		}
		
        // CREATE executor service
        ExecutorService exec = Executors.newFixedThreadPool(imagePaths.length);
        
        long startTime = System.currentTimeMillis();
        List<Callable<Integer[][]>> callables = new ArrayList<Callable<Integer[][]>>();
        
        int i = 0;
        for (String child : imagePaths) {
        	
        	callables.add(new MyCallable(i++, new File(child)));

        }
        
        // Now run the callables
        List<Future<Integer[][]>> results;
        try {
        	results = exec.invokeAll(callables);
        	for (Future<Integer[][]> result : results) {
        		boxData.add(result.get());
        	}
        } catch (InterruptedException e) {
        	e.printStackTrace();
        } catch (ExecutionException e) {
        	e.printStackTrace();
        } finally {
        	exec.shutdown();
        }

        
        System.out.println("It took " + (System.currentTimeMillis() - startTime) + " milliseconds to read those images");
		
	}
	
	/**
	 * Get the name of this study
	 * @return the name of this study
	 */
    public String getName() {
        return this.name;
    }
    
    /**
     * Get a count of images contained in this study
     * @return the number of images in this study
     */
    public int getImageCount() {
        return imagePaths.length;
    }
    
    /**
     * A special inner class for reading ACR images
     */
    class MyImageIO {

    	public static final int HEADER_OFFSET = 0x2000;
    	
    	public BufferedImage read(File f) {
    		FileImageInputStream imageFile = null;
    		
    		try {
    			imageFile = new FileImageInputStream(f);
    			imageFile.seek(HEADER_OFFSET);
    		}
    		catch (FileNotFoundException e) {
    		    System.err.print("Error opening file: ");
    		    System.err.println(e.getMessage());
    		    System.exit(2);
    		}
    		catch (IOException e) {
    		    System.err.print("IO error on file: ");
    		    System.err.println(e.getMessage());
    		    System.exit(2);
    		}
    		
    		int sliceWidth = 256;
    		int sliceHeight = 256;
    		    
    		BufferedImage sliceBuffer = 
    		    new BufferedImage( sliceWidth,sliceHeight,
    				       BufferedImage.TYPE_USHORT_GRAY );

    		for ( int i = 0; i < sliceBuffer.getHeight(); i++ ) {
    		    for ( int j = 0; j < sliceBuffer.getWidth(); j++ ) {

    			int pixelHigh = 0;
    			int pixelLow = 0;
    			int pixel;
    			
    			try {
    			    pixelHigh = imageFile.read();
    			    pixelLow = imageFile.read();
    			    pixel = pixelHigh << 4 | pixelLow >> 4;
    			    
    			    sliceBuffer.setRGB( j, i,
    					      pixel << 16 | pixel << 8 | pixel);

    			}
    			catch (IOException e) {
    			    System.err.print("IO error readin byte: ");
    			    System.err.println(e.getMessage());
    			    System.exit(2);
    			}
    			
    			
    		    }
    		}
    		try {
				imageFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
    		return sliceBuffer;
    	}
    }
    
    /**
     * A special inner class to allow image reading to be multi-threaded.
     * 
     * @author berlgeof
     */
	class MyCallable implements Callable<Integer[][]> {
		private final File child;
		
		MyCallable(int threadnumber, File child) {
			this.child = child;
		}
		
		public Integer[][] call() {
			BufferedImage tmpImg = null;
        	// Load the image
			try {
				//System.out.println("Trying to read " + child.getPath());
				if (child.getPath().substring(child.getPath().length()-3).equals("acr")) {
					tmpImg = new MyImageIO().read(child);
				} else {
					tmpImg = ImageIO.read(child);
				}

				// NOTE: Doesn't check extensions

				// Add the file to the list of images
				//System.out.println("Reading image " + child.getName());
				int width = tmpImg.getWidth();
				int height = tmpImg.getHeight();
				Integer[][] tmpData = new Integer[width][height];
				for (int x = 0; x < width; x++) {
					for (int y = 0; y < height; y++) {
						// Store the current x, y data into the array
						tmpData[x][y] = tmpImg.getRGB(x, y);
					}
				}
				return tmpData;
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
    
	/**
	 * Get the pixel for a specific coordinate
	 * @param x the x position of the pixel
	 * @param y the y position of the pixel
	 * @param z the z position of the pixel
	 * @return the pixel at the specified coordinate
	 */
	public int getPixel(int x, int y, int z) {
		// If x, y or z exceeds the size, return 0
		if (boxData.size() == 0 || z > boxData.size()) {
			return 0;
		}
		if (x > boxData.get(0).length || y > boxData.get(0)[0].length) {
			return 0;
		}

		return boxData.get(z)[x][y];
		
	}
	
	/**
	 * Get the max X value of the 3D pixel array
	 * @return the max X value of the 3D pixel array
	 */
	public int getMaxX() {
		if (boxData.size() == 0) {
			return 0;
		}
		return boxData.get(0).length;
	}

	/**
	 * Get the max Y value of the 3D pixel array
	 * @return the max Y value of the 3D pixel array
	 */
	public int getMaxY() {
		if (boxData.size() == 0) {
			return 0;
		}
		return boxData.get(0)[0].length;
	}
	
	/**
	 * Get the max Z value of the 3D pixel array
	 * @return the max Z value of the 3D pixel array
	 */
	public int getMaxZ() {
		return boxData.size();
	}
}

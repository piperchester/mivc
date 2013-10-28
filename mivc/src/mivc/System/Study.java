/**
 * Study.java
 */
package mivc.System;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.imageio.ImageIO;

import mivc.System.IO.ImageDAO;

/**
 * Holds images related to what is considered to be a study.
 * 
 * @author Ty
 * 
 */
public class Study {

    private String name;
    private String[] imagePaths;
    private List<Integer[][]> boxData;
	
	public Study(String name) {
		this.name = name;
		imagePaths = ImageDAO.getInstance().listAll(name);
	}

	public void loadImageData() {
		loadBoxDataMT();
	}
	
	public void purgeImageData() {
		boxData = new ArrayList<Integer[][]>();
	}
	
	/**
	 * @param path the path to the images
	 */
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
	
    public String getName() {
        return this.name;
    }
    
    public String getImagePath(int index) {
    	return imagePaths[index];
    }
    
    public int getImageCount() {
        return imagePaths.length;
    }
    
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
				tmpImg = ImageIO.read(child);

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
	
	public int getMaxX() {
		if (boxData.size() == 0) {
			return 0;
		}
		return boxData.get(0).length;
	}

	public int getMaxY() {
		if (boxData.size() == 0) {
			return 0;
		}
		return boxData.get(0)[0].length;
	}
	
	public int getMaxZ() {
		return boxData.size();
	}
}

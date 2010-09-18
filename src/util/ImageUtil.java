package util;

import java.awt.image.BufferedImage;

public class ImageUtil 
{
	/**
	 * Returns an array of BufferedImages that is the spliting to the given Bufferedimage.
	 * 
	 * @param ori - The BufferedImage to be Split.
	 * @param cols - How many columns the BufferedImage is to be split into.
	 * @param rows - How many rows the BufferedImage is to be split into.
	 * @return An array of BufferedImages that is of size (rows * cols).
	 */
	public static BufferedImage[] splitImage(BufferedImage ori, int cols, int rows)
	{
		int width = ori.getWidth();  //Get the width and the height of the Image
		int height = ori.getHeight();
		
		int[] xPoints = new int[cols];  //Reserve space for the x and y points of each new sub image.
		int[] yPoints = new int[rows];
		
		int newWidth = width/cols;  //Get the width and height of each new subimage.
		int newHeight = height/rows;
		
		for(int i = 0; i < xPoints.length; i++)  //Actually calculate the X and Y points.
		{
			xPoints[i] = i * newWidth;
		}
		
		for(int i = 0; i < yPoints.length; i++)
		{
			yPoints[i] = i * newHeight;
		}
		
		BufferedImage[] rv = new BufferedImage[rows * cols];  //This will be our return value.
		int pos = 0;
		
		for(int i = 0; i < xPoints.length; i++)
		{
			for(int j = 0; j < yPoints.length; j++)
			{
				rv[pos++] = ori.getSubimage(xPoints[j], yPoints[i], newWidth, newHeight);  //Get the subimage of each one.
			}
		}
		
		return rv;
	}
}

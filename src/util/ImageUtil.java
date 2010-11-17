package util;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

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

	/**
	 * Returns an array of the pixels from a BufferedImage
	 * @param img - The buffered 
	 * @return An array of pixel values from the BufferedImage img
	 */
	public static int[] getPixels(BufferedImage img)
	{
		return img.getRGB(0, 0, img.getWidth(), img.getHeight(), null, 0, 0);
	}
	
	public static BufferedImage getBufferedImage(String path)
	{
		try {
			return ImageIO.read(ImageUtil.class.getResource(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static int[] getBitMask(int[] bitmask)
	{
		for(int i = 0; i < bitmask.length; i++)
		{
			if((bitmask[i] >>> 24) != 0x0)
			{
				bitmask[i] = 0x1;
			}
			else
			{
				bitmask[i] = 0x0;
			}
		}
		
		return bitmask;
	}
	
	public static int[] getCombinedBitMask(int[] pixels_1, int[] pixels_2)
	{
	    int[] bitmask = new int[Math.min(pixels_1.length, pixels_2.length)];

	    for(int i = 0; i < bitmask.length;  i++)
	    {
	           bitmask[i] = pixels_1[i] & pixels_2[i];
	    }
	    return bitmask;
	}

}

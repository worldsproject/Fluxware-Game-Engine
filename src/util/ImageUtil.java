package util;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class ImageUtil 
{
	public static int LOW_QUALITY = 0;
	public static int MEDIUM_QUALITY = 1;
	public static int HIGH_QUALITY = 2;

	private static HashMap<String, BufferedImage> cache = new HashMap<String, BufferedImage>();

	public static BufferedImage scaleImage(BufferedImage original, int width, int height, int quality)
	{
		if(width < 0 || height < 0)
			return original;

		Object qual = null;

		if(quality == LOW_QUALITY)
		{
			qual = RenderingHints.VALUE_RENDER_SPEED;
		}
		else if(quality == MEDIUM_QUALITY)
		{
			qual = RenderingHints.VALUE_RENDER_DEFAULT;
		}
		else
		{
			qual = RenderingHints.VALUE_RENDER_QUALITY;
		}

		int type = original.getType() == 0? BufferedImage.TYPE_INT_ARGB : original.getType();
		BufferedImage resizedImage = new BufferedImage(width, height, type);
		Graphics2D g = resizedImage.createGraphics();
		g.setComposite(AlphaComposite.Src);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, qual);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawImage(original, 0, 0, width, height, null);

		return resizedImage;
	}

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
				rv[pos++] = ori.getSubimage(xPoints[i], yPoints[j], newWidth, newHeight);  //Get the subimage of each one.
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
		if(cache.containsKey(path))
		{
			return cache.get(path);
		}
		else
		{
			try 
			{
				BufferedImage temp = ImageIO.read(ImageUtil.class.getResource(path));
				cache.put(path, temp);
				return temp;
			}
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
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

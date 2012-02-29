package util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import de.matthiasmann.textureloader.Texture;

public class ImageUtil 
{
	public static ImageData loadTexture(String location)
	{
		Texture t = null;
		boolean[][] mask = null;
		try 
		{
			t = Texture.loadTexture(ImageData.class.getResource(location));
			t.getWidth();
			mask = generateMask(location);
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new ImageData(t, mask);
	}
	
	private static boolean[][] generateMask(String location) throws IOException
	{
		URL u = ImageUtil.class.getResource(location);
		BufferedImage buf = ImageIO.read(u);

		boolean[][] rv = new boolean[buf.getWidth()][buf.getHeight()];
		
		for(int i = 0; i < buf.getWidth(); i++)
		{
			for(int j = 0; j < buf.getWidth(); j++)
			{
				if(new Color(buf.getRGB(i, j)).getAlpha() != 0)
				{
					rv[i][j] = true;
				}
			}
		}
		
		return rv;
	}
}

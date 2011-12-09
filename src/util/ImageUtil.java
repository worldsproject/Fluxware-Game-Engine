package util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class ImageUtil 
{
	public static ImageData loadTexture(String format, String location)
	{
		Texture t = null;
		boolean[][] mask = null;
		try 
		{
			t = TextureLoader.getTexture(format, ResourceLoader.getResourceAsStream(location));
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
		BufferedImage buf = ImageIO.read(new File(location));

		
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

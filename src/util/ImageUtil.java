package util;

import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class ImageUtil 
{
	public static Texture loadTexture(String format, String location)
	{
		Texture t = null;
		try 
		{
			t = TextureLoader.getTexture(format, ResourceLoader.getResourceAsStream(location));
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return t;
	}
}

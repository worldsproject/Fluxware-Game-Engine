package util;

import de.matthiasmann.textureloader.Texture;

public class BreakTexture
{
	public static void main(String args[])
	{
		try
		{
			Texture t = Texture.loadTexture(BreakTexture.class.getResource("/tests/resources/fox.gif"));
			t.getWidth();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}

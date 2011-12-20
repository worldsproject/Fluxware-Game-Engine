package util;

import org.newdawn.slick.opengl.Texture;

public class ImageData 
{
	private Texture texture = null;
	private boolean[][] mask = null;
	
	public ImageData(Texture texture, boolean[][] mask) 
	{
		super();
		this.texture = texture;
		this.mask = mask;
	}
	
	public Texture getTexture() 
	{
		return texture;
	}
	
	public void setTexture(Texture texture) 
	{
		this.texture = texture;
	}
	
	public boolean[][] getMask() 
	{
		return mask;
	}
	
	public void setMask(boolean[][] mask) 
	{
		this.mask = mask;
	}
	
}

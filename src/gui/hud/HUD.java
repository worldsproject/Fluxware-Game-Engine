package gui.hud;

import java.util.HashMap;
import java.util.LinkedList;

import sprites.Sprite;

public class HUD
{
	HashMap<Integer, Sprite> all = new HashMap<Integer, Sprite>();
	
	private int width = 200;
	private int height = 200;
	
	private boolean show = true;
	private int transparency = 100;
	
	public HUD()
	{
		
	}
	
	public void setWidth(int width)
	{
		this.width = width;
	}
	
	public void setHeight(int height)
	{
		this.height = height;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public void setVisible(boolean b)
	{
		show = b;
	}
	
	public boolean isVisible()
	{
		return show;
	}
	
	public HashMap<Integer, Sprite> getSprites()
	{
		return all;
	}
	
	public void addSprite(Sprite s, int key)
	{
		all.put(key, s);
	}
	
	public boolean removeSprite(int key)
	{
		return (all.remove(key) == null);
	}
}

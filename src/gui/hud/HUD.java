package gui.hud;

import java.util.HashMap;
import java.util.LinkedList;

import sprites.Sprite;

/**
 * The HUD is essentially a collection of Sprites that is displayed on top of a Room.
 * @author Tim Butram
 *
 */
public class HUD
{
	private HashMap<Integer, Sprite> all = new HashMap<Integer, Sprite>();
	
	private int width = 200;
	private int height = 200;
	
	private boolean show = true;
	private int transparency = 100;
	
	/**
	 * Creates a default, empty HUD.
	 */
	public HUD()
	{
		
	}
	
	/**
	 * Sets the width of the HUD
	 * @param width - How wide the HUD is.
	 */
	public void setWidth(int width)
	{
		this.width = width;
	}
	
	/**
	 * Sets the height of the HUD
	 * @param height - How high the HUD reaches.
	 */
	public void setHeight(int height)
	{
		this.height = height;
	}
	
	/**
	 * Returns the width of the HUD
	 * @return int of how wide the HUD is.
	 */
	public int getWidth()
	{
		return width;
	}
	
	/**
	 * Returns the width of the HUD
	 * @return int of how height the HUD is.
	 */
	public int getHeight()
	{
		return height;
	}
	
	/**
	 * Sets the visibility of the HUD.
	 * @param b true to allow the HUD to be shown, false otherwise.
	 */
	public void setVisible(boolean b)
	{
		show = b;
	}
	
	/**
	 * 
	 * @return true if the HUD is visible, false otherwise.
	 */
	public boolean isVisible()
	{
		return show;
	}
	
	/**
	 * This returns the list of Sprites that is in the HUD.
	 * @return Hashmap<Integer, Sprite> of the Sprites in the HUD.
	 */
	public HashMap<Integer, Sprite> getSprites()
	{
		return all;
	}
	
	/**
	 * Add a Sprite to the HUD
	 * @param s - The Sprite to be added.
	 * @param key - The key to the Sprite.
	 */
	public void addSprite(Sprite s, int key)
	{
		all.put(key, s);
	}
	
	/**
	 * Removes the Sprite by the key
	 * @param key - The key to the Sprite.
	 * @return true if the Sprite was removed, false otherwise.
	 */
	public boolean removeSprite(int key)
	{
		return (all.remove(key) != null);
	}
}

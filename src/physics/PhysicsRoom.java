package physics;

import java.util.LinkedList;

import level.Room;
import sprites.Sprite;

public class PhysicsRoom extends Room 
{
	private int pixelsToMeters = 10;
	private Vector2D gravity = new Vector2D((1.5 * Math.PI), 2.0);
	
	private LinkedList<PhysicsSprite> pSprites = new LinkedList<PhysicsSprite>();
	
	public PhysicsRoom(int width, int height, int layers)
	{
		super(width, height, layers);
	}
	
	/**
	 * Defines how many pixels equal a meter.
	 * @param amount of pixels are in a meter.
	 */
	public void setPixelsToMeters(int amount)
	{
		pixelsToMeters = amount;
	}
	
	/**
	 * 
	 * @return The amount of pixels that equal a meter.
	 */
	public int getPixelsToMeters()
	{
		return pixelsToMeters;
	}
	
	/**
	 * Set gravity as a Vector.
	 * @param grav
	 */
	public void setGravity(Vector2D grav)
	{
		gravity = grav;
	}
	
	/**
	 * Returns the Vector representation of Gravity.
	 * @return
	 */
	public Vector2D getGravity()
	{
		return gravity;
	}
	
	public void addSprite(Sprite sprite)
	{
		super.addSprite(sprite);
		
		if(sprite instanceof PhysicsSprite)
		{
			pSprites.add((PhysicsSprite)sprite);
		}
	}
	
	public LinkedList<PhysicsSprite> getPhysicsSprites()
	{
		return pSprites;
	}
}

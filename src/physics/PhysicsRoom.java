package physics;

import level.Room;

public class PhysicsRoom extends Room 
{
	private int pixelsToMeters = 10;
	private Vector2D gravity = new Vector2D((1.5 * Math.PI), 9.8);
	
	public void setPixelsToMeters(int amount)
	{
		pixelsToMeters = amount;
	}
	
	public int getPixelsToMeters()
	{
		return pixelsToMeters;
	}
	
	public void setGravity(Vector2D grav)
	{
		gravity = grav;
	}
	
	public Vector2D getGravity()
	{
		return gravity;
	}
}

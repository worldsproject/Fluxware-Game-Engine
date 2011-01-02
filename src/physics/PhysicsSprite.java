package physics;

import java.awt.image.BufferedImage;

import sprites.Sprite;

public class PhysicsSprite extends Sprite 
{
	private Vector2D vector = new Vector2D(0, 0);
	private PhysicsRoom room = null;

	private double inertia = 1.0;

	public PhysicsSprite(PhysicsRoom room)
	{
		this.room = room;
	}

	public PhysicsSprite(BufferedImage img, PhysicsRoom room, int x, int y, int layer)
	{
		super(img, x, y, layer);

		this.room = room;
	}
	
	public void setInertia(double inertia)
	{
		this.inertia = inertia;
	}
	
	public double getInertia()
	{
		return inertia;
	}

	public void update(long elapsed, long total)
	{
		applyVector(room.getGravity()); //This will continiously apply Gravity to the Sprite.

		int xMoved = 0;
		int yMoved = 0;
		
		if(inertia != Double.POSITIVE_INFINITY || inertia != Double.NEGATIVE_INFINITY || inertia != Double.NaN)
		{
			xMoved = (int) ((((double)elapsed / 1000) * vector.getXComponent()) / inertia);
			yMoved = (int) ((((double)elapsed / 1000) * vector.getYComponent()) / inertia);
		}
		

		this.setX( this.getX() + xMoved);
		this.setY( this.getY() + yMoved);
		
		
	}

	/**
	 * Adds the given vector to the Sprites current Vector state.
	 * @param vector - The Vector to be added.
	 */
	public void applyVector(Vector2D vector)
	{
		this.vector = Vector2D.add(this.vector, vector);
	}
}

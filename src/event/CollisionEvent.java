package event;

import java.util.LinkedList;

import sprites.Sprite;

/**
 * This class defines a collision between two different Sprites.
 * @version February 24, 2010
 */
public class CollisionEvent extends Event
{

	private LinkedList<Sprite> collisions;  //Defines all of the Sprites involed in the collision.
	
	/**
	 * Default constructor for the CollisionEvent.
	 * This essentially defines a "non-collision" as no Sprites are added to the list.
	 */
	public CollisionEvent()
	{
		super();
		collisions = null;
	}
	
	/**
	 * Defines a CollisionEvent with a host Sprite, and a list of Sprites it has collided with.
	 * @param s - The Sprite of origin.
	 * @param l - A LinkedList of Sprites that are colliding with the origin Sprite.
	 */
	public CollisionEvent(Sprite s, LinkedList<Sprite> l)
	{
		super(s);
		collisions = l;
	}
	
	@Override
	public Sprite getSource()
	{
		return (Sprite)source;
	}
	
	/**
	 * Returns a LinkedList of Sprites that are colliding with the source Sprite.
	 * @return LinkedList of Sprites.
	 */
	public LinkedList<Sprite> getCollisions()
	{
		return collisions;
	}

}

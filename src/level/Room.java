package level;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

import listener.CollisionListener;
import listener.bounding.Bounding;
import sprites.Sprite;
import util.Point2D;
import event.CollisionEvent;

/**
 * Default Room class. This is the general, broad overview of a room.
 * This particular type of room is used for full 2D freedom viewed in an orthagonal plane.
 * 
 * Generally to use, extend a room, define your Key Board based actions, and you're all set.
 * 
 * @author Tim Butram, CJ Robinson
 *
 */
public class Room implements KeyListener
{
	//Basic dimensions of the room.
	protected int height;
	protected int width;
	protected int layers;
	protected LinkedList<KeyListener> keylisteners = new LinkedList<KeyListener>();
	protected LinkedList<CollisionListener> collisionListeners = new LinkedList<CollisionListener>();

	//Holds all of the sprites in the room.
	protected HashMap<Integer, LinkedList<Sprite>> allSprites = new HashMap<Integer, LinkedList<Sprite>>();

	public Room(){}

	public Room(int width, int height, int layers)
	{
		this.height = height;
		this.width = width;
		this.layers = layers;
	}

	/**
	 * @return - Width of the room.
	 */
	public int getHeight()
	{
		return height;
	}

	/**
	 * @return - Height of the room.
	 */
	public int getWidth()
	{
		return width;
	}

	/**
	 * @return - The number of Layers in the room.
	 */
	public int getLayers()
	{
		return layers;
	}

	/**
	 * Returns the Sprite, if any, at the location (x,y, layer)
	 * @param x - The X coordinate of the search.
	 * @param y - The Y coordinate of the search.
	 * @param layer - The layer you are looking at.
	 * @return - The sprite at (x,y,layer) if any.  If there is no sprite at (x,y,layer) then null is returned.
	 */
	@Deprecated
	public Sprite getSprite(int x, int y, int layer)
	{	
		LinkedList<Sprite> as = allSprites.get(new Integer(layer));

		for(Sprite s: as)
		{
			if(s.getX() == x && s.getY() == y)
			{
				return s;
			}
		}

		return null;
	}

	/**
	 * Returns a Sprite based on the location represented by <i>p</i>.
	 * @param p - Location to be checked.
	 * @return - A Sprite if the location at <i>p</i> contains a Sprite, null otherwise.
	 */
	@Deprecated
	public Sprite getSprite(Point2D p)
	{
		LinkedList<Sprite> as = allSprites.get(new Integer(p.getLayer()));

		for(Sprite s: as)
		{
			if(s.getX() == p.getX() && s.getY() == p.getY())
			{
				return s;
			}
		}

		return null;
	}

	/**
	 * Returns a Sprite based on the optional unique ID.
	 * @param id - The ID being searched for.
	 * @return - The Sprite with the matching ID, null if no Sprite exists with given ID.
	 */
	public Sprite getSprite(int id)
	{	
		for(Sprite s: this.getSprites())
		{
			if(id == s.id)
			{
				return s;
			}
		}

		return null;
	}

	/**
	 * Moves the Sprite by the given <i>xAmount</i> and given <i>yAmount</i>
	 * <br /><b><font color=red>DOES NOT DO COLLISION DETECTION AS OF NOW.</font></b>
	 * 
	 * @param sprite - The Sprite to be moved.
	 * @param xAmount - Amount in the X direction the Sprite is to move, may be negative.
	 * @param yAmount - Amount in the Y direction the Sprite is to move, may be negative.
	 * @return true is the move was successful, false if otherwise.
	 */
	@Deprecated
	public boolean move(Sprite sprite, int xAmount, int yAmount)
	{
		sprite.setX(sprite.getX() + xAmount);
		sprite.setY(sprite.getY() + yAmount);
		return true;
	}

	/**
	 * Returns all Sprites in the room.
	 * @return - All Sprites.
	 */
	public LinkedList<Sprite> getSprites()
	{
		LinkedList<Sprite> as = new LinkedList<Sprite>();

		Set<Integer> s = allSprites.keySet();
		
		for(Integer i : s)
		{
			try
			{
				as.addAll(allSprites.get(i));
			}
			catch(ArrayIndexOutOfBoundsException e)
			{
				//Ignore it, the sprite is mostly likely gone already/
			}
		}
		
		return as;
	}

	public LinkedList<Sprite> getSprites(Point2D p)
	{
		LinkedList<Sprite> rv = new LinkedList<Sprite>();

		for(Sprite s : this.getSprites())
		{
			Bounding b = s.getBounding();

			if(b != null)
			{
				if(b.withinBounds(p))
				{
					rv.add(s);
				}
			}
		}

		return rv;
	}

	/**
	 * Returns all Sprites of a specific type, based on serial number.
	 * 
	 * @param serial - The Serial number of the type of Sprite to be returned.
	 * @return - A linked list of all matching sprites.
	 */
	public LinkedList<Sprite> getSprites(int serial)
	{
		LinkedList<Sprite> sprites = new LinkedList<Sprite>(); 

		for(Sprite s: this.getSprites())
		{
			if(serial == s.serial)
			{
				sprites.add(s);
			}
		}

		return sprites;
	}

	/**
	 * Adds a Sprite.
	 * @param sprite - The sprite to be added.
	 */
	public void addSprite(Sprite sprite)
	{
		LinkedList<Sprite> temp = allSprites.get(sprite.getLayer());

		if(temp != null)
		{
			temp.add(sprite);
		}
		else
		{
			temp = new LinkedList<Sprite>();
			temp.add(sprite);
			allSprites.put(new Integer(sprite.getLayer()), temp);
		}
	}

	/**
	 * Removes a sprite located at (x, y, layer).
	 * If no Sprite is at that location, no action is performed, no error is returned.
	 * 
	 * @param x - The X coordinate of the Sprite to be removed.
	 * @param y - The Y coordinate of the Sprite to be removed.
	 * @param layer - The Layer of the Sprite to be removed.
	 */
	public void removeSprite(int x, int y, int layer)
	{
		LinkedList<Sprite> as = allSprites.get(layer);

		for(Sprite s: as)
		{
			if(s.getX() == x && s.getY() == y)
			{
				as.remove(s);
			}
		}
	}

	/**
	 * Removes the Sprite passed into the method.
	 * @param sprite - The sprite to be removed.
	 */
	public void removeSprite(Sprite sprite) 
	{	
		LinkedList<Sprite> as = allSprites.get(sprite.getLayer());

		as.remove(sprite);
	}

	public void addKeyListener(KeyListener k)
	{
		keylisteners.add(k);
	}

	public LinkedList<KeyListener> getKeyListeners()
	{
		return keylisteners;
	}

	public LinkedList<CollisionListener> getCollisionListeners()
	{
		return collisionListeners;
	}

	public void throwCollision(Sprite s, LinkedList<Sprite> list)
	{
		for(CollisionListener cl : collisionListeners)
		{
			cl.collided(new CollisionEvent(s, list));
		}
	}

	public void throwCollision(Sprite s, Point2D p)
	{
		for(CollisionListener cl : collisionListeners)
		{
			cl.collided(new CollisionEvent(s, this.getSprites(p)));
		}
	}

	public void throwCollision(Sprite s, int serial)
	{
		for(CollisionListener cl : collisionListeners)
		{
			cl.collided(new CollisionEvent(s, this.getSprites(serial)));
		}
	}

	public boolean inBound(int x, int y)
	{
		if(((x >= 0) && (x < width)) && ((y >= 0) && (y < height)))
		{
			return true;
		}

		return false;
	}
	
	public String toString()
	{
		return "Width: " + this.getWidth() + " Height: " + this.getHeight() + " Layers: " + this.getLayers();
	}

	public void keyPressed(KeyEvent arg0){}
	public void keyReleased(KeyEvent arg0){}
	public void keyTyped(KeyEvent arg0){}
}

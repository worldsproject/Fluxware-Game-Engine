package level;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

import listener.bounding.Bounding;
import listener.bounding.BoundingBox;
import listener.bounding.BoundingCircle;
import sprites.Sprite;
import util.ImageUtil;
import util.Point2D;

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

	/**
	 * 
	 * @param p
	 * @param s
	 * @return
	 */
	public LinkedList<Sprite> getOverlaps(Point2D p, Sprite s)
	{
		LinkedList<Sprite> rv = new LinkedList<Sprite>();

		Point2D one = new Point2D(p.getX(),p.getY(),p.getLayer());
		Point2D two = new Point2D(p.getX()+s.getWidth()-1,p.getY(),p.getLayer());
		Point2D three = new Point2D(p.getX(),p.getY()+s.getHeight()-1,p.getLayer());
		Point2D four = new Point2D(p.getX()+s.getWidth()-1,p.getY()+s.getHeight()-1,p.getLayer());
		for(Sprite sprite : this.getSprites())
		{
			if(sprite==s)
			{
				continue;
			}

			Bounding b = sprite.getBounding();

			if(b != null)
			{
				if(b.withinBounds(one)||b.withinBounds(two)||b.withinBounds(three)||b.withinBounds(four))
				{
					rv.add(sprite);
				}
			}
		}

		return rv;
	}

	/**
	 * Returns true is the Sprite has collided with anything on the layer.
	 * 
	 * @param s - The Sprite being check.
	 * @return <b>true</b> if the Sprite has collided with anything, <b>false</b> otherwise.
	 */
	public boolean hasCollided(Sprite s)
	{
		if(!getCollisions(s.getPoint(),s).isEmpty())
		{
			return true;
		}
		return false;
	}

	/**
	 *  Pixel perfect collision detection. Can only be used when all sprites use a bounding box.
	 * @param p - Point to check for collisions
	 * @param a - The sprite checking for the collisions
	 * @return - LinkedList of sprites 
	 */
	public LinkedList<Sprite> getCollisions(Point2D p, Sprite a)
	{
		LinkedList<Sprite> rv = new LinkedList<Sprite>();
		LinkedList<Sprite> overlaps = getOverlaps(p,a);

		if(a.getBounding() instanceof BoundingCircle)
		{
			for(Sprite b: overlaps)
			{
				if(b.getBounding() instanceof BoundingCircle)
				{
					if(((BoundingCircle)a.getBounding()).withinBounds((BoundingCircle)b.getBounding()))
					{
						rv.add(b);
					}
				}
				else
				{
					if(((BoundingCircle)a.getBounding()).withinBounds((BoundingBox)b.getBounding()))
					{
						rv.add(b);
					}
				}
			}
		}

		else
		{
			for(Sprite b: overlaps)
			{
				if(b.getBounding() instanceof BoundingCircle)
				{
					if(((BoundingBox)a.getBounding()).withinBounds((BoundingCircle)b.getBounding()))
					{
						rv.add(b);
					}
				}

				else
				{
					int ax1 = p.getX();
					int ay1 = p.getY();
					int ax2 = p.getX() + a.getWidth() - 1;
					int ay2 = p.getY() + a.getHeight() - 1;

					int bx1, bx2, by1, by2;
					int cx1, cy1, cx2, cy2;

					int[] amask, bmask, bitmask;

					bx1 = b.getX();
					bx2 = b.getX() + b.getWidth() - 1;
					by1 = b.getY();
					by2 = b.getY() + b.getWidth() - 1;

					cx1 = Math.max(ax1,bx1);
					cy1 = Math.max(ay1,by1);
					cx2 = Math.min(ax2,bx2);
					cy2 = Math.min(ay2, by2);

					amask = a.print().getRGB(cx1-ax1, cy1-ay1, cx2-cx1+1, cy2-cy1+1, null, 0, cx2-cx1+1);
					bmask = b.print().getRGB(cx1-bx1, cy1-by1, cx2-cx1+1, cy2-cy1+1, null, 0, cx2-cx1+1);

					bitmask = ImageUtil.getCombinedBitMask(ImageUtil.getBitMask(amask), ImageUtil.getBitMask(bmask));

					for(int i = 0; i < bitmask.length; i++)
					{
						if(bitmask[i] == 0x1)
						{
							rv.add(b);
							break;
						}
					}
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

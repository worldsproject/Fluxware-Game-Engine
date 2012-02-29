package level;

import java.util.HashMap;
import java.util.LinkedList;

import sprites.Sprite;

/**
 * Default Room class. This is the general, broad overview of a room.
 * This particular type of room is used for full 2D freedom viewed in an orthagonal plane.
 * 
 * Generally to use, extend a room, define your Key Board based actions, and you're all set.
 * 
 * @author Tim Butram, CJ Robinson
 *
 */
public class Room
{	
	//Basic dimensions of the room.
	protected int height;
	protected int width;

	//Holds all of the sprites in the room.
	protected LinkedList<Sprite> allSprite = new LinkedList<Sprite>();
	protected HashMap<Integer, Sprite> spriteByID = new HashMap<Integer, Sprite>();

	public Room(){}

	public Room(int width, int height, int layers)
	{
		this.height = height;
		this.width = width;
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
	 * Returns a Sprite based on its serial number.
	 * @param id - The serial number being searched for.
	 * @return - The Sprite with the matching ID, null if no Sprite exists with given ID.
	 */
	public Sprite getSprite(int id)
	{	
		return spriteByID.get(id);
	}

	/**
	 * Returns all Sprites in the room.
	 * @return - All Sprites.
	 */
	public LinkedList<Sprite> getSprites()
	{
		return allSprite;
	}

	/**
	 * Adds a Sprite.
	 * @param sprite - The sprite to be added.
	 */
	public void addSprite(Sprite sprite)
	{
		allSprite.add(sprite);
		spriteByID.put(sprite.serial, sprite);
		sprite.setRoom(this);
	}

	/**
	 * Removes the Sprite passed into the method.
	 * @param sprite - The sprite to be removed.
	 */
	public void removeSprite(Sprite sprite) 
	{	
		allSprite.remove(sprite);
		spriteByID.remove(sprite);
		sprite.setRoom(null);
	}
	
	public Type getType()
	{
		return Type.NORMAL;
	}

	public String toString()
	{
		return "Width: " + this.getWidth() + " Height: " + this.getHeight();
	}
}

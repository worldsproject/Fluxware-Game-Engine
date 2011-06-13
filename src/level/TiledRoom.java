package level;
import java.awt.event.KeyListener;
import java.util.LinkedList;

import listener.bounding.TiledBoundingBox;
import sprites.Sprite;


/**
 * This is a default class for creating tile based rooms, such as those used in
 * Chess, Checkers or Pokemon.
 * 
 * The default tile size is 30px by 30px, and all such tiles are square.
 * <br /><br />
 * <B>To Use:</b>  Extend the class, to further define the Key and Mouse Listeners,
 * the one is able to use either the TiledRoom(File, HashMap) or 
 * TiledRoom(int width, int height, int layer) constructors to create the room.
 * <br /><br/>
 * The HashMap required by the TiledRoom(File, HashMap) can be defined as follows for
 * optimal performance: <br />
 * <pre>
 * HashMap&#060;Integer, Sprite&#062; example = new HashMap&#060;Integer, Sprite&#062;();
 * example.put(SpriteOne.serial, new SpriteOne());
 * example.put(SpriteTwo.serial, new SpriteTwo());
 * ...and so forth...
 * </pre> 
 * 
 * @author Tim Butram, CJ Robinson
 *
 */
public class TiledRoom extends Room
{
	//This is a default size of the cells.
	private int cellSize = 30;
	
	/**
	 * Creates and empty room with the given dimensions.
	 * 
	 * @param width - The Width of the room.
	 * @param height - The Height of the room.
	 * @param layers - The number of Layers in the room.
	 */
	public TiledRoom(int width, int height, int layers)
	{
		this.width = width * cellSize;
		this.height = height * cellSize;
		
		this.layers = layers;
		keylisteners = new LinkedList<KeyListener>();
	}
	
	/**
	 * Creates a TiledRoom with the given dimensions.
	 * 
	 * @param width - The width of the room.
	 * @param height - The height of the room.
	 * @param layers - The number of layers in the room.
	 * @param cs - The size of each cell.
	 */
	public TiledRoom(int width, int height, int layers, int cs)
	{
		cellSize = cs;
		this.width = width  * cellSize;
		this.height = height * cellSize;
		this.layers = layers;
		keylisteners = new LinkedList<KeyListener>();
	}
	
	/**
	 * Adds a Sprite.
	 * @param sprite - The sprite to be added.
	 */
	public void addSprite(Sprite sprite)
	{
		sprite.setBounding(new TiledBoundingBox(sprite, cellSize));

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
		
		x *= cellSize;
		y *= cellSize;

		for(Sprite s: as)
		{
			if(s.getX() == x && s.getY() == y)
			{
				as.remove(s);
			}
		}
	}

	/**
	 * 
	 * @return The size of the cells in the TiledRoom.
	 */
	public int getCellSize()
	{
		return cellSize;
	}
}
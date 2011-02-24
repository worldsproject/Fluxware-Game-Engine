package sprites;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.io.Serializable;

import listener.bounding.Bounding;
import listener.bounding.BoundingBox;
import util.Point2D;

public class Sprite implements Serializable
{
	public int serial = 0;
	public int id = 0;
	private static int internalID = 0;

	private transient BufferedImage o = new BufferedImage(1, 1, 1);

	protected int state;
	protected Point2D location;
	
	protected Bounding box = null;

	public Sprite()
	{
		o = null;
		state = 0;
		
		location = new Point2D(-1, -1, 0);
		
		id = ++internalID;
		
		box = new BoundingBox(this);
		box.updateBounds();
	}

	public Sprite(BufferedImage img, int x, int y, int layer)
	{
		o = img;
		
		location = new Point2D(x, y, layer);
		
		id = ++internalID;
		
		box = new BoundingBox(this);
		box.updateBounds();
	}

	/**
	 * @return The current X coordinate of the Sprite.
	 */
	public int getX() 
	{
		return location.getX();
	}

	/**
	 * 
	 * @param x - Sets the Sprites current X coordinate to this value.
	 */
	public void setX(int x)
	{
		location.setX(x);
	}

	/**
	 * @return The current Y coordinate of the Sprite.
	 */
	public int getY() 
	{
		return location.getY();
	}

	/**
	 * @param y - Sets the Sprites current Y coordinate to this value.
	 */
	public void setY(int y)
	{
		location.setY(y);
	}

	/**
	 * @return The layer the Sprite is currently residing on.
	 */
	public int getLayer()
	{
		return location.getLayer();
	}

	/**
	 * @param layer - Changes the Sprites layer to this value.
	 */
	public void setLayer(int layer)
	{
		location.setLayer(layer);
	}

	public int getState()
	{
		return state;
	}

	public void setState(int state)
	{
		this.state = state;
	}

	public void setSprite(BufferedImage img)
	{
		o = img;
	}

	public void update(long lastUpdate, long totalTime)
	{
		box.updateBounds();
	}
	
	/**
	 * Compares two Sprites.  If the Sprites occupy the same spot,
	 * the method returns true, otherwise the method returns false.
	 * 
	 * @param s The other Sprite to be compared.
	 * @return true if the Sprites have the same coordinates, false if otherwise.
	 */
	public boolean equals(Sprite s)
	{
		if(s.getX() == this.getX() && s.getY() == this.getY() && s.getLayer() == this.getLayer())
			return true;

		return false;
	}

	/**
	 * Returns the Object to be printed.
	 * @return The Object that represents the Sprite.
	 */
	public BufferedImage print()
	{
		return o;
	}
	
	public Point2D getPoint()
	{
		return location;
	}
	
	public void setPoint(Point2D p)
	{
		location = p;
	}
	
	public int getWidth()
	{
		return o.getWidth();
	}
	
	public int getHeight()
	{
		return o.getHeight();
	}

	public Bounding getBounding()
	{
		return box;
	}
	
	public void setBounding(Bounding b)
	{
		box = b;
	}
	
	public String toString()
	{
		return "Coords: (" + this.getX() + ", " + this.getY() + ")";
	}
	
	private void writeObject(java.io.ObjectOutputStream out) throws IOException
	{
		out.defaultWriteObject();
		int w = o.getWidth();
		int h = o.getHeight();
		int[] rgb = new int[w*h];
		o.getRGB(0, 0, w, h, rgb, 0, w);  //I find this odd that it works as a function and procedure.
		
		out.writeObject(rgb);
		out.writeObject(new Integer(w));
		out.writeObject(new Integer(h));
	}
	
	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		int[] rgb = (int[]) in.readObject();
		int width = ((Integer) in.readObject()).intValue();
		int height = ((Integer) in.readObject()).intValue();
		
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        WritableRaster raster = (WritableRaster) image.getData();
        raster.setPixels(0, 0, width, height, rgb);
        
        o = image;
	}
}

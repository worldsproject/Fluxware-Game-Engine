package sprites;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.io.IOException;
import java.io.Serializable;

import util.Point2D;
import util.Texture;
import collision.BoundingBox;

public class Sprite implements Serializable
{
	public final int serial;
	private static int internalID = 0;
	
	protected Texture texture;

	protected int state;
	protected Point2D location;
	
	protected BoundingBox box = null;
	
	protected boolean dead = false;

	public Sprite()
	{
		texture = null;
		state = 0;
		
		location = new Point2D(-1, -1, 0);
		
		serial = ++internalID;
		
		box = new BoundingBox(this);
		box.updateBounds();
	}

	public Sprite(Texture tex, int x, int y, int layer)
	{
		texture = tex;
		
		location = new Point2D(x, y, layer);
		
		serial = ++internalID;
		
		box = new BoundingBox(this);
		box.updateBounds();
	}

	/**
	 * @return The current X coordinate of the Sprite.
	 */
	public double getX() 
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
		box.updateBounds();
	}

	/**
	 * @return The current Y coordinate of the Sprite.
	 */
	public double getY() 
	{
		return location.getY();
	}

	/**
	 * @param y - Sets the Sprites current Y coordinate to this value.
	 */
	public void setY(int y)
	{
		location.setY(y);
		box.updateBounds();
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
		box.updateBounds();
	}

	public int getState()
	{
		return state;
	}

	public void setState(int state)
	{
		this.state = state;
	}

	public void setSprite(Texture tex)
	{
		texture = tex;
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
	public void draw()
	{
		glPushMatrix();
		texture.bind();
		glTranslatef((int)location.getX(), (int)location.getY(), location.getLayer());
		
		glBegin(GL_QUADS);
		{
			glTexCoord2f(0, 0);
			glVertex2f(0, 0);
			
			glTexCoord2f(0, texture.getHeight());
			glVertex2f(0, getHeight());
			
			glTexCoord2f(texture.getWidth(), texture.getHeight());
			glVertex2f(getWidth(), getHeight());
			
			glTexCoord2f(texture.getWidth(), 0);
			glVertex2f(getWidth(), 0);
		}
	}
	
	public Point2D getPoint()
	{
		return location;
	}
	
	public void setPoint(Point2D p)
	{
		location = p;
		box.updateBounds();
	}
	
	public int getWidth()
	{
		return texture.getImageWidth();
	}
	
	public int getHeight()
	{
		return texture.getImageHeight();
	}

	public BoundingBox getBoundingBox()
	{
		return box;
	}
	
	public void setBoundingBox(BoundingBox b)
	{
		box = b;
	}
	
	public boolean isGarbage()
	{
		return dead;
	}
	
	public void setAsGarbage(boolean garbage)
	{
		dead = garbage;
	}
	
	public String toString()
	{
		return "Coords: (" + this.getX() + ", " + this.getY() + ")";
	}
	
	private void writeObject(java.io.ObjectOutputStream out) throws IOException //TODO
	{
//		out.defaultWriteObject();
//		int w = o.getWidth();
//		int h = o.getHeight();
//		int[] rgb = new int[w*h];
//		o.getRGB(0, 0, w, h, rgb, 0, w);  //I find this odd that it works as a function and procedure.
//		
//		out.writeObject(rgb);
//		out.writeObject(new Integer(w));
//		out.writeObject(new Integer(h));
	}
	
	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException //TODO
	{
//		in.defaultReadObject();
//		int[] rgb = (int[]) in.readObject();
//		int width = ((Integer) in.readObject()).intValue();
//		int height = ((Integer) in.readObject()).intValue();
//		
//		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
//        WritableRaster raster = (WritableRaster) image.getData();
//        raster.setPixels(0, 0, width, height, rgb);
//        
//        o = image;
	}
}

package sprites;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;

import level.Room;
import util.ImageData;
import util.Point2D;
import collision.BoundingBox;

public class Sprite implements Serializable
{
	public final int serial;
	private static int internalID = 0;
	
	protected ImageData imageData;

	protected int state;
	protected Point2D location;
	
	protected BoundingBox box = null;
	
	protected boolean dead = false;
	
	protected double dy = 0;
	protected double dx = 0;
	
	protected Room room;
	
	//Sprite sheet adjustments.
	protected int columns = 1;
	protected int rows = 1;
	protected int which_column = 0;
	protected int which_row = 0;
	protected int wide = 1;
	protected int tall = 1;

	public Sprite()
	{
		imageData = null;
		state = 0;
		
		location = new Point2D(-1, -1, 0);
		
		serial = ++internalID;
		
		box = new BoundingBox(this);
		box.updateBounds();
	}

	public Sprite(ImageData tex, int x, int y, int layer)
	{
		imageData = tex;
		
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
		return location.x;
	}

	/**
	 * 
	 * @param x - Sets the Sprites current X coordinate to this value.
	 */
	public void setX(double x)
	{
		location.x = x;
		box.updateBounds();
	}

	/**
	 * @return The current Y coordinate of the Sprite.
	 */
	public double getY() 
	{
		return location.y;
	}

	/**
	 * @param y - Sets the Sprites current Y coordinate to this value.
	 */
	public void setY(double y)
	{
		location.y = y;
		box.updateBounds();
	}

	/**
	 * @return The layer the Sprite is currently residing on.
	 */
	public int getLayer()
	{
		return location.layer;
	}

	/**
	 * @param layer - Changes the Sprites layer to this value.
	 */
	public void setLayer(int layer)
	{
		location.layer = layer;
		box.updateBounds();
	}

	public void setSprite(ImageData tex)
	{
		imageData = tex;
	}
	
	public void setHorizontalMovementSpeed(double dx)
	{
		this.dx = dx;
	}
	
	public void setVerticalMovementSpeed(double dy)
	{
		this.dy = dy;
	}
	
	public double getHorizontalMovementSpeed()
	{
		return dx;
	}
	
	public double getVerticalMovementSpeed()
	{
		return dy;
	}
	
	public void updateBounds()
	{
		box.updateBounds();
	}
	
	public void move(long delta)
	{
		location.x += (delta * dx) / 1000;
		location.y += (delta * dy) / 1000;
	}
	
	public void logic()
	{
		
	}
	
	public void collisions(LinkedList<Sprite> collisions)
	{
		
	}
	
	public void setRoom(Room r)
	{
		room = r;
	}
	
	public void setSpriteSheetPosition(int columns, int rows, int which_column, int which_row, int wide, int tall)
	{
		this.columns = columns;
		this.rows = rows;
		this.which_column = which_column;
		this.which_row = which_row;
		this.wide = wide;
		this.tall = tall;
	}

	/**
	 * Returns the Object to be printed.
	 * @return The Object that represents the Sprite.
	 */
	public void draw()
	{
		glPushMatrix();
		imageData.getTexture().bind();
		int tx = (int)location.x;
		int ty = (int)location.y;
		glTranslatef(tx, ty, location.layer);
		
//		float height = imageData.getTexture().getHeight();
//		float width = imageData.getTexture().getWidth();
		
		float texture_X = ((float)which_column/(float)columns);
		float texture_Y = ((float)which_row/(float)rows);
		float texture_XplusWidth = ((float)(which_column+wide)/(float)columns);
		float texture_YplusHeight = ((float)(which_row+tall)/(float)rows);
		
		glBegin(GL_QUADS);
		{
			glTexCoord2f(texture_X, texture_Y);
			glVertex2f(0, 0);
			
			glTexCoord2f(texture_X, texture_YplusHeight);
			glVertex2f(0, getHeight());
			
			glTexCoord2f(texture_XplusWidth, texture_YplusHeight);
			glVertex2f(getWidth(), getHeight());
			
			glTexCoord2f(texture_XplusWidth, texture_Y);
			glVertex2f(getWidth(), 0);
		}
		glEnd();
		glPopMatrix();
	}
	
	public Point2D getLocation()
	{
		return location;
	}
	
	public void setLocation(Point2D p)
	{
		location = p;
		box.updateBounds();
	}
	
	public int getWidth()
	{
		return imageData.getTexture().getWidth()/columns;
	}
	
	public int getHeight()
	{
		return imageData.getTexture().getHeight()/rows;
	}
	
	public boolean[][] getMask()
	{
		return this.imageData.getMask();
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

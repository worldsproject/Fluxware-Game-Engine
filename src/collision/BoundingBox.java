package collision;

import java.awt.Rectangle;

import sprites.Sprite;
import util.Point2D;

@SuppressWarnings("serial")
public class BoundingBox
{
	private Sprite bound = null;
	private Rectangle rect = null;
	
	public BoundingBox(Sprite s)
	{
		bound = s;
		
		try
		{
			rect = new Rectangle((int)bound.getX(), (int)bound.getY(), bound.getWidth(), bound.getHeight());
		}
		catch(NullPointerException e)
		{
			rect = new Rectangle(30, 30, 30, 30);
		}
	}
	
	public void updateBounds() 
	{
		try
		{
			rect.x = (int)bound.getX();
			rect.y = (int)bound.getY();
			rect.width = bound.getWidth();
			rect.height = bound.getHeight();
		}
		catch(NullPointerException e)
		{
			rect.x = 30;
			rect.y = 30;
			rect.width = 30;
			rect.height = 30;
		}
		
	}

	public boolean withinBounds(Point2D p) 
	{
		double x = p.getX();
		double y = p.getY();
		
		if(p.getLayer() == bound.getLayer())
		{
			if(rect == null)
			{
				return false;
			}
			
			return rect.contains(x, y);
		}
		else
		{
			return false;
		}
	}
	
	public int getX()
	{
		return rect.x;
	}
	
	public int getY()
	{
		return rect.y;
	}
	
	public int getWidth()
	{
		return rect.width;
	}
	
	public int getHeight()
	{
		return rect.height;
	}

	public boolean withinBounds(BoundingBox box) 
	{	
		if(bound.getLayer() != bound.getLayer())
			return false;
		
		return rect.intersects(box.rect);
	}
}

package listener.bounding;

import java.awt.Rectangle;

import sprites.Sprite;
import util.Point2D;

@SuppressWarnings("serial")
public class BoundingBox extends Bounding 
{
	protected Rectangle rect = null;
	
	public BoundingBox(Sprite s)
	{
		super(s);
		
		try
		{
			rect = new Rectangle((int)bound.getX(), (int)bound.getY(), bound.getWidth(), bound.getHeight());
		}
		catch(NullPointerException e)
		{
			rect = new Rectangle(30, 30, 30, 30);
		}
	}
	
	@Override
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

	@Override
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
		if(this.getLayer() != box.getLayer())
			return false;
		
		return rect.intersects(box.rect);
	}
	
	public boolean withinBounds(BoundingCircle circle)
	{
		int radius = circle.getRadius();
		Point2D center = circle.getCenter();
		
		double cx = Math.abs(center.getX() - rect.x - rect.width/2);
		double cy = Math.abs(center.getY() - rect.y - rect.height/2);
		
		if((cx>radius+rect.width/2)||(cy>radius+rect.height/2))
		{
			return false;
		}
		
		else if((cx<=rect.width/2)||(cy<=rect.height/2))
		{
			return true;
		}
		
		else
		{
			return (((cx-rect.width/2)*(cx-rect.width/2))+((cy-rect.height/2)*(cy-rect.height/2))<=radius*radius);
		}
		
	}
	
}

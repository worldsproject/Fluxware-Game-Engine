package listener.bounding;

import java.awt.Rectangle;

import sprites.Sprite;
import util.Point2D;

public class BoundingBox extends Bounding 
{
	protected Rectangle rect = null;
	
	public BoundingBox(Sprite s)
	{
		super(s);
		
		rect = new Rectangle(bound.getX(), bound.getY(), bound.getWidth(), bound.getHeight());
	}
	
	@Override
	public void updateBounds() 
	{
		if(bound.print() != null)
		{
			rect.x = bound.getX();
			rect.y = bound.getY();
			rect.width = bound.getWidth();
			rect.height = bound.getHeight();
		}
		
	}

	@Override
	public boolean withinBounds(Point2D p) 
	{
		int x = p.getX();
		int y = p.getY();
		
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
	
	public int getLayer()
	{
		return bound.getLayer();
	}

	public boolean withinBounds(Bounding box) 
	{
		if((box instanceof BoundingBox) == false)
			return false;
		
		BoundingBox b = (BoundingBox)box;
		
		Point2D one = new Point2D(b.getX(), b.getX(), b.getLayer());  //Top Left Corner.
		Point2D two = new Point2D(b.getX() + b.getWidth()-1, b.getY(), b.getLayer());  //Top Right Corner.
		Point2D thr = new Point2D(b.getX(), b.getY() + b.getHeight()-1, b.getLayer());  //Bottom Left Corner.
		Point2D fou = new Point2D(b.getX() + b.getWidth()-1, b.getY() + b.getHeight()-1, b.getLayer());  //Bottom Right Corner.
		
		if(this.withinBounds(one) || this.withinBounds(two) || this.withinBounds(thr) || this.withinBounds(fou))
		{
			return true;
		}
		
		return false;
	}
	
}

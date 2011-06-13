package listener.bounding;

import sprites.Sprite;
import util.FluxMath;
import util.Point2D;

/**
 * Creates a Bounding that is centered around the given Sprite.
 * The radius of the circle is given by the biggest between the 
 * height or width.
 */
@SuppressWarnings("serial")
public class BoundingCircle extends Bounding 
{
	private Point2D center = null;
	private int radius = 0;
	
	/**
	 * Creates a BoundingCircle around the given Sprite.
	 * @param s - The Sprite the bounds are based off of.
	 */
	public BoundingCircle(Sprite s)
	{
		bound = s;
		
		int re[] = findCenter();
		center = new Point2D(re[0], re[1], s.getLayer());
		
		int sw = s.getWidth();
		int sh = s.getHeight();
		
		radius = (((int)Math.max(sw, sh))/ 2);
	}
	
	/*
	 * Returns the (X,Y) of the center of BoundingCircle. 
	 * @return An int[] where [0] is the X coordinate and [1] is the Y coordinate.
	 */
	private int[] findCenter()
	{
		int[] rv = new int[2];
		
		rv[0] = bound.getX() + (bound.getWidth() >> 1);
		rv[1] = bound.getY() + (bound.getHeight() >> 1);
		
		return rv;
	}
	
	@Override
	public void updateBounds() 
	{
		int[] re = findCenter();
		
		center.setX(re[0]);
		center.setY(re[1]);
	}

	@Override
	public boolean withinBounds(Point2D p) 
	{
		double result = FluxMath.distance(p, center);
		
		return Math.abs(result) > radius; 
	}

	/**
	 * Checks to see if two BoundingCircles are intersecting.
	 * 
	 * @param b - The BoundingCircle to be checked against.
	 * @return <b>true</b> if they intersect, <b>false</b> otherwise.
	 */
	public boolean withinBounds(BoundingCircle b) 
	{
		return withinBounds(b.getCenter());
	}
	
	/**
	 * Checks to see if a BoundingBox and this object is intersecting.
	 * @param box - The BoundingBox to be checked against.
	 * @return <b>true</b> if the BoundingCircle and BoundingBox intersect, <b>false</b> otherwise.
	 */
	public boolean withinBounds(BoundingBox box)
	{
		int width = box.getWidth();
		int height = box.getHeight();

		int cx = Math.abs(center.getX() - box.getX() - width/2);
		int cy = Math.abs(center.getY() - box.getY() - height/2);
		
		if((cx>radius+width/2)||(cy>radius+height/2))
		{
			return false;
		}
		
		else if((cx<=width/2)||(cy<=height/2))
		{
			return true;
		}
		
		else
		{
			return (((cx-width/2)*(cx-width/2))+((cy-height/2)*(cy-height/2))<=radius*radius);
		}
		
	}
	
	/**
	 * Returns the Point2D of the center of the BoundingCircle
	 * @return A Point2D of the center of the BoundingCircle.
	 */
	public Point2D getCenter()
	{
		return center;
	}
	
	/**
	 * Returns the radius of the BoundingCircle.
	 * @return Returns the radius as an int.
	 */
	public int getRadius()
	{
		return radius;
	}
}

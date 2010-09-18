package listener.bounding;

import sprites.Sprite;
import util.FluxMath;
import util.Point2D;

/**
 * Creates a Bounding that is centered around the given Sprite.
 * The radius of the circle is given by the biggest between the 
 * height or width.
 */
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
	 * @return true if they intersect, false otherwise.
	 */
	public boolean withinBounds(BoundingCircle b) 
	{
		return withinBounds(b.getCenter());
	}
	
	protected Point2D getCenter()
	{
		return center;
	}

	public boolean withinBounds(Bounding b) {
		// TODO Auto-generated method stub
		return false;
	}

}

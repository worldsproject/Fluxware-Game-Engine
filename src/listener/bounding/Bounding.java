package listener.bounding;

import java.io.Serializable;

import sprites.Sprite;
import util.Point2D;

/**
 * Bounding defines the two main methods that define Polygonal collision detection.
 * @author Tim Butram
 *
 */
@SuppressWarnings("serial")
public abstract class Bounding implements Serializable
{
	protected Sprite bound;
	
	public Bounding(){
		
		bound = null;
	}
	
	public Bounding(Sprite s){
		
		bound = s;
	}
	
	/**
	 * Updates the location of the Bounding polygon to match the Sprites movement.
	 */
	public abstract void updateBounds();
	
	/**
	 * Checks to see if <b>p</b> is within the objects bounds.
	 * @param p - The point to be tested.
	 * @return <b>true</b> if the point is within this objects bounds, <b>false</b> otherwise.
	 */
	public abstract boolean withinBounds(Point2D p);
}

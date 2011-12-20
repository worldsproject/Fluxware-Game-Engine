package util;

import java.io.Serializable;

/**
 * The Point class encapsulates a single point in a TiledRoom
 * containing the X and Y coordinate, along with the Layer.
 */
public class Point2D implements Serializable
{
	public double x;
	public double y;
	public int layer;

	/**
	 * The Point class encapsulates a single point in a TiledRoom
	 * containing the X and Y coordinate, along with the Layer.
	 * 
	 * @param x - double of the X coordinate in the TiledRoom.
	 * @param y - double of the Y coordinate in the TiledRoom.
	 * @param layer - int of the Layer in the TiledRoom.
	 */
	public Point2D(double x, double y, int layer)
	{
		this.x = x;
		this.y = y;
		this.layer = layer;
	}

	/**
	 * Matches itself to the given Point2D.  If all 3 coordinates match (x, y, layer)
	 * the method returns true, otherwise false.
	 * 
	 * @param p - The outside Point2D to be matched.
	 * @return - true if all 2 coordinates match (x, y, layer), false if otherwise.
	 */
	public boolean equals(Point2D p)
	{
		return (p.x == x && p.y == y && p.layer == layer) ? true : false; 
	}
	
	/**
	 * Scales the X and Y coordinates of the Point2D.  Does NOT scale the layer.
	 * @param multiple - The number that the X and Y coordinate are to be multiplied by.
	 * @return A Point2D with scaled X and Y.
	 */
	public Point2D scale(int multiple)
	{
		return new Point2D(x * multiple, y * multiple, layer);
	}
}

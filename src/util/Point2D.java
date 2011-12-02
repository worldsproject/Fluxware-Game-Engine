package util;

import java.io.Serializable;

/**
 * The Point class encapsulates a single point in a TiledRoom
 * containing the X and Y coordinate, along with the Layer.
 */
public class Point2D implements Serializable
{
	private double x;
	private double y;
	private int layer;

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
	 * Returns the X coordinate of the Point.
	 * 
	 * @return int of the X coordinate of the Point.
	 */
	public double getX()
	{
		return x;
	}

	/**
	 * Returns the Y coordinate of the Point.
	 * 
	 * @return int of the Y coordinate of the Point.
	 */
	public double getY()
	{
		return y;
	}

	/**
	 * Return the Layer of the Point.
	 * 
	 * @return int of the Layer of the Point.
	 */
	public int getLayer()
	{
		return layer;
	}

	/**
	 * Set the x coordinate of the Point.
	 * 
	 * @param x - int of the x coordinate.
	 */
	public void setX(double x)
	{
		this.x = x;
	}

	/**
	 * Sets the y coordinate of the Point.
	 * 
	 * @param y - int of the y coordinate.
	 */
	public void setY(double y)
	{
		this.y = y;
	}

	/**
	 * Sets the layer of the Point.
	 * 
	 * @param layer - int of the layer position.
	 */
	public void setLayer(int layer)
	{
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
		return (p.getX() == x && p.getY() == y && p.getLayer() == layer) ? true : false; 
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

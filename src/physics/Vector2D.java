package physics;

/**
 * Class Vector2D defines a Vector which will be used in all Physics simulations
 * in the Fluxware Game Engine.  The Vector2D has both a Direction and Magnitude.
 * <br><br>
 * The units are as follows:<br>
 * <ul>
 * <li>Magnitude is defined in <i>Meters per second</i>
 * <li>Direction is defined in <i>Radians</i>
 * </ul>
 * @author atrus
 *
 */
public class Vector2D 
{
	private double direction = 0;
	private double magnitude = 0;
	
	/**
	 * Creates a Default Vector2D with a direction of 0, and a magnitude of 0.
	 */
	public Vector2D(){}
	
	/**
	 * Creates a Vector2D with the given direction and magnitude.
	 * 
	 * @param dir - The Direction the Vector2D is pointing.
	 * @param mag - The Magnitude of the Vector2D.
	 */
	public Vector2D(double dir, double mag)
	{
		direction = dir;
		magnitude = mag;
	}

	/**
	 * Standard setter for Direction.
	 * @param dir - The new Direction of the Vector2D.
	 */
	public void setDirection(double dir)
	{
		direction = dir;
	}
	
	/**
	 * Standard setter for Magnitude.
	 * @param mag - The new Magnitude of the Vector2D.
	 */
	public void setMagnitude(double mag)
	{
		magnitude = mag;
	}
	
	/**
	 * Standard getter for the Direction of the Vector 2D
	 * @return The Vector2Ds direction.
	 */
	public double getDirection()
	{
		return direction;
	}
	
	/**
	 * Standard getter for the Magnitude of the Vector 2D.
	 * @return The Vector2Ds magnitude.
	 */
	public double getMagnitude()
	{
		return magnitude;
	}
	
	/**
	 * Gets the X Component of the Vector2D
	 * @return the X component.
	 */
	public double getXComponent()
	{
		return this.getMagnitude() * Math.cos(this.getDirection());
	}
	
	/**
	 * Gets the Y Component of the Vector2D
	 * @return The Y component.
	 */
	public double getYComponent()
	{
		return this.getMagnitude() * Math.sin(this.getDirection());
	}
	
	/**
	 * Adds two Vector2Ds together.  Returns the resulting Vector2D.
	 * 
	 * @param one - The first Vector2D to be added.
	 * @param two - The second Vector2D to be added.
	 * @return The result of the addition of the two given Vector2Ds.
	 */
	public static Vector2D add(Vector2D one, Vector2D two)
	{
		double x1 = one.getXComponent();
		double y1 = one.getYComponent();
		
		double x2 = two.getXComponent();
		double y2 = two.getYComponent();
		
		double xr = x1 - x2;
		double yr = y1 - y2;
		
		double dr = Math.atan(yr / xr);
		double mr = Math.sqrt(Math.pow(xr, 2) + Math.pow(yr, 2));
		
		return new Vector2D(dr, mr);
	}
	
	/**
	 * Multiplies two Vector2Ds together. Returns the resulting Vector2D.
	 * 
	 * @param one - The first Vector2D multiplicand.
	 * @param two - The second Vector2D multiplicand.
	 * @return - The product of the two Vector2Ds.
	 */
	public static Vector2D multiply(Vector2D one, Vector2D two) //TODO
	{
		return null;
	}
	
	public String toString()
	{
		return "X component: " + this.getXComponent() + "\nY component: " + this.getYComponent() + "\nDirection: " + this.getDirection() + "\nMagnitude: " + this.getMagnitude(); 
	}

}

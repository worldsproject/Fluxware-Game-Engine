package listener.bounding;

import java.io.Serializable;

import sprites.Sprite;
import util.Point2D;

public abstract class Bounding implements Serializable
{
	protected Sprite bound;
	
	public Bounding(){
		
		bound = null;
	}
	
	public Bounding(Sprite s){
		
		bound = s;
	}
	
	public int getLayer()
	{
		return bound.getLayer();
	}
	
	public abstract void updateBounds();
	public abstract boolean withinBounds(Point2D p);
}

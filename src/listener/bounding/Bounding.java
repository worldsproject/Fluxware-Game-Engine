package listener.bounding;

import java.io.Serializable;

import sprites.Sprite;
import util.Point2D;

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
	
	public abstract void updateBounds();
	public abstract boolean withinBounds(Point2D p);
	public abstract boolean withinBounds(Bounding b);
}

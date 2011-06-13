package listener.bounding;

import java.awt.Rectangle;

import sprites.Sprite;

@SuppressWarnings("serial")
public class TiledBoundingBox extends BoundingBox{

	protected int cellSize;
	
	public TiledBoundingBox(Sprite s, int cellSize) {
		super(s);
		this.cellSize = cellSize;
		updateBounds();
	}
	
	@Override
	public void updateBounds() 
	{
		if(bound.print() != null)
		{
			rect = new Rectangle(bound.getX() * cellSize, bound.getY() * cellSize, bound.getWidth(), bound.getHeight());
		}
		
	}

	
	
}

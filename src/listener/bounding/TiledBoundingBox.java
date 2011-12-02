package listener.bounding;

import java.awt.Rectangle;

import sprites.Sprite;

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
			rect = new Rectangle((int)bound.getX() * cellSize, (int)bound.getY() * cellSize, bound.getWidth(), bound.getHeight());
		}
		
	}

	
	
}

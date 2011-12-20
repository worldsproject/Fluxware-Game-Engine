package level;

public class TiledRoom extends Room
{
	protected int cellSize = 32;
	
	public TiledRoom(){}

	public TiledRoom(int cellsize, int width, int height, int layers)
	{
		this.height = height;
		this.width = width;
		this.layers = layers;
		this.cellSize = cellsize;
	}
	
	public int getCellSize()
	{
		return cellSize;
	}
}

package level;

public class TiledRoom extends Room
{
	protected int cellSize = 32;
	
	public TiledRoom(){}

	public TiledRoom(int cellsize, int width, int height)
	{
		this.height = height;
		this.width = width;
		this.cellSize = cellsize;
	}
	
	public int getCellSize()
	{
		return cellSize;
	}
	
	public Type getType()
	{
		return Type.TILED;
	}
}

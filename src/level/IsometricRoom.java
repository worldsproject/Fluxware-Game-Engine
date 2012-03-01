package level;

public class IsometricRoom extends Room
{
	private int tile_height = 60;
	private int tile_width = 64;
	
	public IsometricRoom(int width, int height)
	{
		super(width, height);
	}
	
	public IsometricRoom(int width, int height, int tileWidth, int tileHeight)
	{
		super(width, height);
		
		tile_height = tileHeight;
		tile_width = tileHeight;
	}
	
	public void setTileHeight(int height)
	{
		tile_height = height;
	}
	
	public void setTileWidth(int width)
	{
		tile_width = width;
	}
	
	public Type getType()
	{
		return Type.ISOMETRIC;
	}
}

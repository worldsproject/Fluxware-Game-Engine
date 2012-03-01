package level;


public class HexRoom extends Room
{
	private int tile_width = 64;
	private int tile_height = 64;
	
	private double r = 0;
	private double h = 0;
	private double s = 0;
	
	private Type type = Type.FLAT_HEX;
	
	public HexRoom(int width, int height)
	{
		this(width, height, 64, 64, Type.FLAT_HEX);
	}
	
	public HexRoom(int width, int height, int tile_width, int tile_height)
	{
		this(width, height, tile_width, tile_height, Type.FLAT_HEX);
	}
	
	public HexRoom(int width, int height, int tile_width, int tile_height, Type type)
	{
		super(width, height);
		
		if(type != Type.POINTED_HEX || type != Type.FLAT_HEX)
			throw new IllegalArgumentException("HexRoom may only be of POINTED_HEX or FLAT_HEX type.");
		
		setTileWidth(tile_width);
		setTileHeight(tile_height);
		this.type = type;
	}
		
	public int getTileWidth()
	{
		return tile_width;
		
	}

	public void setTileWidth(int tile_width)
	{
		this.tile_width = tile_width;
		
		adjust_dimensions();
	}
	
	

	public int getTileHeight()
	{
		return tile_height;
	}

	public void setTileHeight(int tile_height)
	{
		this.tile_height = tile_height;
		
		adjust_dimensions();
	}

	public Type getType()
	{
		return type;
	}
	
	private void adjust_dimensions()
	{
		if(type == Type.POINTED_HEX)
		{
			r = tile_width / 2;
			s = tile_height / 2;
			h = s / 2;
		}
		else
		{
			r = tile_height / 2;
			s = tile_width / 2;
			h = s / 2;
		}
	}
}

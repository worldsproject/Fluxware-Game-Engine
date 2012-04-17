package tests.simple.roomtypes;

import gui.Game;
import level.HexRoom;
import level.Type;
import sprites.Sprite;
import util.ImageData;

public class FlatHex extends Game
{
	public FlatHex(HexRoom r)
	{
		super(r, true);
		
		ImageData one = util.ImageUtil.loadTexture("/tests/resources/hex/flat/clayHex.png");
		
		Sprite a = new Sprite(one, 0, 0);
		Sprite b = new Sprite(one, 1, 0);
		Sprite c = new Sprite(one, 2, 0);
		Sprite d = new Sprite(one, 0, 1);
		Sprite e = new Sprite(one, 1, 1);
		
		r.addSprite(a);
		r.addSprite(b);
		r.addSprite(c);
		r.addSprite(d);
		r.addSprite(e);
	}
	
	public static void main(String args[])
	{
		HexRoom hr = new HexRoom(500, 500, 64, 55, Type.FLAT_HEX);
		FlatHex fh = new FlatHex(hr);
		fh.startGame();
	}
}

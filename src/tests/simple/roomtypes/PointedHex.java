package tests.simple.roomtypes;

import gui.Game;
import level.HexRoom;
import level.Type;
import sprites.Sprite;
import util.ImageData;

public class PointedHex extends Game
{
	public PointedHex(HexRoom r)
	{
		super(r, true);
		
		ImageData one = util.ImageUtil.loadTexture("/tests/resources/hex/pointed/clayHex.png");
		
		Sprite a = new Sprite(one, 0, 0);
		Sprite b = new Sprite(one, 1, 0);
		Sprite c = new Sprite(one, 2, 0);
		Sprite d = new Sprite(one, 0, 1);
		Sprite e = new Sprite(one, 1, 1);
		Sprite f = new Sprite(one, 0, 2);
		Sprite g = new Sprite(one, 1, 2);
		
		
		r.addSprite(a);
		r.addSprite(b);
		r.addSprite(c);
		r.addSprite(d);
		r.addSprite(e);
		r.addSprite(f);
		r.addSprite(g);
	}
	
	public static void main(String args[])
	{
		HexRoom hr = new HexRoom(500, 500, 55, 64, Type.POINTED_HEX);
		PointedHex fh = new PointedHex(hr);
		fh.startGame();
	}
}

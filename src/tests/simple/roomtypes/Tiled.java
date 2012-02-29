package tests.simple.roomtypes;

import gui.Game;
import level.TiledRoom;
import sprites.Sprite;
import util.ImageData;

public class Tiled extends Game
{
	public Tiled(TiledRoom r)
	{
		super(r, false);
		
		ImageData sheet = util.ImageUtil.loadTexture("/tests/resources/tiled/tiles.png");
		Sprite one = new Sprite(sheet, 0, 0, 0);
		one.setSpriteSheetPosition(2, 2, 0, 0, 1, 1);
		
		Sprite two = new Sprite(sheet, 1, 0, 0);
		two.setSpriteSheetPosition(2, 2, 0, 0, 1, 1);
		
		Sprite three = new Sprite(sheet, 2, 0, 0);
		three.setSpriteSheetPosition(2, 2, 0, 0, 1, 1);
		
		Sprite four = new Sprite(sheet, 3, 0, 0);
		four.setSpriteSheetPosition(2, 2, 0, 0, 1, 1);
		
		r.addSprite(one);
		r.addSprite(two);
		r.addSprite(three);
		r.addSprite(four);
	}
	
	public static void main(String args[])
	{
		TiledRoom r = new TiledRoom(32, 100, 100);
		Tiled t = new Tiled(r);
		t.startGame();
	}
}

package tests.simple.roomtypes;

import gui.Game;
import level.IsometricRoom;
import sprites.Sprite;
import util.ImageData;

public class Isometric extends Game
{
	public Isometric(IsometricRoom r)
	{
		super(r, false);
		
		ImageData id = util.ImageUtil.loadTexture("/tests/resources/isometric/grassland_tiles.png");
		
		Sprite one = new Sprite(id, 0, 0);
		one.setSpriteSheetPosition(16, 42, 1, 0);
		
		Sprite two = new Sprite(id, 1, 0);
		two.setSpriteSheetPosition(16, 42, 1, 0);
		
		Sprite three = new Sprite(id, 0, 1);
		three.setSpriteSheetPosition(16, 42, 1, 0);
		
		r.addSprite(one);
		r.addSprite(two);
		r.addSprite(three);
		
	}
	
	public static void main(String args[])
	{
		IsometricRoom r = new IsometricRoom(500, 500);
		Isometric i = new Isometric(r);
		i.startGame();
	}
}

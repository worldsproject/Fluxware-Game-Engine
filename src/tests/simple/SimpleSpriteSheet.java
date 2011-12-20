package tests.simple;

import gui.Game;
import level.Room;
import sprites.Sprite;
import util.ImageData;
import util.ImageUtil;

public class SimpleSpriteSheet extends Game 
{

	public SimpleSpriteSheet(Room room) 
	{
		super(room, false);
		
		ImageData fox = ImageUtil.loadTexture("png", "tests/resources/tiled/tiles.png");
		Sprite one = new Sprite(fox, 0, 0, 0);
		one.setSpriteSheetPosition(2, 2, 0, 0, 1, 1);
		Sprite two = new Sprite(fox, 32, 0, 0);
		two.setSpriteSheetPosition(2, 2, 1, 1, 1, 1);
		Sprite three = new Sprite(fox, 64, 0, 0);
		three.setSpriteSheetPosition(2, 2, 1, 0, 1, 1);
		room.addSprite(one);
		room.addSprite(two);
		room.addSprite(three);
	}
	
	public static void main(String args[])
	{
		Room r = new Room(500, 500, 10);
		SimpleSpriteSheet g = new SimpleSpriteSheet(r);
		g.startGame();
	}

}

package tests.simple;

import gui.Game;
import level.Room;
import sprites.Sprite;
import util.ImageData;
import util.ImageUtil;

public class SimpleGame extends Game 
{
	public SimpleGame(Room room) 
	{
		super(room, false);
		
		ImageData fox = ImageUtil.loadTexture("png", "tests/resources/fox.png");
		Fox one = new Fox(fox, 0, 0, 0);
		Sprite two = new Sprite(fox, 10, 10, 0);
		Sprite three = new Sprite(fox, 200, 200, 0);
		room.addSprite(one);
		room.addSprite(two);
		room.addSprite(three);
	}

	public static void main(String[] args)
	{
		Room r = new Room(500, 500, 10);
		SimpleGame g = new SimpleGame(r);
		g.startGame();
	}
}

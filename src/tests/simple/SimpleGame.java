package tests.simple;

import gui.Game;
import level.Room;
import sprites.Sprite;
import util.Texture;

public class SimpleGame extends Game 
{
	public SimpleGame(Room room) 
	{
		super(room, false);
		
		Texture fox = getImageManager().getTexture("tests/resources/fox.jpg");
		Sprite one = new Sprite(fox, 0, 0, 0);
		Sprite two = new Sprite(fox, 100, 100, 0);
		one.setHorizontalMovementSpeed(10);
		room.addSprite(one);
		room.addSprite(two);
	}

	public static void main(String args[])
	{
		Room r = new Room(500, 500, 10);
		SimpleGame g = new SimpleGame(r);
		g.startGame();
	}
}

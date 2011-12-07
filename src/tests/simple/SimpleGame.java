package tests.simple;

import gui.Game;
import level.Room;

import org.newdawn.slick.opengl.Texture;

import sprites.Sprite;
import util.ImageUtil;

public class SimpleGame extends Game 
{
	public SimpleGame(Room room) 
	{
		super(room, false);
		
		Texture fox = ImageUtil.loadTexture("png", "tests/resources/fox.png");
		Sprite one = new Sprite(fox, 0, 0, 0);
		Sprite two = new Sprite(fox, 100, 100, 0);
		Sprite three = new Sprite(fox, 200, 200, 0);
		room.addSprite(one);
		room.addSprite(two);
		room.addSprite(three);
	}

	public static void main(String args[])
	{
		Room r = new Room(500, 500, 10);
		SimpleGame g = new SimpleGame(r);
		g.startGame();
	}
}

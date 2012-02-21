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
		super(room, true);
		
		ImageData foxPNG = ImageUtil.loadTexture("/tests/resources/fox.png");
//		ImageData foxGIF = ImageUtil.loadTexture("/tests/resources/fox.gif");
		ImageData foxBMP = ImageUtil.loadTexture("/tests/resources/fox.bmp");
		ImageData foxJPG = ImageUtil.loadTexture("/tests/resources/fox.jpg");
		Sprite one = new Sprite(foxPNG, 0, 0, 0);
//		Sprite two = new Sprite(foxGIF, 100, 0, 0);
		Sprite three = new Sprite(foxBMP, 200, 0, 0);
		Sprite five = new Sprite(foxJPG, 400, 0, 0);
		room.addSprite(one);
//		room.addSprite(two);
		room.addSprite(three);
		room.addSprite(five);
	}

	public static void main(String[] args)
	{
		System.setProperty("org.lwjgl.util.Debug", "true");
		Room r = new Room(500, 500, 10);
		SimpleGame g = new SimpleGame(r);
		g.startGame();
	}
}

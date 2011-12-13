package tests.simple;

import java.util.LinkedList;

import de.matthiasmann.twl.ToggleButton;

import gui.Game;
import level.Room;
import sprites.Sprite;
import util.ImageData;
import util.ImageUtil;

public class GUITest extends Game
{
	public static void main(String args[])
	{
		Room r = new Room(500, 500, 10);
		GUITest g = new GUITest(r);
		g.startGame();
	}
	
	public GUITest(Room room)
	{
		super(room, false);
		
		ImageData fox = ImageUtil.loadTexture("png", "tests/resources/fox.png");
		Fox one = new Fox(fox, 0, 0, 0);
		Sprite two = new Sprite(fox, 10, 10, 0);
		Sprite three = new Sprite(fox, 200, 200, 0);
		room.addSprite(one);
		room.addSprite(two);
		room.addSprite(three);
		
		ToggleButton button = new ToggleButton();
		button.setTheme("armageddon");
		addWidget(button);
		button.adjustSize();
		button.setPosition(100, 100);
	}
	
	private class Fox extends Sprite
	{
		public Fox(ImageData i, int a, int b, int c)
		{
			super(i, a, b,c);
		}
		public void collisions(LinkedList<Sprite> coll)
		{
			System.out.println(coll.size());
		}
	}
}

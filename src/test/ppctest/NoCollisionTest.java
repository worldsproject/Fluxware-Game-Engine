package test.ppctest;

import java.util.LinkedList;

import level.Room;
import sprites.Sprite;
import util.ImageUtil;
import util.Point2D;

public class NoCollisionTest {

	public static void main(String args[])
	{
		LinkedList<Sprite> sprites;
		Sprite a = null;
		Sprite b = null;
		Sprite c = null;
		Sprite d = null;
		Sprite e = null;
		
		a = new Sprite(ImageUtil.getBufferedImage("/resources/purple square.png"), 60, 60, 0);
		b = new Sprite(ImageUtil.getBufferedImage("/resources/red square.png"), 60, 0, 0);
		c = new Sprite(ImageUtil.getBufferedImage("/resources/yellow square.png"), 0, 60, 0);
		d = new Sprite(ImageUtil.getBufferedImage("/resources/green square.png"), 60, 120, 0);
		e = new Sprite(ImageUtil.getBufferedImage("/resources/blue square.png"), 120, 60, 0);
		
		a.id = 1;
		b.id = 2;
		c.id = 3;
		d.id = 4;
		e.id = 5;
		
		Room room = new Room(500,500,0);
		
		a.setSight(room);
		b.setSight(room);
		c.setSight(room);
		d.setSight(room);
		e.setSight(room);
		
		sprites = room.getSprites(new Point2D(60,60,0), a);
		
		if(sprites.isEmpty())
		{
			sprites = room.getSprites(new Point2D(109,109,0), a);
			if(sprites.isEmpty())
			{
				System.out.println("Test passed.");
				System.exit(0);
			}
		}
		
		System.out.println("Test failed");
	}
}

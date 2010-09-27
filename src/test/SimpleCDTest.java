package test;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import level.TiledRoom;
import listener.bounding.TiledBoundingBox;
import sprites.Sprite;
import util.Point2D;


public class SimpleCDTest {

	public static void main(String args[]) throws MalformedURLException, IOException
	{
		LinkedList<Sprite> sprites;
		Sprite a = null;
		Sprite b = null;
		Sprite c = null;
		Sprite d = null;
		
		try {
				a = new Sprite(ImageIO.read(SimpleCDTest.class.getResource("testsprites/50x50.png")),0,0,0);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 b = new Sprite(ImageIO.read(SimpleCDTest.class.getResource("testsprites/100x50.png")),1,0,0);
		 c = new Sprite(ImageIO.read(SimpleCDTest.class.getResource("testsprites/50x100.png")),0,2,0);
		 d = new Sprite(ImageIO.read(SimpleCDTest.class.getResource("testsprites/100x100.png")),1,2,0);
		 
		 a.id = 1;
		 b.id = 2;
		 c.id = 3;
		 d.id = 4;
		 
		 a.setBounding(new TiledBoundingBox(a, 50));
		 b.setBounding(new TiledBoundingBox(b, 50));
		 c.setBounding(new TiledBoundingBox(c, 50));
		 d.setBounding(new TiledBoundingBox(d, 50));
		
		TiledRoom room = new TiledRoom(5,5,0,50);
		
		room.addSprite(a);
		room.addSprite(b);
		room.addSprite(c);
		room.addSprite(d);

		sprites = room.getSprites(new Point2D(2,0,0));

		if(!sprites.isEmpty())
		{
			System.out.println("At (2,0,0): " + sprites.getFirst().id);
		}
		else
		{
			System.out.println("At (2,0,0): No sprites");
		}

		sprites = room.getSprites(new Point2D(0,3,0));

		if(!sprites.isEmpty())
		{
			System.out.println("At (0,3,0): " + sprites.getFirst().id);
		}
		else
		{
			System.out.println("At (0,3,0): No sprites");
		}

		sprites = room.getSprites(new Point2D(2,3,0));

		if(!sprites.isEmpty())
		{
			System.out.println("At (2,3,0): " + sprites.getFirst().id);
		}
		else
		{
			System.out.println("At (2,3,0): No sprites");
		}
		
	}
}

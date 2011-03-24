package collision.test;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import gui.Game;
import level.Room;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sprites.Sprite;
import util.ImageUtil;
import util.Point2D;
import collision.Collision;

public class CollisionTest
{	
	private Room room;
	private Game game;
	@Before
	public void setUp() throws Exception
	{
		room = new Room(500, 500, 2);
		game = new Game(room, false);
	}

	@After
	public void tearDown() throws Exception
	{	
		room = null;
		game = null;
	}
	
	@Test
	public void test_No_Bounding_Box_Collision()
	{
		Sprite one = new Sprite(ImageUtil.getBufferedImage("/resources/50x50.png"), 0, 0, 0);
		Sprite two = new Sprite(ImageUtil.getBufferedImage("/resources/50x50.png"), 100, 100, 0);
		
		room.addSprite(one);
		room.addSprite(two);
		assertFalse(Collision.hasCollided(one, two, false));
		assertFalse(Collision.hasCollided(two, one, false));
	}
	
	@Test
	public void test_No_Pixel_Perfect_Collision()
	{
		Sprite one = new Sprite(ImageUtil.getBufferedImage("/resources/50x50.png"), 0, 0, 0);
		Sprite two = new Sprite(ImageUtil.getBufferedImage("/resources/50x50.png"), 100, 100, 0);
		
		room.addSprite(one);
		room.addSprite(two);
		
		assertFalse(Collision.hasCollided(one, two, true));
		assertFalse(Collision.hasCollided(two, one, true));
	}
	
	@Test
	public void test_Next_To_Each_Other_Bounding_Box_Collision()
	{
		Sprite one = new Sprite(ImageUtil.getBufferedImage("/resources/50x50.png"), 0, 0, 0);
		Sprite two = new Sprite(ImageUtil.getBufferedImage("/resources/50x50.png"), 25, 0, 0);
		
		room.addSprite(one);
		room.addSprite(two);
		
		assertTrue(Collision.hasCollided(one, two, false));
		assertTrue(Collision.hasCollided(two, one, false));
	}

	@Test
	public void test_Next_To_Each_Other_Pixel_Perfect_Collision()
	{
		Sprite one = new Sprite(ImageUtil.getBufferedImage("/resources/50x50.png"), 0, 0, 0);
		Sprite two = new Sprite(ImageUtil.getBufferedImage("/resources/50x50.png"), 25, 0, 0);
		
		room.addSprite(one);
		room.addSprite(two);
		
		assertTrue(Collision.hasCollided(one, two, true));
		assertTrue(Collision.hasCollided(two, one, true));
	}
	
	@Test
	public void test_One_Sprite_Inside_Bounding_Box_Collision()
	{
		Sprite one = new Sprite(ImageUtil.getBufferedImage("/resources/100x100.png"), 0, 0, 0);
		Sprite two = new Sprite(ImageUtil.getBufferedImage("/resources/50x50.png"), 25, 25, 0);
		
		room.addSprite(one);
		room.addSprite(two);
		
		assertTrue(Collision.hasCollided(one, two, false));
		assertTrue(Collision.hasCollided(two, one, false));
	}
	
	@Test
	public void test_One_Sprite_Inside_Pixel_Perfect_Collision()
	{
		Sprite one = new Sprite(ImageUtil.getBufferedImage("/resources/100x100.png"), 0, 0, 0);
		Sprite two = new Sprite(ImageUtil.getBufferedImage("/resources/50x50.png"), 25, 25, 0);
		
		room.addSprite(one);
		room.addSprite(two);
		
		assertTrue(Collision.hasCollided(one, two, true));
		assertTrue(Collision.hasCollided(two, one, true));
	}
	
	@Test
	public void test_Sprite_Change_Position_Bounding_Box()
	{
		Sprite one = new Sprite(ImageUtil.getBufferedImage("/resources/100x100.png"), 0, 0, 0);
		Sprite two = new Sprite(ImageUtil.getBufferedImage("/resources/50x50.png"), 100, 100, 0);
		
		room.addSprite(one);
		room.addSprite(two);
		
		assertFalse(Collision.hasCollided(one, two, false));
		assertFalse(Collision.hasCollided(two, one, false));
		
		one.setPoint(new Point2D(25, 25, 0));
		
		assertTrue(Collision.hasCollided(one, two, false));
		assertTrue(Collision.hasCollided(two, one, false));
	}
	
	@Test
	public void test_Sprite_Change_Position_Pixel_Perfect()
	{
		Sprite one = new Sprite(ImageUtil.getBufferedImage("/resources/100x100.png"), 0, 0, 0);
		Sprite two = new Sprite(ImageUtil.getBufferedImage("/resources/50x50.png"), 100, 100, 0);
		
		room.addSprite(one);
		room.addSprite(two);
		
		assertFalse(Collision.hasCollided(one, two, true));
		assertFalse(Collision.hasCollided(two, one, true));
		
		one.setPoint(new Point2D(25, 25, 0));
		
		assertTrue(Collision.hasCollided(one, two, true));
		assertTrue(Collision.hasCollided(two, one, true));
	}
}

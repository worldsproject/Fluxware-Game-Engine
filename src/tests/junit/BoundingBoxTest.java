package tests.junit;


import static org.junit.Assert.*;
import gui.Game;

import java.util.LinkedList;

import level.Room;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.newdawn.slick.opengl.Texture;

import sprites.Sprite;
import util.ImageUtil;
import util.Point2D;

public class BoundingBoxTest 
{
	private Room room;
	private SimpleGame game;
	
	private FoxSprite Fone;
	private FoxSprite Ftwo;
	
	private BoxSprite Bone;
	private BoxSprite Btwo;
	
	private BoxTSprite Tone;
	private BoxTSprite Ttwo;
	
	private int currentFrame = 0;
	private int frameLimit = 10;
	
	@Before
	public void setUp() throws Exception 
	{
		room = new Room(500, 500, 1);
		
		game = new SimpleGame(room);
		
		Fone = new FoxSprite();
		Ftwo = new FoxSprite();
		
		Bone = new BoxSprite();
		Btwo = new BoxSprite();
		
		Tone = new BoxTSprite();
		Ttwo = new BoxTSprite();
	}
	
	@After
	public void tearDown()
	{
		currentFrame = 0;
	}

	@Test
	public void NormalSquaresNotTouching()
	{
		room.addSprite(Bone);
		room.addSprite(Btwo);
		
		Bone.setLocation(new Point2D(0, 0, 0));
		Btwo.setLocation(new Point2D(400, 400, 0));

		game.startGame();
		
		assertFalse(Bone.collisionsOccured());
		assertFalse(Btwo.collisionsOccured());
	}
	
	@Test
	public void NormalSquaresHalfwayOverlaping()
	{
		room.addSprite(Bone);
		room.addSprite(Btwo);
		
		Bone.setLocation(new Point2D(0, 0, 0));
		Btwo.setLocation(new Point2D(25, 25, 0));
		
		game.startGame();
		
		assertTrue(Bone.collisionsOccured());
		assertTrue(Btwo.collisionsOccured());
	}
	
	@Test
	public void FoxSquaresNotTouching()
	{
		room.addSprite(Fone);
		room.addSprite(Ftwo);
		
		Fone.setLocation(new Point2D(0, 0 ,0));
		Ftwo.setLocation(new Point2D(400, 400, 0));
		
		game.startGame();
		
		assertFalse(Fone.collisionsOccured());
		assertFalse(Ftwo.collisionsOccured());
	}
	
	@Test
	public void FoxSquaresJustTouchingOnTransparency()
	{
		room.addSprite(Fone);
		room.addSprite(Ftwo);
		
		Fone.setLocation(new Point2D(0,0,0));
		Fone.setLocation(new Point2D(0, 63, 0));
		
		game.startGame();
		
		assertTrue(Fone.collisionsOccured());
		assertTrue(Ftwo.collisionsOccured());
	}
	
	@Test
	public void TSquaresNotTouching()
	{
		room.addSprite(Tone);
		room.addSprite(Ttwo);
		
		Tone.setLocation(new Point2D(0,0,0));
		Ttwo.setLocation(new Point2D(400, 400, 0));
		
		game.startGame();
		
		assertFalse(Tone.collisionsOccured());
		assertFalse(Ttwo.collisionsOccured());
	}
	
	@Test
	public void TSquaresTransparencyTouching()
	{
		room.addSprite(Tone);
		room.addSprite(Ttwo);
		
		Tone.setLocation(new Point2D(0,0,0));
		Ttwo.setLocation(new Point2D(80, 0, 0));
		
		game.startGame();
		
		assertTrue(Tone.collisionsOccured());
		assertTrue(Ttwo.collisionsOccured());
	}

	private class SimpleGame extends Game
	{
		public SimpleGame(Room r)
		{
			super(r, false);
		}
		
		public void drawFrame()
		{
			super.drawFrame();
			currentFrame++;
			
			if(currentFrame >= frameLimit)
			{
				isRunning = false;
			}
		}
	}
	
	private class FoxSprite extends Sprite
	{
		private boolean collided = false;
		private int texW = 2;
		private int imgW = 2;
		
		public FoxSprite()
		{
			super(null, 0, 0, 0);
			Texture tex = ImageUtil.loadTexture("png", "tests/resources/fox.png");
			texW = tex.getTextureWidth();
			imgW = tex.getImageWidth();
			setSprite(tex);
		}
		
		@Override
		public void collisions(LinkedList<Sprite> ls)
		{
			collided = true;
		}
		
		public int getTextureWidth()
		{
			return texW;
		}
		
		public int getImageWidth()
		{
			return imgW;
		}
		
		public boolean collisionsOccured()
		{
			return collided;
		}
	}
	
	private class BoxSprite extends Sprite
	{
		private boolean collided = false;
		
		public BoxSprite()
		{
			super(null, 0, 0, 0);
			Texture tex = ImageUtil.loadTexture("png", "tests/resources/box.png");
			setSprite(tex);
		}
		
		@Override
		public void collisions(LinkedList<Sprite> ls)
		{
			collided = true;
		}
		
		public boolean collisionsOccured()
		{
			return collided;
		}
	}
	
	private class BoxTSprite extends Sprite
	{
		private boolean collided = false;
		
		public BoxTSprite()
		{
			super(null, 0, 0, 0);
			Texture tex = ImageUtil.loadTexture("png", "tests/resources/botT.png");
			setSprite(tex);
		}
		
		@Override
		public void collisions(LinkedList<Sprite> ls)
		{
			collided = true;
		}
		
		public boolean collisionsOccured()
		{
			return collided;
		}
	}
}

package gui.test;

import gui.Game;

import java.awt.Dimension;

import junit.framework.Assert;
import level.Room;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestGame
{
	private Room room = null;
	
	@Before
	public void setUp() throws Exception 
	{
		room = new Room(800, 600, 1);
	}

	@After
	public void tearDown() throws Exception 
	{
		room = null;
	}

	@Test
	public void testGame() 
	{
		Game g = new Game();
		
		Assert.assertNull("Room is not Null", g.getRoom());
	}

	@Test
	public void testGameRoomBoolean() 
	{
		Game g = new Game(room, false);
		Game g2 = new Game(room, true);
		
		Assert.assertNotNull(g.getRoom());
		Assert.assertNotNull(g2.getRoom());
	}

	@Test
	public void testGameRoomBooleanDimension() 
	{
		Game g = new Game(room, false, new Dimension(400,400));
		
		Assert.assertNotNull(g.getRoom());
	}

	@Test
	public void testGameRoomBooleanDimensionString() 
	{
		Game g = new Game(room, false, new Dimension(400,400), "Test");
		
		Assert.assertNotNull(g.getRoom());
	}

	@Test
	public void testGameRoomBooleanString() 
	{
		Game g = new Game(room, false, "Test");
		
		Assert.assertNotNull(g.getRoom());
	}

}

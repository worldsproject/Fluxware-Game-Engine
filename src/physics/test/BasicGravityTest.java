package physics.test;

import gui.Game;

import java.awt.Dimension;

import physics.PhysicsRoom;
import physics.PhysicsSprite;
import util.ImageUtil;

public class BasicGravityTest
{
	private Game game = null;
	private PhysicsRoom room2 = new PhysicsRoom(600, 600, 1);
	BasicGravityTest()
	{
		PhysicsSprite sprite = new PhysicsSprite(ImageUtil.getBufferedImage("/resources/50x50.png"), room2, 200, 50, 1);
		
		PhysicsSprite s2 = new PhysicsSprite(ImageUtil.getBufferedImage("/resources/50x50.png"), room2, 200, 300, 1);
		s2.setInertia(Double.POSITIVE_INFINITY);
		
		room2.addSprite(sprite);
		room2.addSprite(s2);
		
		game = new Game(room2, false, new Dimension(600, 600), "Basic Gravity Test");
	}
	
	public static void main(String args[])
	{
		new BasicGravityTest();
	}
}

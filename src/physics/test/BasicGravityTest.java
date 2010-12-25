package physics.test;

import gui.Game;
import physics.PhysicsRoom;
import physics.PhysicsSprite;
import util.ImageUtil;

public class BasicGravityTest
{
	private Game game = null;
	private PhysicsRoom room2 = new PhysicsRoom(800, 600, 1);
	BasicGravityTest()
	{
		PhysicsSprite sprite = new PhysicsSprite(ImageUtil.getBufferedImage("/resources/50x50.png"), room2, 200, 200, 1);
		
		room2.addSprite(sprite);
		
		game = new Game(room2, false, "Basic Gravity Test");
	}
	
	public static void main(String args[])
	{
		new BasicGravityTest();
	}
}

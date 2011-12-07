package collision;

import java.util.LinkedList;

import sprites.Sprite;


public class CollisionManager 
{
	public static void checkBoundingBoxCollisions(LinkedList<Sprite> list)
	{
		for(Sprite s: list)
		{
			LinkedList<Sprite> collided = new LinkedList<Sprite>();
			
			for(Sprite sn: list)
				sn.updateBounds();
			
			for(Sprite sn: list)
			{
				if(s == sn)
					continue;
				
				if(s.getBoundingBox().withinBounds(sn.getBoundingBox()))
				{
					collided.add(sn);
				}
			}
			
			if(collided.isEmpty() == false)
				s.collisions(collided);
		}
	}
	
	public static void checkPixelPerfectCollisions(LinkedList<Sprite> list)
	{
		
	}
}

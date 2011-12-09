package collision;

import java.util.LinkedList;

import sprites.Sprite;


public class CollisionManager 
{
	public static void checkBoundingBoxCollisions(LinkedList<Sprite> list)
	{
		for(Sprite sn: list)
			sn.updateBounds();
		
		for(Sprite s: list)
		{
			LinkedList<Sprite> collided = new LinkedList<Sprite>();
			
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
		for(Sprite sn: list)
			sn.updateBounds();
		
		for(Sprite s: list)
		{
			LinkedList<Sprite> collided = new LinkedList<Sprite>();
			
			for(Sprite sn: list)
			{
				if(s == sn)
					continue;
				
				if(s.getBoundingBox().withinBounds(sn.getBoundingBox()))
				{
					double ax1 = s.getX();  //Top left X corner of sprite A
					double ay1 = s.getY(); // Top left Y corner of sprite A
					double ax2 = s.getX()+s.getWidth(); //Bottom right X corner of Sprite A.
					double ay2 = s.getY()+s.getHeight(); //Bottom right Y corner of Sprite A.
					
					double bx1 = sn.getX();
					double by1 = sn.getY();
					double bx2 = sn.getX()+sn.getWidth();
					double by2 = sn.getY()+sn.getHeight();
					
					double cx1 = 0; //Collision box top left X corner.
					double cx2 = 0; //Collision box top left Y corner.
					double cy1 = 0; //Collision box bottom right X corner.
					double cy2 = 0; //Collision box bottom right Y corner.
					
					int aw1 = 0;
					int aw2 = s.getMask().length;
					int ah1 = 0;
					int ah2 = s.getMask()[0].length;
					
					int bw1 = 0;
					int bw2 = sn.getMask().length;
					int bh1 = 0;
					int bh2 = sn.getMask()[0].length;
					
					if(ax1 < bx1)
					{
						cx1 = bx1;
						cx2 = ax2;
					}
					else
					{
						cx1 = ax1;
						cx2 = bx2;
					}
					
					if(ay1 < by1)
					{
						cy1 = by1;
						cy2 = ay2;
					}
					else
					{
						cy1 = ay1;
						cy2 = by2;
					}
					
					aw1 = (int) (cx1 - ax1);
					aw2 = (int) (aw1 + (cx2-cx1));
					ah1 = (int) (cy1 - ay1);
					ah2 = (int) (ah1 + (cy2-cy1));
					
					bw1 = (int) (cx1 - bx1);
					bw2 = (int) (bw1 + (cx2 - cx1));
					bh1 = (int) (cy1 - by1);
					bh2 = (int) (bh1 + (cy2 - cy1));
					
					int a = 0;
				}
			}
		}
	}
}

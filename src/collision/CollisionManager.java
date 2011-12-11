package collision;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import level.Room;
import sprites.Sprite;
import sprites.SpriteGroup;


public class CollisionManager 
{
	private Room room;
	private ArrayList<GroupPair> pairs = new ArrayList<GroupPair>();
	private boolean pixelPerfect = true;
	
	public CollisionManager(Room r)
	{
		room = r;
	}
	
	public void manageCollisions()
	{
		if(pairs.isEmpty())
		{
			if(pixelPerfect)
			{
				this.checkPixelPerfectCollisions(room.getSprites(), room.getSprites());
			}
			else
			{
				this.checkBoundingBoxCollisions(room.getSprites(), room.getSprites());
			}
		}
		else
		{
			if(pixelPerfect)
			{
				for(GroupPair p: pairs)
				{
					this.checkPixelPerfectCollisions(p.a.getGroup(), p.b.getGroup());
				}
			}
			else
			{
				for(GroupPair p: pairs)
				{
					this.checkBoundingBoxCollisions(p.a.getGroup(), p.b.getGroup());
				}
			}
		}
	}
	
	public void setCollisionDetectionMode(boolean pixelPerfect)
	{
		this.pixelPerfect = pixelPerfect;
	}
	
	public void addPair(SpriteGroup a, SpriteGroup b)
	{
		pairs.add(new GroupPair(a, b));
	}
	
	public void removePair(String nameA, String nameB)
	{
		Iterator<GroupPair> it = pairs.iterator();
		
		while(it.hasNext())
		{
			GroupPair temp = it.next();
			
			if(temp.nameMatches(nameA, nameB))
			{
				it.remove();
				break;
			}
		}
	}
	
	private void checkBoundingBoxCollisions(List<Sprite> listA, List<Sprite> listB)
	{
		for(Sprite sn: listA)
			sn.updateBounds();
		for(Sprite sn: listB)
			sn.updateBounds();

		for(Sprite s: listA)
		{
			LinkedList<Sprite> collided = new LinkedList<Sprite>();

			for(Sprite sn: listB)
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

	private void checkPixelPerfectCollisions(List<Sprite> listA, List<Sprite> listB)
	{
		for(Sprite sn: listA)
			sn.updateBounds();
		for(Sprite sn: listB)
			sn.updateBounds();

		for(Sprite s: listA)
		{
			LinkedList<Sprite> collided = new LinkedList<Sprite>();

			for(Sprite sn: listB)
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
					int ah1 = 0;

					int bw1 = 0;
					int bh1 = 0;

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
					ah1 = (int) (cy1 - ay1);

					bw1 = (int) (cx1 - bx1);
					bh1 = (int) (cy1 - by1);
					
					boolean[][] aMask = s.getMask();
					boolean[][] bMask = sn.getMask();

					collisionchecking: 
						for(int i = 0; i < (cx2-cx1); i++)
						{
							for(int j = 0; j < (cy2-cy1); j++)
							{
								if(aMask[aw1+i][ah1+j] == bMask[bw1+i][bh1+i])
								{
									collided.add(sn);
									break collisionchecking;
								}
							}
						}
					if(collided.isEmpty() == false)
					{
						s.collisions(collided);
					}
				}
			}
		}
	}

	private class GroupPair
	{
		private SpriteGroup a, b;
		
		public GroupPair(SpriteGroup a, SpriteGroup b)
		{
			this.a = a;
			this.b = b;
		}
		
		public boolean nameMatches(String a, String b)
		{
			if(this.a.getName().equals(a) && this.b.getName().equals(b))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
	}
}

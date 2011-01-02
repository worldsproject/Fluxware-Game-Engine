package collision;

import java.util.LinkedList;

import listener.bounding.BoundingBox;
import listener.bounding.BoundingCircle;
import sprites.Sprite;
import util.ImageUtil;
import util.Point2D;

public class Collision {
	
	/**
	 * Checks if a collision has occurred between non-transparent pixels of sprite a and b.
	 * @param a - Primary sprite
	 * @param b - Secondary sprite
	 * @return 	0: Pixel collision has occurred
	 * 			1: Pixel collision has not occurred
	 * 			2: The boudnings of both sprite a and b were not bounding boxes. No check was performed.
	 */
	public static int pixelsCollided(Sprite a, Sprite b)
	{
		if(a.getBounding() instanceof BoundingBox && b.getBounding() instanceof BoundingBox)
		{
			if(boundsCollided(a,b))
			{
				int ax1 = a.getX();
				int ay1 = a.getY();
				int ax2 = a.getX() + a.getWidth() - 1;
				int ay2 = a.getY() + a.getHeight() - 1;

				int bx1, bx2, by1, by2;
				int cx1, cy1, cx2, cy2;

				int[] amask, bmask, bitmask;

				bx1 = b.getX();
				bx2 = b.getX() + b.getWidth() - 1;
				by1 = b.getY();
				by2 = b.getY() + b.getWidth() - 1;

				cx1 = Math.max(ax1,bx1);
				cy1 = Math.max(ay1,by1);
				cx2 = Math.min(ax2,bx2);
				cy2 = Math.min(ay2, by2);

				amask = a.print().getRGB(cx1-ax1, cy1-ay1, cx2-cx1+1, cy2-cy1+1, null, 0, cx2-cx1+1);
				bmask = b.print().getRGB(cx1-bx1, cy1-by1, cx2-cx1+1, cy2-cy1+1, null, 0, cx2-cx1+1);

				bitmask = ImageUtil.getCombinedBitMask(ImageUtil.getBitMask(amask), ImageUtil.getBitMask(bmask));

				for(int i = 0; i < bitmask.length; i++)
				{
					if(bitmask[i] == 0x1)
					{
						return 0;
					}
				}
			}
			return 1;
		}
		return 2;
	}
	
	public static LinkedList<Point2D> getPixels(Sprite a, Sprite b)
	{
		if(a.getBounding() instanceof BoundingBox && b.getBounding() instanceof BoundingBox)
		{
			if(boundsCollided(a,b))
			{
				LinkedList<Point2D> points = new LinkedList<Point2D>();
				int ax1 = a.getX();
				int ay1 = a.getY();
				int ax2 = a.getX() + a.getWidth() - 1;
				int ay2 = a.getY() + a.getHeight() - 1;

				int bx1, bx2, by1, by2;
				int cx1, cy1, cx2, cy2;

				int[] amask, bmask, bitmask;

				bx1 = b.getX();
				bx2 = b.getX() + b.getWidth() - 1;
				by1 = b.getY();
				by2 = b.getY() + b.getWidth() - 1;

				cx1 = Math.max(ax1,bx1);
				cy1 = Math.max(ay1,by1);
				cx2 = Math.min(ax2,bx2) - cx1 + 1;
				cy2 = Math.min(ay2,by2) - cy1 + 1;

				amask = a.print().getRGB(cx1-ax1, cy1-ay1, cx2, cy2, null, 0, cx2);
				bmask = b.print().getRGB(cx1-bx1, cy1-by1, cx2, cy2, null, 0, cx2);

				bitmask = ImageUtil.getCombinedBitMask(ImageUtil.getBitMask(amask), ImageUtil.getBitMask(bmask));

				for(int i = 0; i < bitmask.length; i++)
				{
					if(bitmask[i] == 0x1)
					{
						points.add(new Point2D(cx1+(i%cx2), cy1+(i/cx2), a.getLayer()));
					}
				}
				return points;
			}
		}
		return null;
	}
	
	/**
	 * Either checks for a pixel perfect collision or overlapping bounds.
	 * @param a - Primary sprite
	 * @param b - Secondary sprite
	 * @param pixelPerfectCollision - True to perform pixel perfect collision check, 
	 * false to check for overlapping boundings. If pixelPerfectCollision is true, but both sprite a and b do not have 
	 * bounding boxes, the method will only check for overlapping boundings.
	 * @return - True if an overlap of boundings or pixel perfect collision has occured, false otherwise.
	 */
	public static boolean hasCollided(Sprite a, Sprite b, boolean pixelPerfectCollision)
	{
		if(pixelPerfectCollision)
		{
			int i = pixelsCollided(a,b);
			
			if(i == 0)
			{
				return true;
			}
			else if(i == 1)
			{
				return false;
			}
		}
		return boundsCollided(a,b);
	}
	
	/**
	 * Checks if the boundings of sprite a and sprite b overlap
	 * @param a - Primary sprite
	 * @param b - Secondary sprite
	 * @return - True if the boundings of sprite a and b overlap, false otherwise
	 */
	public static boolean boundsCollided(Sprite a, Sprite b)
	{
		if(a.getBounding() instanceof BoundingCircle)
		{
			if(b.getBounding() instanceof BoundingCircle)
			{
				if(((BoundingCircle)a.getBounding()).withinBounds((BoundingCircle)b.getBounding()))
				{
					return true;
				}
			}
			
			else if(b.getBounding() instanceof BoundingBox)
			{
				if(((BoundingCircle)a.getBounding()).withinBounds((BoundingBox)b.getBounding()))
				{
					return true;
				}
			}
		}
		
		else if(a.getBounding() instanceof BoundingBox)
		{
			if(b.getBounding() instanceof BoundingCircle)
			{
				if(((BoundingBox)a.getBounding()).withinBounds((BoundingCircle)b.getBounding()))
				{
					return true;
				}
			}
			else if(((BoundingBox)a.getBounding()).withinBounds((BoundingBox)b.getBounding()))
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Checks if sprite a has collided with any of the secondary sprites in the linked list.
	 * @param a - Primary sprite
	 * @param sprites - List of secondary sprites
	 * @param pixelPerfectCollision - True to perform pixel perfect collision check, 
	 * false to check for overlapping boundings. If pixelPerfectCollision is true, but both sprite a and b do not have 
	 * bounding boxes, the method will only check for overlapping boundings.
	 * @return True if an overlap of boundings or pixel perfect collision has occured, false otherwise.
	 */
	public static LinkedList<Sprite> hasCollided(Sprite a, LinkedList<Sprite> sprites, boolean pixelPerfectCollision)
	{
		LinkedList<Sprite> collisions = new LinkedList<Sprite>();
		
		for(Sprite b: sprites)
		{
			if(a == b)
			{
				continue;
			}
			
			if(hasCollided(a,b,pixelPerfectCollision))
			{
				collisions.add(b);
			}
		}
		
		return collisions;
	}
}

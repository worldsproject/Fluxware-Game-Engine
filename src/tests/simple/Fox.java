package tests.simple;

import java.util.LinkedList;

import sprites.Sprite;
import util.ImageData;

public class Fox extends Sprite
{
	public Fox(ImageData i, int a, int b, int c)
	{
		super(i, a, b,c);
	}
	public void collisions(LinkedList<Sprite> coll)
	{
		System.out.println(coll.size());
	}
}

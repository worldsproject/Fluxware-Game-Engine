package test;

import gui.Game;

import java.util.LinkedList;

import level.TiledRoom;
import sprites.Sprite;
import util.Point2D;

public class SimpleGame extends Game{

	public SimpleGame()
	{
		LinkedList<Sprite> sprites;
		Sprite a = new Sprite();
		Sprite b = new Sprite();
		Sprite c = new Sprite();
		Sprite d = new Sprite();

		TiledRoom room = new TiledRoom(5,5,0,50);

		setRoom(room);

		sprites = a.getSight().getSprites(new Point2D(1,0,0));

		if(sprites != null)
		{
			System.out.println("At (2,0,0): " + sprites.getFirst().id);
		}
		else
		{
			System.out.println("At (2,0,0): No sprites");
		}

		sprites = a.getSight().getSprites(new Point2D(0,2,0));

		if(sprites != null)
		{
			System.out.println("At (0,3,0): " + sprites.getFirst().id);
		}
		else
		{
			System.out.println("At (0,3,0): No sprites");
		}

		sprites = a.getSight().getSprites(new Point2D(1,0,0));

		if(sprites != null)
		{
			System.out.println("At (2,3,0): " + sprites.getFirst().id);
		}
		else
		{
			System.out.println("At (2,3,0): No sprites");
		}
	}
}
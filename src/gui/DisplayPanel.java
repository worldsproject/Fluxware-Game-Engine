package gui;

import gui.hud.HUD;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

import javax.swing.JPanel;

import level.Room;
import level.TiledRoom;
import sprites.Sprite;

/**
 * Default class for displaying a Room.  
 * Extends a JPanel so it is compatible for a Swing layouts.
 * 
 * Each DisplayPanel is only capable of Displaying a single room,
 * and the size of the DisplayPanel is DEPENDENT on the Room size.
 */
public class DisplayPanel extends JPanel 
{
	private Room r;
	private HUD h = null;

	private Dimension resolution;
	private Dimension size;
	private int spacing = 1;
	
	private int viewX = 0;
	private int viewY = 0;

	Graphics buf;
	BufferedImage offscreen;

	/**
	 * Creates a DisplayPanel that takes in a Room to be displayed.
	 * 
	 * @param r - The default room to be displayed.
	 */
	public DisplayPanel(Room r, Dimension res)
	{
		//Sets so the the Display Driver only  **Feb 1, 2010  WTF does this comment mean?
		if(r instanceof TiledRoom)
		{
			spacing = ((TiledRoom)r).getCellSize();
		}

		resolution = res;
		
		//Setting the size of the DisplayPanel.
		int width = r.getWidth();
		int length = r.getHeight();

		size = new Dimension((spacing * width), (spacing * length));

		this.setMinimumSize(res);
		this.setPreferredSize(res);
		this.setMaximumSize(res);

		//Creating a separate Double Buffering System.
		offscreen = new BufferedImage(res.width, res.height, BufferedImage.TYPE_4BYTE_ABGR);
		buf = offscreen.getGraphics();

		this.setDoubleBuffered(true);

		//Setting the room up.
		this.r = r;
	}
	
	public void changeRoom(Room r)
	{
		this.r = r;
	}

	public void addHUD(HUD h)
	{
		this.h = h;
		h.setWidth(r.getWidth());
		h.setHeight(h.getHeight());
	}

	public void updateSprites(long time, long totalTime)
	{
		LinkedList<Sprite> l = r.getSprites();
		for(Sprite s : l)
		{
			s.update(time, totalTime);
		}
	}
	
	public void scrollViewscreen(int x, int y)
	{
		viewX += x;
		viewY += y;
	}
	
	public void scrollRelativeTo(Sprite s)
	{
		if(s == null)
		{
			return;
		}
		
		//First we need to calculate the center.
		int width = s.getWidth();
		int height = s.getHeight();
		int vpWidth = (int) resolution.getWidth();
		int vpHeight = (int) resolution.getHeight();
		
		int centerHeight = (vpHeight >> 1) - height;
		int centerWidth = (vpWidth >> 1) - width;
		
		viewY = s.getY() - centerHeight;
		viewX = s.getX() - centerWidth;
	}

	/**
	 * Override for the default <i>paintComponent</i>, not to be used except in special debug cases.
	 */
	@Override
	public void paintComponent(Graphics g) 
	{
		buf = offscreen.getGraphics();

		LinkedList<Sprite> l = r.getSprites();

		buf.setColor(Color.GRAY);  //General background of the screen.

		buf.fillRect(0, 0, (int)size.getWidth(), (int)size.getHeight());

		buf.setColor(Color.BLACK);
		buf.fillRect(viewX * -1, viewY * -1, r.getWidth(), r.getHeight());

		buf.setColor(Color.WHITE);

		for(Sprite temp: l)
		{	
//			if(r.getWidth() >= resolution.getWidth() && r.getHeight() >= resolution.getHeight())
//			{
				buf.drawImage(temp.print(), (temp.getX() * spacing) - viewX, (temp.getY() * spacing) - viewY, null);
//			}
//			else if(r.getWidth() > resolution.getWidth())
//			{
//				buf.drawImage(temp.print(), (temp.getX() * spacing) - viewX, (temp.getY() * spacing) - viewY + (r.getHeight() >> 1), null);
//			}
//			else if(r.getHeight() > resolution.getHeight())
//			{
//				buf.drawImage(temp.print(), (temp.getX() * spacing) - viewX + (r.getWidth() >> 1), (temp.getY() * spacing) - viewY, null);
//			}
//			else
//			{
//				buf.drawImage(temp.print(), (temp.getX() * spacing) - viewX + (r.getWidth() >> 1), (temp.getY() * spacing) - viewY + (r.getHeight() >> 1), null);
//			}	
		}

		if(h != null && h.isVisible()) //This deals with the display of the HUD
		{
			HashMap<Integer, Sprite> hash = h.getSprites();
			Set<Integer> set = h.getSprites().keySet();
			
			for(Integer i : set)
			{
				Sprite s = hash.get(i);
				buf.drawImage(s.print(), s.getX(), s.getY(), null);
			}
		}

		g.drawImage(offscreen, 0, 0, null);
	}
}

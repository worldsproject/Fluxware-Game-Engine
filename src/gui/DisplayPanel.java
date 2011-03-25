package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

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
@SuppressWarnings("serial")
public class DisplayPanel extends JPanel 
{
	private Room r; //The displayed room.

	private Dimension resolution; //The resolution the window is showing.
	private Dimension size; //The pixel size of the Room.
	private int spacing = 1; //Each cell.

	//The X and Y coordinates of the ViewPort.
	private int viewX = 0;
	private int viewY = 0;

	//For DoubleBuffering.
	Graphics buf;
	BufferedImage offscreen;

	/**
	 * Creates a DisplayPanel that takes in a Room to be displayed.
	 * 
	 * @param r - The default room to be displayed.
	 */
	public DisplayPanel(Room r, Dimension res)
	{
		//Sets the space to the TiledRoom cellsize.
		if(r instanceof TiledRoom)
		{
			spacing = ((TiledRoom)r).getCellSize();
		}

		resolution = res; //How big the window should be.

		//Setting the size of the DisplayPanel.
		int width = r.getWidth();
		int length = r.getHeight();

		size = new Dimension((spacing * width), (spacing * length)); //Pixel size of the room.

		//Forces the JPanel to be the correct size.
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

	/**
	 * Changes the current displayed Room to the given Room.
	 * @param r - The new Room to be displayed.
	 */
	public void changeRoom(Room r)
	{
		this.r = r; //Changes the room.
	}

	/**
	 * This calls every Sprites update method with the total time since the game started, and time since the last frame refresh.
	 * @param time
	 * @param totalTime
	 */
	public void updateSprites(long time, long totalTime)
	{
		LinkedList<Sprite> l = r.getSprites();  //Iterate through each Sprite and call its update method.
		for(Sprite s : l)
		{
			s.update(time, totalTime);
		}
	}

	/**
	 * Scroll the ViewScreen by the amount given. This is relative to it's current location.
	 * Thus, if the current Viewport is at (100,100) and this method is called with (10, 5) the
	 * Viewports X and Y coordinates will be (110, 105).
	 * @param x - Amount the Viewports X coordinate will change by.
	 * @param y - Amount the Viewports Y coordinate will change by.
	 */
	public void scrollViewscreen(int x, int y)
	{
		viewX += x;
		viewY += y;
	}

	/**
	 * This moves the Viewscreen relative to a Sprite. The Viewscreen will be centered on
	 * the supplied Sprite.
	 * @param s - The Sprite the Viewscreen is to be centered upon.
	 */
	public void scrollRelativeTo(Sprite s)
	{
		if(s == null) //If the Sprite is null, don't bother changing anything.
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

		//Then we set the viewports X and Y to the calculated point.
		viewY = s.getY() - centerHeight;
		viewX = s.getX() - centerWidth;
	}

	/**
	 * Override for the default <i>paintComponent</i>, not to be used except in special debug cases.
	 */
	@Override
	public void paintComponent(Graphics g) 
	{
		buf = offscreen.getGraphics(); //Get the offscreen graphics.

		LinkedList<Sprite> l = r.getSprites(); //Get the list of Sprites.

		buf.setColor(Color.GRAY);  //General background of the screen.

		buf.fillRect(0, 0, (int)size.getWidth(), (int)size.getHeight()); //Fill everything with Gray.

		buf.setColor(Color.BLACK); //Fill the viewscreen with Black.
		buf.fillRect(viewX * -1, viewY * -1, r.getWidth(), r.getHeight());

		buf.setColor(Color.WHITE);

		for(Sprite temp: l) //Iterate through each sprite and draw it.
		{	
			buf.drawImage(temp.print(), (temp.getX() * spacing) - viewX, (temp.getY() * spacing) - viewY, null);	
		}

		g.drawImage(offscreen, 0, 0, null);
	}
}

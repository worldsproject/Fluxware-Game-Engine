package gui;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Menu;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import level.Room;
import sprites.Sprite;
import error.CrashReport;

/**
 * This is the main class for the Fluxware Game Engine.  This class handles and maintains the Windowing System.
 * @author Fluxware
 *
 */
public class Game
{
	//Sets the default size of the window that the game is displayed in.
	private Dimension size = new Dimension(800, 600);
	
	//Sets whether the window will display in fullscreen. Default false.
	private boolean fullscreen = false;
	
	//The current room that the game is displaying.
	private Room room = null;

	/**
	 * Creates a Game with the given Room. Uses defaults of 800x600 and not in fullscreen.
	 * @param room
	 */
	public Game(Room room)
	{
		this.room = room;
	}
	
	/**
	 * Creates a new Game based on the given room and if it should be fullscreen or not.
	 * @param room - The room to be displayed.
	 * @param fullscreen - True to enable fullscreen. 
	 */
	public Game(Room room, boolean fullscreen)
	{	
		this.room = room;
		this.fullscreen = fullscreen;
	}
	
	/**
	 * Creates a new Game based upon the Room given to it.
	 * @param room - The room the Game will start displaying.
	 * @param fullscreen - true if the game is fullscreen.
	 * @param size - The resolution of the game.
	 */
	public Game(Room room, boolean fullscreen, Dimension size)
	{

	}
	
	/**
	 * Creates a new Game based upon the Room given to it.
	 * @param room - The Room the Game will start displaying.
	 * @param fullscreen - true if the game is to be in fullscreen.
	 * @param size - The resolution of the game.
	 * @param title - The title of the window, if not fullscreen.
	 */
	public Game(Room room, boolean fullscreen, Dimension size, String title)
	{

	}

	/**
	 * Creates a new Game based upon a given Room.
	 * @param room - The Room the Game will start displaying.
	 * @param fullscreen - true if the Game is to show as fullscreen.
	 * @param title - The text of the window title, only shows if the game is not in fullscreen.
	 */
	public Game(Room room, boolean fullscreen, String title)
	{

	}
	
	/*
	 * The standard default method calls that are needed for every constructor.
	 */
	private void construct(boolean fullscreen)
	{
		
	}

	/**
	 * This creates all of the Swing components, sets the JFrame properties,
	 * and does other config items.  This should be called before calling Game.run().
	 * Sets up and creates the GUI.
	 */
	protected void setupGUI()
	{

	}

	/**
	 * Sets a new Room as the default, generally used when changing rooms or levels.
	 * @param room
	 */
	public void setRoom(Room room)
	{
		//TODO
	}
	
	/**
	 * Returns the current Room
	 */
	public Room getRoom()
	{
		return null; //TODO
	}
	
	/**
	 * Updates all of the Sprites.
	 * @param totalTime - The amount of time since the start of the Game.
	 * @param elapsedTime - The amount of time since the last Frame update.
	 */
	public void update(long totalTime, long elapsedTime)
	{
		//TODO
	}
	
	/**
	 * Moves the viewscreen by X and Y amount. This is relative to it's current position.
	 * @param x - The amount of X movement.
	 * @param y - The amount of Y movement.
	 */
	public void scrollViewscreen(int x, int y)
	{
		//TODO
	}
	
	/**
	 * Centers the Viewscreen to the given Sprite.
	 * @param s - The Sprite that the Viewscreen will center upon.
	 */
	public void scrollViewscreenRelativeTo(Sprite s)
	{
		//TODO
	}
}

package gui;

import java.awt.Dimension;

import level.Room;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.*;

import sprites.Sprite;

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

	//The title of the window. Defaults to 'Fluxware Game Engine'
	private String title = "Fluxware Game Engine";
	
	//Each of the differing managers.
	//SoundManager
	//CollisionManager
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
		this.room = room;
		this.fullscreen = fullscreen;
		this.size = size;
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
		this.room = room;
		this.fullscreen = fullscreen;
		this.size = size;
		this.title = title;
	}
	
	/*
	 * The standard default method calls that are needed for every constructor.
	 */
	private void construct()
	{
		try
		{
			setDisplayMode();
			Display.setTitle(title);
			Display.setFullscreen(fullscreen);
			Display.create();
			
			Mouse.setGrabbed(true);
			
			glEnable(GL_TEXTURE_2D); //Enable textures in 2D mode.
			glDisable(GL_DEPTH_TEST); //Disable depth test as we are only doing 2D items.
			glMatrixMode(GL_PROJECTION); //TODO unknown function.
			glLoadIdentity(); //TODO unknown function.
			
			glOrtho(0, size.width, size.height, 0, -1, 1);
			glMatrixMode(GL_MODELVIEW);
			glLoadIdentity();
			glViewport(0, 0, size.width, size.height);
		}
		catch(LWJGLException e)
		{
			System.err.println("Game Exiting - Error in initialization: ");
			e.printStackTrace();
		}
	}
	
	//Sets the display mode for fullscreen if possible.
	private void setDisplayMode()
	{
		try
		{
			DisplayMode[] dm = org.lwjgl.util.Display.getAvailableDisplayModes(size.width, size.height, -1, -1, -1, -1, 60, 60);
			
			org.lwjgl.util.Display.setDisplayMode(dm, new String[] {
					"width="+size.width,
					"height="+size.height,
					"freq=60",
					"bpp="+org.lwjgl.opengl.Display.getDisplayMode().getBitsPerPixel()
					});
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.err.println("Unable to enter into fullscreen, continuing in windowed mode.");
		}
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

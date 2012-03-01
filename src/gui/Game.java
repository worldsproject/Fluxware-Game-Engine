package gui;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glViewport;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import level.Room;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import sprites.Sprite;
import collision.CollisionManager;
import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import de.matthiasmann.twl.theme.ThemeManager;

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
	private boolean fullscreen;

	//The current room that the game is displaying.
	private Room room = null;

	//The title of the window. Defaults to 'Fluxware Game Engine'
	private String title = "Fluxware Game Engine";

	private static long ticksPerSecond = Sys.getTimerResolution();
	private long delta = 0;
	private long lastLoopTime = 0;
	private int framesPerSecond = 60;
	
	protected boolean isRunning = true;

	//Each of the differing managers.
	//SoundManager
	//CollisionManager
	CollisionManager collision;
	
	//GUI manager
	LWJGLRenderer render;
	GUI gui;
	Widget root = new Widget();
	String theme_location = "/tests/resources/gui/gameui.xml";
	/**
	 * Creates a Game with the given Room. Uses defaults of 800x600 and not in fullscreen.
	 * @param room
	 */
	public Game(Room room)
	{
		this(room, false, null, null);
	}

	/**
	 * Creates a new Game based on the given room and if it should be fullscreen or not.
	 * @param room - The room to be displayed.
	 * @param fullscreen - True to enable fullscreen. 
	 */
	public Game(Room room, boolean fullscreen)
	{	
		this(room, fullscreen, null, null);
	}

	/**
	 * Creates a new Game based upon the Room given to it.
	 * @param room - The room the Game will start displaying.
	 * @param fullscreen - true if the game is fullscreen.
	 * @param size - The resolution of the game.
	 */
	public Game(Room room, boolean fullscreen, Dimension size)
	{
		this(room, fullscreen, size, null);
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
		if(room != null)
			this.room = room;
		
		this.fullscreen = fullscreen;
		
		if(size != null)
			this.size = size;
		
		if(title != null)
			this.title = title;
		
		collision = new CollisionManager(room);
		construct();
	}


	/**
	 * Sets a new Room as the default, generally used when changing rooms or levels.
	 * @param room
	 */
	public void setRoom(Room room)
	{
		this.room = room;
		collision.updateRoom(room);
	}

	/**
	 * Returns the current Room
	 */
	public Room getRoom()
	{
		return room;
	}

	public long getDelta()
	{
		return delta;
	}

	public long getTime()
	{
		return (Sys.getTime() * 1000) / ticksPerSecond;
	}
	
	public void setFramesPerSecond(int fps)
	{
		framesPerSecond = fps;
	}
	
	public int getFramesPerSecond()
	{
		return framesPerSecond;
	}
	
	public void addWidget(Widget w)
	{
		root.add(w);
		w.adjustSize();
	}
	
	public void setTheme(String theme_location)
	{
		if(theme_location == null)
			theme_location = "/tests/resources/gui/gameui.xml";
		
		ThemeManager theme = null;
		try
		{
			theme = ThemeManager.createThemeManager(Game.class.getResource(this.theme_location), render);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		root.setTheme("gameuidemo");
		gui.applyTheme(theme);
	}
	
	public CollisionManager getCollisionManager()
	{
		return collision;
	}

	private void gameLoop()
	{
		while(isRunning)
		{
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			glMatrixMode(GL_MODELVIEW);
			glLoadIdentity();

			if(room != null)
				drawFrame();

			gui.update();
			Display.update();
			
			if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
				isRunning = false;
		}
		gui.destroy();
		Display.destroy();
	}

	public void drawFrame()
	{
		Display.sync(framesPerSecond);
		
		delta = getTime() - lastLoopTime;
		lastLoopTime = getTime();
		
		collision.manageCollisions();

		Iterator<Sprite> it = room.getSprites().iterator();
		
		while(it.hasNext())
		{
			Sprite s = it.next();
			if(s.isGarbage())
			{
				it.remove();
				continue;
			}
			s.move(delta);
			s.logic();
			s.draw();
		}
	}
	
	public void startGame()
	{
		gameLoop();
	}

	/*
	 * The standard default method calls that are needed for every constructor.
	 */
	private void construct()
	{
		try
		{
			lastLoopTime = getTime();
			Display.setDisplayMode(new DisplayMode(size.width, size.height));
			Display.setTitle(title);
			Display.setFullscreen(fullscreen);
			Display.create();
			
			render = new LWJGLRenderer(); 
			gui = new GUI(root, render);
			
			setTheme(null);

//			Mouse.setGrabbed(true);

			glEnable(GL_TEXTURE_2D); //Enable textures in 2D mode.
			glDisable(GL_DEPTH_TEST); //Disable depth test as we are only doing 2D items.
			glMatrixMode(GL_PROJECTION);
			glLoadIdentity();

			glOrtho(0, size.width, size.height, 0, -100, 100);
//			glMatrixMode(GL_MODELVIEW);
//			glLoadIdentity();
			glViewport(0, 0, size.width, size.height);
		}
		catch(LWJGLException e)
		{
			System.err.println("Game Exiting - Error in initialization: ");
			e.printStackTrace();
		}
	}
}

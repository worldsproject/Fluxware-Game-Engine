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
public class Game extends JFrame implements KeyListener, MouseListener
{
	private GraphicsDevice device = null;
	private Room room;
	protected DisplayPanel dp;
	private RunThread rt;

	private boolean fullscreen = false;

	protected JPanel root = null;

	private boolean showMenu = false;
	private Menu menu = null;
	
	private Dimension resolution = new Dimension(1024, 768);

	public Game(){}

	/**
	 * Creates a new Game based on the given room and if it should be fullscreen or not.
	 * @param room - The room to be displayed.
	 * @param fullscreen - True to enable fullscreen. 
	 */
	public Game(Room room, boolean fullscreen)
	{
		super("Fluxware Game Engine");
		
		this.room = room;
		
		construct(fullscreen);
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
		
		if(size != null)
		{
			resolution = size;
		}
		
		construct(fullscreen);
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
		
		if(title != null)
		{
			this.setTitle(title);
		}
		else
		{
			this.setTitle("Fluxware Game Engine");
		}
		
		if(size != null)
		{
			resolution = size;
		}
		
		construct(fullscreen);
	}

	/**
	 * Creates a new Game based upon a given Room.
	 * @param room - The Room the Game will start displaying.
	 * @param fullscreen - true if the Game is to show as fullscreen.
	 * @param title - The text of the window title, only shows if the game is not in fullscreen.
	 */
	public Game(Room room, boolean fullscreen, String title)
	{
		this.room = room;
		
		if(title != null)
			this.setTitle(title);
		else
			this.setTitle("Fluxware Game Engine");

		construct(fullscreen);
	}
	
	/*
	 * The standard default method calls that are needed for every constructor.
	 */
	private void construct(boolean fullscreen)
	{
		this.fullscreen = fullscreen;
		
		this.addKeyListener(this);
		this.addMouseListener(this);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		setRoom(room);
		setupGUI();
		run();
	}

	/**
	 * This creates all of the Swing components, sets the JFrame properties,
	 * and does other config items.  This should be called before calling Game.run().
	 * Sets up and creates the GUI.
	 */
	protected void setupGUI()
	{
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //Makes sure it fully exits when you click the 'X'

		setupDP(room);  //Sets up the Display Panel with the first room.

		root = (JPanel)this.getContentPane();  //Gets the content pane, and sets it up so that it is always centered.
		root.setLayout(new GridBagLayout());

		root.add(dp, new GridBagConstraints());

		//This is here to determine the fullscreen status
		if(fullscreen == false)//Keep it as a window.
		{
			this.setSize(resolution);

			this.setLocationRelativeTo(null);
		}
		else //Fullscreen it!
		{
			this.setUndecorated(true);
			this.setResizable(false);

			GraphicsDevice[] gda = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
			device = gda[0];
			device.setFullScreenWindow(this);
		}

		//Makes it visible and adds a keylistener.
		this.setVisible(true);
		this.addKeyListener(room);
		
		for(KeyListener k: room.getKeyListeners())
		{
			this.addKeyListener(k);
		}
	}

	/*
	 * Changes the room.
	 */
	protected void setupDP(Room room)
	{
		if(dp != null)
		{
			dp.changeRoom(room);
		}
		else
		{
			dp = new DisplayPanel(room, resolution);
		}
	}

	/*
	 * Creates a new thread and runs it.  This thread handles the updates.
	 */
	protected void run()
	{
		rt = new RunThread(this);
		rt.setPriority(Thread.MAX_PRIORITY);
		rt.start();
	}

	/**
	 * Sets a new Room as the default, generally used when changing rooms or levels.
	 * @param room
	 */
	public void setRoom(Room room)
	{
		this.room = null;

		this.room = room;
		setupDP(room);
	}
	
	/**
	 * Returns the current Room
	 */
	public Room getRoom()
	{
		return room;
	}
	
	/**
	 * Updates all of the Sprites.
	 * @param totalTime - The amount of time since the start of the Game.
	 * @param elapsedTime - The amount of time since the last Frame update.
	 */
	public void update(long totalTime, long elapsedTime)
	{

	}
	
	/**
	 * Moves the viewscreen by X and Y amount. This is relative to it's current position.
	 * @param x - The amount of X movement.
	 * @param y - The amount of Y movement.
	 */
	public void scrollViewscreen(int x, int y)
	{
		dp.scrollViewscreen(x, y);
	}
	
	/**
	 * Centers the Viewscreen to the given Sprite.
	 * @param s - The Sprite that the Viewscreen will center upon.
	 */
	public void scrollViewscreenRelativeTo(Sprite s)
	{
		dp.scrollRelativeTo(s);
	}

	/*
	 * This private class creates a new thread and handles the screen refreshes.
	 */
	private class RunThread extends Thread 
	{
		private long time = System.currentTimeMillis();
		private Game g = null;
		RunThread(Game g)
		{
			this.g = g;
		}


		public void run()
		{	
			try
			{
				long currentTime = System.currentTimeMillis();
				long totalTime = 0;
				while(true)
				{
					long lastTime = currentTime;
					currentTime = System.currentTimeMillis();

					time = currentTime - lastTime;
					totalTime += time;

					root.repaint();

					dp.updateSprites(time, totalTime);
					g.update(time, totalTime);
					dp.repaint();

					try 
					{
						long sleepTime = System.currentTimeMillis() - currentTime;

						Thread.sleep(Math.max(17 - sleepTime, 0));
					}
					catch (InterruptedException e) 
					{
						e.printStackTrace();
					}
				}
			}
			catch(Exception e)
			{
				new CrashReport(e);
			}

		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub LACE

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub LACE

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub LACE

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub LACE

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub LACE

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub LACE

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub LACE 

	}
}

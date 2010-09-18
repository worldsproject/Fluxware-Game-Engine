package gui;

import error.CrashReport;
import gui.hud.HUD;

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

/**
 * This is the main class for the Fluxware Game Engine.  This class handles and maintains the Windowing System.
 * @author Fluxware
 *
 */
public class Game extends JFrame implements KeyListener, MouseListener
{
	/**
	 * Debug is disabled by default.
	 */
	public static boolean DEBUG = false;

	private GraphicsDevice device = null;
	private Room room;
	protected DisplayPanel dp;
	private RunThread rt;

	private boolean fullscreen = false;

	protected JPanel root = null;

	private boolean showMenu = false;
	private Menu menu = null;

	private HUD hud = null;
	private boolean showHUD = true;
	
	private Dimension resolution = new Dimension(1024, 768);

	public Game(){}

	public Game(Room room, boolean fullscreen)
	{
		super("Fluxware Game Engine");
		
		construct(fullscreen);
	}
	
	public Game(Room room, boolean fullscreen, Dimension size)
	{
		if(size != null)
		{
			resolution = size;
		}
		
		construct(fullscreen);
	}
	
	public Game(Room room, boolean fullscreen, Dimension size, String title)
	{
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

	public Game(Room room, boolean fullscreen, String title)
	{
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
	 * Sets the HUG to the given.
	 * @param h - The HUD to be displayed.
	 */
	public void setHUD(HUD h)
	{
		this.hud = h;
		dp.addHUD(h);
	}

	/**
	 * Shows the current HUD, if one is available.
	 * @param show - Shows the HUD if true.
	 */
	public void showHUD(boolean show)
	{
		showHUD = show;
	}

	/**
	 * Returns the current HUD.
	 * @return The current HUD.
	 */
	public HUD getHUD()
	{
		return hud;
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

	public static void enableDebug(boolean value)
	{
		DEBUG = value;
	}

	public static boolean isDebugEnabled()
	{
		return DEBUG;
	}

	public void update(long totalTime, long elapsedTime)
	{

	}
	
	public void scrollViewscreen(int x, int y)
	{
		dp.scrollViewscreen(x, y);
	}
	
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

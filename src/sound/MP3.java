package sound;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javazoom.jl.player.Player;

public class MP3 extends Sound
{
	private String filename;
	private Player player; 
	private Thread ted;
	private boolean paused = false;

	public MP3(String filename) 
	{
		this.filename = filename;
	}

	public void stop()
	{
		if(player != null)
		{
			player.close();
		}
	}

	// play the MP3 file to the sound card
	public void play() 
	{
		if(paused)
		{
			ted.notify();
			paused = false;
			return;
		}

		try 
		{
			FileInputStream fis     = new FileInputStream(filename);
			BufferedInputStream bis = new BufferedInputStream(fis);
			player = new Player(bis);
		}
		catch (Exception e) 
		{
			e.printStackTrace(); //TODO Error handleing LACE
		}

		// run in new thread to play in background
		ted = new Thread() 
		{
			public void run() 
			{
				try
				{
					player.play(); 
				}
				catch(Exception e)
				{
					e.printStackTrace(); //TODO Error handleing LACE
				}
			}
		};

		ted.start();
	}
}
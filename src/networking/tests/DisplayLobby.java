package networking.tests;

import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JFrame;

import networking.Lobby;

public class DisplayLobby extends JFrame 
{
	public DisplayLobby()
	{
		try 
		{
			this.add(new Lobby(new URL("http://localhost:8080/")));
		}
		catch (MalformedURLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.pack();
		this.setVisible(true);
	}
	
	public static void main(String args[])
	{
		new DisplayLobby();
	}
}

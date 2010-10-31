package networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Lobby extends JPanel 
{
	private JButton newLobby = new JButton("Create Lobby");
	private JButton joinLobby = new JButton("Join Lobby");
	
	private String lobbyID = null;
	private String playerID = null;
	
	private ArrayList<String> messages = new ArrayList<String>(30);
	
	private URL server = null;
	
	public Lobby(URL serverLocation)
	{
		
	}
	
	/*
	 * The builds the interface for when the user is not in a lobby.
	 */
	private void notInLobby()
	{
		
	}
	
	/*
	 * This builds the interface for when the user is in a lobby.
	 */
	private void inLobby()
	{
		
	}
	
	private void createLobby(String name, String game, String playerID)
	{
		URL to = null;
		
		try 
		{
			to = new URL(server.getPath() + "/1");
		}
		catch (MalformedURLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String[][] data = new String[3][2];
		data[0][0] = "name";
		data[1][0] = "game";
		data[2][0] = "playerid";
		
		data[0][1] = name;
		data[1][1] = game;
		data[2][1] = playerID;
		
		LinkedList<String> returned = sentPOST(to, data);
		
		lobbyID = returned.getFirst();
		inLobby();
	}
	
	private LinkedList<String> sentPOST(URL to, String[][] data)
	{
		StringBuffer buf = new StringBuffer();
		
		for(int i = 0; i < data.length; i++)
		{
			try 
			{
				buf.append(URLEncoder.encode(data[i][0], "UTF-8") + "=" + URLEncoder.encode(data[i][1], "UTF-8"));
			}catch (UnsupportedEncodingException e){}
		}
		
		LinkedList<String> rv = new LinkedList<String>();
		
		try 
		{
			URLConnection conn = to.openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(buf.toString());
			wr.flush();
			
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			
			String line;
	        while ((line = rd.readLine()) != null) 
	        {
	        	rv.add(line);
	        }
	        
	        wr.close();
	        rd.close();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rv;
	}
}

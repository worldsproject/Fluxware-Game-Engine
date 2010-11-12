package networking;

import java.awt.BorderLayout;
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

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Lobby extends JPanel 
{
	//Out of Lobby GUI elements.
	private JButton newLobby = new JButton("Create Lobby");
	private JButton joinLobby = new JButton("Join Lobby");
	private JList lobbies = null;
	private JLabel info = new JLabel("Lobby Information.");
	private DefaultListModel model = new DefaultListModel();
	
	//General Elements.
	private String lobbyID = null;
	private String playerID = null;
	
	private ArrayList<String> messages = new ArrayList<String>(30);
	
	private URL server = null;
	
	public Lobby(URL serverLocation)
	{
		
		lobbies = new JList(model);
		notInLobby();
	}
	
	/*
	 * The builds the interface for when the user is not in a lobby.
	 */
	private void notInLobby()
	{
		this.removeAll();
		
		this.setLayout(new BorderLayout());

		for(int i = 0; i < 50; i++)
			model.addElement("Lobby numer " + i);
		
		
		JScrollPane sp = new JScrollPane(lobbies);
		this.add(sp, BorderLayout.CENTER);
		
		JPanel bottom = new JPanel(new BorderLayout());
		bottom.add(info, BorderLayout.WEST);
		bottom.add(newLobby, BorderLayout.CENTER);
		bottom.add(joinLobby, BorderLayout.EAST);
		
		this.add(bottom, BorderLayout.SOUTH);
	}
	
	/*
	 * This builds the interface for when the user is in a lobby.
	 */
	private void inLobby()
	{
		
	}
	
	private void createLobby(String name, String game, String playerID)
	{
		URL to = createURL("1");
		
		String[][] data = new String[3][2];
		data[0][0] = "name";
		data[1][0] = "game";
		data[2][0] = "playerid";
		
		data[0][1] = name;
		data[1][1] = game;
		data[2][1] = playerID;
		
		LinkedList<String> returned = sendPOST(to, data);
		
		lobbyID = returned.getFirst();
		inLobby();
	}
	
	private void joinLobby(String lobbyid, String playerid)
	{
		URL to = createURL("2");
		
		String[][] data = new String[2][2];
		data[0][0] = "lobbyid";
		data[1][0] = "playerid";
		
		data[0][1] = lobbyid;
		data[1][1] = playerid;
		
		LinkedList<String> returned = sendPOST(to, data);
		
		lobbyID = returned.getFirst();
		inLobby();
	}
	
	private void leaveLobby(String lobbyid, String playerid)
	{
		URL to = createURL("3");
		
		String[][] data = new String[2][2];
		
		data[0][0] = "lobbyid";
		data[1][0] = "playerid";
		
		data[0][1] = lobbyid;
		data[1][1] = playerid;
		
		sendPOST(to, data);
		inLobby();
	}
	
	private LinkedList<String> sendPOST(URL to, String[][] data)
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
	
	/*
	 * Appends the String append to whatever the server path is.
	 */
	private URL createURL(String append)
	{
		try
		{
			return new URL(server.getPath() + append);
		}
		catch(MalformedURLException e)
		{
			e.printStackTrace();
			return null;
		}
	}
}

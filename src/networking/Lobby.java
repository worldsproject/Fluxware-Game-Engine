package networking;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Lobby extends JPanel implements ActionListener
{
	//Out of Lobby GUI elements.
	private JButton newLobby = new JButton("Create Lobby");
	private JButton joinLobby = new JButton("Join Lobby");
	private JList lobbies = null;
	private JLabel info = new JLabel("Lobby Information.");
	private DefaultListModel outOfLobbyModel = new DefaultListModel();
	
	//In lobby GUI elements.
	private JButton ready = new JButton("Ready");
	private JButton leave = new JButton("Leave");
	
	private JTextField input = new JTextField("Enter Message");
	private JTextArea chat = new JTextArea("Welcome to the Lobby!");
	private JList currentPlayers = null;
	private DefaultListModel inLobbyModel = new DefaultListModel();
	
	//General Elements.
	private String lobbyID = null;
	private String playerID = null;
	
	private ArrayList<String> messages = new ArrayList<String>(30);
	
	private LinkedList<String> players = new LinkedList<String>();
	private LinkedList<String> lobbys = new LinkedList<String>();
	
	private URL server = null;
	
	public Lobby(URL serverLocation, String game)
	{
		server = serverLocation;
		
		lobbies = new JList(outOfLobbyModel);
		currentPlayers = new JList(inLobbyModel);
		
//		newPlayer(game);
		
//		notInLobby();
		inLobby();
	}
	
	/*
	 * The builds the interface for when the user is not in a lobby.
	 */
	private void notInLobby()
	{
		this.removeAll();
		
		this.setLayout(new BorderLayout());

		for(int i = 0; i < 10; i++)
			outOfLobbyModel.addElement("Lobby number " + i);
		
		
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
		this.removeAll();
		
		this.setLayout(new BorderLayout());
		
		for(int i = 0; i < 10; i++)
			inLobbyModel.addElement("Player " + i);
		
		JScrollPane sp = new JScrollPane(currentPlayers);
		
		this.add(chat, BorderLayout.CENTER);
		this.add(sp, BorderLayout.EAST);
		
		JPanel panel = new JPanel(new FlowLayout());
		panel.add(input);
		panel.add(ready);
		panel.add(leave);
		
		this.add(panel, BorderLayout.SOUTH);
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
	
	private void startGame(String lobbyid)
	{
		URL to = createURL("4");
		
		String[][] data = new String[1][2];
		
		data[0][0] = "lobbyid";
		data[0][1] = lobbyid;
		
		players = sendPOST(to, data);
		notInLobby();
	}
	
	public void listLobbies(String game)
	{
		URL to = createURL("5");
		
		String[][] data = new String[1][2];
		
		data[0][0] = "game";
		data[0][1] = game;
		
		lobbys = sendPOST(to, data);
	}
	
	public void readyToPlay(String lobbyid, String playerid)
	{
		URL to = createURL("6");
		
		String[][] data = new String[2][2];
		
		data[0][0] = "lobbyid";
		data[1][0] = "playerid";
		
		data[0][1] = lobbyid;
		data[1][1] = playerid;
		
		sendPOST(to, data);
	}
	
	public void notReadyToPlay(String lobbyid, String playerid)
	{
		URL to = createURL("7");
		
		String[][] data = new String[2][2];
		
		data[0][0] = "lobbyid";
		data[1][0] = "playerid";
		
		data[0][1] = lobbyid;
		data[1][1] = playerid;
		
		sendPOST(to, data);
	}
	
	public void sendMessage(String lobbyid, String playerid, String message)
	{
		URL to = createURL("8");
		
		String[][] data = new String[3][2];
		
		data[0][0] = "lobbyid";
		data[1][0] = "playerid";
		data[2][0] = "message";
		
		data[0][1] = lobbyid;
		data[1][1] = playerid;
		data[2][1] = message;
		
		sendPOST(to, data);
	}
	
	public void receiveMessage(String lobbyid, String time, String messageid)
	{
		URL to = createURL("9");
		
		String[][] data = new String[3][2];
		
		data[0][0] = "lobbyid";
		data[1][0] = "time";
		data[2][0] = "messageid";
		
		data[0][1] = lobbyid;
		data[1][1] = time;
		data[2][1] = messageid;
		
		LinkedList<String> messages = sendPOST(to, data);
	}
	
	public void newPlayer(String game)
	{
		URL to = createURL("a");
		
		String[][] data = new String[1][2];
		
		data[0][0] = "game";
		data[0][1] = game;
		
		playerID = sendPOST(to, data).getFirst();
	}
	
	private LinkedList<String> sendPOST(URL to, String[][] data)
	{
		StringBuffer buf = new StringBuffer(); //This is the String that we will create our data filled URL with.
		
		for(int i = 0; i < data.length; i++) //We are going to iterate through our 2D array.
		{
			try 
			{
				//We encode our URL with our (key, value) pairs.
				buf.append(URLEncoder.encode(data[i][0], "UTF-8") + "=" + URLEncoder.encode(data[i][1], "UTF-8"));
			}catch (UnsupportedEncodingException e){} //This will (should) only happen if the UTF-8 doesn't work.
		}
		
		//This will be the Linked list that contains all the data returned from the server.
		LinkedList<String> rv = new LinkedList<String>();
		
		try 
		{
			URLConnection conn = to.openConnection(); //Open a connection to the server.
			conn.setDoOutput(true); //Tell our connection that we will receive some output.
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream()); //Create our data pipe.
			wr.write(buf.toString()); //Write to our data pipe with out URL that we made earlier.
			wr.flush(); //Empty the pipe and ensure we send all the data.
			
			//This will be used to read that data coming from the server.
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			
			String line; //The lines that we get from the BufferedReader
	        while ((line = rd.readLine()) != null) //Keep reading until we get all the information from the server.
	        {
	        	rv.add(line); //Add each line to the LinkedList
	        }
	        
	        wr.close(); //Close both pipes, we're done using them.
	        rd.close();
		} 
		catch (IOException e) //Incase something bad happens.
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rv; //Return our LinkedList.
	}
	
	/*
	 * Appends the String append to whatever the server path is.
	 */
	private URL createURL(String append)
	{
		try
		{
			return new URL(server.toString() + append); //Takes the default address and appends whatever was given to the URL
		}
		catch(MalformedURLException e) //Incase the new URL is broken.
		{
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		// TODO Auto-generated method stub
		
	}
}

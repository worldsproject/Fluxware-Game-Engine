package networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Lobby extends JPanel 
{
	private JButton newLobby = new JButton("Create Lobby");
	private JButton joinLobby = new JButton("Join Lobby");
	
	private URL server = null;
	
	public Lobby(URL serverLocation)
	{
		
	}
	
	private void createLobby(String name, String game, int playerID)
	{
		
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

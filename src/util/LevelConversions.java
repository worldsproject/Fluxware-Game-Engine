package util;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.security.MessageDigest;
import java.util.LinkedList;

import level.Room;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.newdawn.slick.opengl.Texture;

import sprites.Sprite;


public class LevelConversions 
{
	public Room JSONToRoom(File f)
	{
		Room rv = null;
		
		try 
		{
			BufferedReader fr = new BufferedReader(new FileReader(f));
			
			String version = fr.readLine();
			String room = fr.readLine();
			
			LinkedList<String> list = new LinkedList<String>();
			String line = null;
			
			while((line = fr.readLine()) != null)
			{
				list.add(line);
			}
			
			fr.close();
			
			JSONParser parser = new JSONParser();
			JSONObject ob = (JSONObject)parser.parse(room);
			
			String type = (String)ob.get("type");
			
			rv = new Room(((Long)ob.get("width")).intValue(), ((Long)ob.get("height")).intValue(), ((Long)ob.get("layers")).intValue());
			
			for(String l : list)
			{
				JSONObject temp = (JSONObject)parser.parse(l);
				
				String name = (String)temp.get("name");
				String hash = (String)temp.get("hash");
				int x = ((Long)temp.get("x")).intValue();
				int y = ((Long)temp.get("y")).intValue();
				int layer = ((Long)temp.get("layer")).intValue();
				
				Texture tex = ImageUtil.loadTexture("png", new File(hash + ".png").getPath());
				Sprite s = new Sprite(tex, x, y, layer);
				
				rv.addSprite(s);
			}
		}
		catch (Exception e) 
		{
			//TODO
			e.printStackTrace();
		}
		
		return rv;
	}
	
	public void RoomToJSON(Room room, File f)
	{
		try
		{
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));

			LinkedList<Sprite> allSprite = room.getSprites();
			String typeOfRoom = "Room";

			String version = "0.1";

			JSONObject ver = new JSONObject();
			ver.put("version", version);
			
			bw.write(ver.toString() + "\n");

			JSONObject roomInfo = new JSONObject();
			roomInfo.put("type", typeOfRoom);
			roomInfo.put("width", new Integer(room.getWidth()));
			roomInfo.put("height", new Integer(room.getHeight()));
			roomInfo.put("layers", new Integer(room.getLayers()));
			
			bw.write(roomInfo.toString() + "\n");

			JSONObject sprites = new JSONObject();

			for(Sprite s : allSprite)
			{
				JSONObject temp = new JSONObject();

				String Hash = null;

				try
				{
					ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//					ImageIO.write(s.draw(), "png", outputStream); TODO
					byte[] data = outputStream.toByteArray();

					MessageDigest md = MessageDigest.getInstance("MD5");
					md.update(data);
					byte[] hash = md.digest();

					Hash = returnHex(hash);
				}
				catch(Exception e)
				{
					//TODO
					e.printStackTrace();
				}

//				try TODO
//				{
//					ImageIO.write(s.draw(), "png", new File(Hash + ".png"));
//				}
//				catch (IOException e) 
//				{
//					new CrashReport(e);
//				}

				temp.put("name", s.getClass().getName());
				temp.put("hash", Hash);
				temp.put("x", s.getX());
				temp.put("y", s.getY());
				temp.put("layer", s.getLayer());

				bw.write(temp.toString() + "\n");
			}
			
			bw.flush();
			bw.close();
		}
		catch(Exception e)
		{
			//TODO
			e.printStackTrace();
		}
	}
	
	private static String returnHex(byte[] byes)
	{
		StringBuffer buf = new StringBuffer();

		for(int i = 0; i < byes.length; i++)
		{
			buf.append(Integer.toString( ( byes[i] & 0xff ) + 0x100, 16).substring( 1 ));
		}

		return buf.toString();
	}
}

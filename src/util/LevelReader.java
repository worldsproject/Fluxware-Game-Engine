package util;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.imageio.ImageIO;

import sprites.CharacterSprite;
import sprites.Sprite;

/**
 * The LevelReader class reads in Level files that were either created by the Fluxware Level Editor
 * or created through a text editor.  Details of what correctly constitutes a Level File is defined otherwere.
 *
 */
@Deprecated
public class LevelReader
{	
	private LinkedList<Sprite> sprites = new LinkedList<Sprite>(); //This will contain all of the Sprites read in from the Level.
	private HashMap<Integer, Sprite> examples = new HashMap<Integer, Sprite>();  //Contains all types that the Level will specifically create for.
	
	//Set as a default of -1.  If a one of these are still -1 after a level is loaded, something has gone WRONG.
	private int height = -1;
	private int width = -1;
	private int layers = -1;

	private ArrayList<File> files = new ArrayList<File>(10);
	public LevelReader(File f, HashMap<Integer, Sprite> examples)
	{
		HashMap<String, File> names = new HashMap<String, File>();
		this.examples = examples;

		try 
		{		
			ZipFile zipFile = new ZipFile(f);

			Enumeration enumeration = zipFile.entries();

			while (enumeration.hasMoreElements()) 
			{
				ZipEntry zipEntry = (ZipEntry) enumeration.nextElement();

				BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(zipEntry));

				int size;
				byte[] buffer = new byte[2048];

				File temp = new File(zipEntry.getName());
				files.add(temp);

				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(temp), buffer.length);

				while ((size = bis.read(buffer, 0, buffer.length)) != -1) 
				{
					bos.write(buffer, 0, size);
				}

				bos.flush();
				bos.close();
				bis.close();
				
				names.put(temp.getName(), temp);
			}
		}
		catch (IOException e) 
		{
			e.printStackTrace(); //TODO
		}
		f = null;
		checkVersion(names);
		
		for(File fu : files)
		{
			fu.delete();
		}
	}
	
	public LinkedList<Sprite> getSprites()
	{
		return sprites;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public int getLayers()
	{
		return layers;
	}

	private void checkVersion(HashMap<String, File> files)
	{
		Scanner scan = null;

		try 
		{
			scan = new Scanner(files.get("info.txt"));
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block TIM
			e.printStackTrace();
		}

		String ps = "1";
		
		if(scan.hasNextLine())
		{
			ps = scan.nextLine();
		}

		int s = Integer.parseInt(ps.substring(ps.lastIndexOf(" ")+1, ps.length()));

		switch(s)
		{
		case 1: processVersionOne(files); break;
		case 2: processVersionTwo(files); break;
		default: processVersionOne(files); break;
		}
	}

	private void processVersionOne(HashMap<String, File> files)
	{		
		try
		{
			Scanner scan = new Scanner(files.get("sprites.txt"));
			
			String line = null;
			
			while(scan.hasNextLine())
			{
				line = scan.nextLine();
				
				String[] items = line.split(",");
				
				String superType = items[0];
				String rep = items[1];
				int x = Integer.parseInt(items[2]);
				int y = Integer.parseInt(items[3]);
				int layer = Integer.parseInt(items[4]);
				int id = Integer.parseInt(items[5]);	
				
				if(superType.equalsIgnoreCase("CharacterSprite"))
				{
					CharacterSprite temp = new CharacterSprite(new Character(rep.charAt(0)), x, y, layer);
					try
					{
						Constructor a = examples.get(id).getClass().getConstructor(new Class[]{Character.class, int.class, int.class, int.class});
						Sprite o = (Sprite)a.newInstance(new Object[]{rep.charAt(0), x, y, layer});
						
						sprites.add(o);
					}
					catch(Exception e)
					{
						sprites.add(temp);
					}
				}
				else if(superType.equalsIgnoreCase("ImageSprite"))
				{
					BufferedImage buf = null;
					
					try 
					{
						buf = ImageIO.read(files.get(rep));
					}
					catch (IOException e1) 
					{
						// TODO Auto-generated catch block LACE
						e1.printStackTrace();
					}
					
					Sprite temp = new Sprite(buf, x, y, layer);
					try
					{
						sprites.add((examples.get(id)).getClass().cast(temp));
					}
					catch(Exception e)
					{
						sprites.add(temp);
					}
				}
				else
				{
					//TODO later, I'm busy now. LACE
				}
			}
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();  //TODO Exception LACE
		}
	}
	
	private void processVersionTwo(HashMap<String, File> files)
	{
		try
		{
			Scanner scan = new Scanner(files.get("info.txt"));
			scan.nextLine();
			String width = scan.nextLine();
			String height = scan.nextLine();
			String layers = scan.nextLine();
			
			width = width.substring(width.lastIndexOf(" ")+1, width.length());
			height = height.substring(height.lastIndexOf(" ")+1, height.length());
			layers = layers.substring(layers.lastIndexOf(" ")+1, layers.length());
			
			this.width = Integer.parseInt(width);
			this.height = Integer.parseInt(height);
			this.layers = Integer.parseInt(layers);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			//TODO Exception  LACE
		}
		try
		{
			Scanner scan = new Scanner(files.get("sprites.txt"));
			
			String line = null;
			
			while(scan.hasNextLine())
			{
				line = scan.nextLine();
				
				String[] items = line.split(",");
				
				String superType = items[0];
				String rep = items[1];
				int x = Integer.parseInt(items[2]);
				int y = Integer.parseInt(items[3]);
				int layer = Integer.parseInt(items[4]);
				int id = Integer.parseInt(items[5]);		
				
				if(superType.equalsIgnoreCase("CharacterSprite"))
				{
					CharacterSprite temp = new CharacterSprite(new Character(rep.charAt(0)), x, y, layer);
					try
					{
						Constructor a = examples.get(id).getClass().getConstructor(new Class[]{Character.class, int.class, int.class, int.class});
						Sprite o = (Sprite)a.newInstance(new Object[]{rep.charAt(0), x, y, layer});
						o.serial = id;
						sprites.add(o);
					}
					catch(Exception e)
					{
						temp.serial = id;
						sprites.add(temp);
					}
				}
				else if(superType.equalsIgnoreCase("ImageSprite"))
				{
					BufferedImage buf = null;
					
					try 
					{
						buf = ImageIO.read(files.get(rep + ".png"));					
					}
					catch (IOException e1) 
					{
						// TODO Auto-generated catch block LACE
						e1.printStackTrace();
					}
					
					Sprite temp = new Sprite(buf, x, y, layer);

					try
					{
						Constructor a = examples.get(id).getClass().getConstructor(new Class[]{BufferedImage.class, int.class, int.class, int.class});
						Sprite o = (Sprite)a.newInstance(new Object[]{buf, x, y, layer});
						o.serial = id;
						sprites.add(o);
					}
					catch(Exception e)
					{
						e.printStackTrace();
						sprites.add(temp);
					}
				}
				else
				{
					//TODO later, I'm busy now. LACE
				}
			}
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();  //TODO Exception LACE
		}
	}
	
	public static void upgradeVersionOneToTwo(File f)
	{
		
	}
}
package sprites;

import java.util.ArrayList;

public class SpriteGroup 
{
	private ArrayList<Sprite> group = new ArrayList<Sprite>();
	private String name;
	
	private SpriteGroup(String name)
	{
		this.name = name;
	}
	
	public void addSprite(Sprite s)
	{
		group.add(s);
	}
	
	public void removeSprite(Sprite s)
	{
		group.remove(s);
	}
	
	public ArrayList<Sprite> getGroup()
	{
		return group;
	}
	
	public String getName()
	{
		return name;
	}
}

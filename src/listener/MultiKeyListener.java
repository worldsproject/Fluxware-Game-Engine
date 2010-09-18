package listener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public class MultiKeyListener implements KeyListener 
{
	private HashMap<Integer, Boolean> keys = new HashMap<Integer, Boolean>();
	
	public MultiKeyListener()
	{
		
	}
	
	public boolean isKeyPressed(int key)
	{
		if(keys.get(key) == null || keys.get(key) == false)
		{
			return false;
		}
		
		return true;
	}
	
	@Override
	public void keyPressed(KeyEvent e) 
	{
		keys.put(e.getKeyCode(), new Boolean(true));
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		keys.put(e.getKeyCode(), new Boolean(false));
	}

	@Override
	public void keyTyped(KeyEvent e){}

}

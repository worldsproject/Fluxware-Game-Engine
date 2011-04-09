package listener;

import gui.menu.MenuItem;

import java.util.EventObject;

public class MenuEvent extends EventObject
{
	public MenuEvent(MenuItem source)
	{
		super(source);
	}
}

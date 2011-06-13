package listener;

import gui.menu.MenuItem;

import java.util.EventObject;

/**
 * The event that is fired when a MenuItem is selected.
 * @author Tim Butram
 *
 */
@SuppressWarnings("serial")
public class MenuEvent extends EventObject
{
	/**
	 * Fired when a MenuItem is picked.
	 * @param source - The MenuItem that was selected.
	 */
	public MenuEvent(MenuItem source)
	{
		super(source);
	}
}

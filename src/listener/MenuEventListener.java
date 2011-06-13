package listener;

import java.util.EventListener;

/**
 * Listener for MenuItem selection events.
 * @author Tim Butram
 *
 */
public interface MenuEventListener extends EventListener
{
	public void menuItemSelected(MenuEvent e);
}

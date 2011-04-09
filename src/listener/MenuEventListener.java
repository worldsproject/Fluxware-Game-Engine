package listener;

import java.util.EventListener;

public interface MenuEventListener extends EventListener
{
	public void menuItemSelected(MenuEvent e);
}

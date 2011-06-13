package gui.menu;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.event.EventListenerList;

import level.Room;
import listener.MenuEvent;
import listener.MenuEventListener;
import sprites.Sprite;
import util.Point2D;

public class Menu extends Room
{
	private EventListenerList listeners = new EventListenerList();

	@SuppressWarnings("unused")
	private Sprite background = null;
	@SuppressWarnings("unused")
	private Sprite title = null;
	private Sprite cursorIcon = null;

	private ArrayList<MenuItem> items = new ArrayList<MenuItem>();

	private int cursor = 0;
	private int upKey = KeyEvent.VK_UP;
	private int downKey = KeyEvent.VK_DOWN;
	private int selectKey = KeyEvent.VK_ENTER;

	/**
	 * Creates a Menu of <b>width</b> width and <b>height</b> height.
	 * @param width - How wide the menu is.
	 * @param height - How tall the menu is.
	 */
	public Menu(int width, int height)
	{
		super(width, height, 2);
	}

	/**
	 * Does nothing. Only returns. Use the more appropriate menu methods.
	 */
	public void addSprite(Sprite s)
	{
		return;
	}

	/**
	 * Sets the given Sprite to (0,0,0) and is used as a background.
	 * @param background
	 */
	public void addBackground(Sprite background)
	{
		this.background = background;
		background.setPoint(new Point2D(0, 0, 0));
		super.addSprite(background);
	}

	/**
	 * Puts a title 50px from the top, and centered.
	 * @param title
	 */
	public void addTitle(Sprite title)
	{
		this.title = title;
		int center = (this.getWidth() >> 1) - (title.getWidth() >> 1);
		title.setPoint(new Point2D(center, 50, 0));
		super.addSprite(title);
	}

	/**
	 * Adds a MenuItem. This will attempt to keep the items centered 50px below center.
	 * MenuItems are added in the order they are given. Also, it keeps the spacing of 5px.
	 * @param item
	 */
	public void addMenuItem(MenuItem item)
	{
		items.add(item);
		super.addSprite(item);

		int center = (this.getHeight() >> 1) + 50;

		for(MenuItem i : items)
		{
			int xCen = (this.getWidth() >> 1) - (i.getWidth() >> 1);
			i.setPoint(new Point2D(xCen, center, 1));
			center += i.getHeight() + 5;
		}
	}

	/**
	 * Adds a cursor icon. This cursor can be any Sprite.
	 * @param cursor - The Sprite that is to act as the menu cursor.
	 */
	public void addCursor(Sprite cursor)
	{
		cursorIcon = cursor;
		super.addSprite(cursor);
		setCursorPoint();
	}

	/**
	 * Sets the key to move the current selection up.
	 * @param keyCode - The key to move the current selection up.
	 */
	public void setMoveUpKey(int keyCode)
	{
		upKey = keyCode;
	}

	/**
	 * Sets the key to move the current selection down.
	 * @param keyCode - The key to move the current selection down.
	 */
	public void setMoveDownKey(int keyCode)
	{
		downKey = keyCode;
	}

	/**
	 * Sets the key to choose the current selection.
	 * @param keyCode - The key to choose the current selection.
	 */
	public void setSelectKey(int keyCode)
	{
		selectKey = keyCode;
	}

	private void incrementCursor()
	{
		cursor++;

		if(cursor > items.size()-1)
		{
			cursor = 0;
		}

		setCursorPoint();
	}

	private void decrementCursor()
	{
		cursor--;

		if(cursor < 0)
		{
			cursor = items.size()-1;
		}

		setCursorPoint();
	}

	private void setCursorPoint()
	{
		if(cursorIcon != null)
		{
			MenuItem i = items.get(cursor);

			cursorIcon.setPoint(new Point2D(i.getX()-cursorIcon.getWidth() - 5 ,i.getY(), 1));
		}
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		int kc = e.getKeyCode();

		if(kc == downKey)
		{
			incrementCursor();
		}
		else if(kc == upKey)
		{
			decrementCursor();
		}
		else if(kc == selectKey)
		{
			fireEvent(new MenuEvent(items.get(cursor)));
		}
	}

	/**
	 * Adds a MenuListener.
	 * @param m - The MenuListener to be added.
	 */
	public void addMenuListener(MenuEventListener m)
	{
		listeners.add(MenuEventListener.class, m);
	}

	/**
	 * Removes a MenuListener from this Menu.
	 * @param m - The MenuListener to be removed.
	 */
	public void removeMenuListener(MenuEventListener m)
	{
		listeners.remove(MenuEventListener.class, m);
	}

	private void fireEvent(MenuEvent m)
	{
		MenuEventListener[] list = listeners.getListeners(MenuEventListener.class);

		for (int i=0; i<list.length; i++) 
		{
			((MenuEventListener)list[i]).menuItemSelected(m);
		}
	}
}

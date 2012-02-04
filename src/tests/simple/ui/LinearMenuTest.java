package tests.simple.ui;

import gui.Game;
import gui.menu.LinearMainMenu;

import java.awt.Dimension;

import de.matthiasmann.twl.Button;

public class LinearMenuTest extends Game
{
	public LinearMenuTest()
	{
		super(null, false, new Dimension(640, 480));
	}
	
	public static void main(String args[])
	{
		LinearMenuTest test = new LinearMenuTest();
		LinearMainMenu lmm = new LinearMainMenu(test, 640, 480, 1);
		Button b = new Button();
		b.setTheme("pause");
		lmm.addButton(b);
		
		Button c = new Button();
		c.setTheme("pause");
		c.adjustSize();
		lmm.addButton(c);
		Button d = new Button();
		d.setTheme("pause");
		d.adjustSize();
		lmm.addButton(d);
//		test.setTheme("tests/simple/ui/resources/Run.xml");
		test.setRoom(lmm);
		test.startGame();
	}
}

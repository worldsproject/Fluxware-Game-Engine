package gui.menu;

import gui.Game;

import java.util.ArrayList;

import level.Room;
import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.Label;

public class LinearMainMenu extends Room
{
	private boolean hasTitle = false;
	private int mid_height = 0;
	private int mid_width = 0;
	private int height = 0;
	
	private int margin = 5;
	private int totalHeight = 0;
	
	private Game game;
	
	private Label title = null;
	private ArrayList<Button> buttons = new ArrayList<Button>();
	
	public LinearMainMenu(Game game, int width, int height, int layers)
	{
		super(width, height, layers);
		
		mid_height = (int)(height * 0.4);
		mid_width = width/2;
		this.height = height;
		
		this.game = game;
	}
	
	public void addTitle(Label title)
	{
		game.addWidget(title);
		
		hasTitle = true;
		this.title = title;
		
		int t_width = title.getWidth();
		int t_height = title.getHeight();
		
		
		title.setPosition((mid_width - (t_width >> 1)), (mid_height - (t_height >> 1)));
		
		adjustButtons();
	}
	
	public void addButton(Button button)
	{
		game.addWidget(button);
		buttons.add(button);
		totalHeight += button.getHeight();
		
		adjustButtons();
	}
	
	public void setBottomMargin(int pixels)
	{
		margin = pixels;
		adjustButtons();
	}
	
	private void adjustButtons()
	{
		int distance_covered = 0;
		
		if(hasTitle)
		{
			distance_covered = mid_height + (((height - mid_height) - totalHeight) >> 1);
		}
		else
		{
			distance_covered = (height - totalHeight) >> 1;
		}
		
		for(Button b:buttons)
		{
			b.setPosition((mid_width - (b.getWidth()/2)), distance_covered);
			distance_covered += (b.getHeight() + margin);
		}
	}
}

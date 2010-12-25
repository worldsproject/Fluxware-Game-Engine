package sprites;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;


/**
 * This is a Character Based Sprite.  Used primarily for text based games.
 * @author Fluxware
 *
 */
@SuppressWarnings("serial")
public class CharacterSprite extends Sprite
{
	/**
	 * Default Constructor for the Sprite.  Creates a blank Sprite
	 * at location (-1, -1) with a null representation.
	 */
	public CharacterSprite()
	{
		super();
	}

	/**
	 * Default Character based sprite.  The sprites representation is
	 * based on the char object passed in.  The Sprite created from the character
	 * will be <i>Color.blue</i> in color and have the property of 
	 * Font.MONOSPACED, Font.PLAIN and have a size of 24.  the resulting Sprite will be
	 * 30px by 30px.
	 * 
	 * @param c - The Sprite's pictorial representation
	 * @param x - X coordinate the Sprite will be set to.
	 * @param y - Y coordinate the Sprite will be set to.
	 * @param layer - The layer the Sprite resides on. (0 is lowest layer)
	 */
	public CharacterSprite(Character c, int x, int y, int layer)
	{
		super(null, x, y, layer);
		
		BufferedImage img = createImage(c.toString());
		
		this.setSprite(resizeImage(img, 30));
	}
	
	/**
	 * Default Character based sprite.  The sprites representation is
	 * based on the char object passed in.  The Sprite created from the character
	 * will be <i>Color.blue</i> in color and have the property of 
	 * Font.MONOSPACED, Font.PLAIN and have a size of 24.  the resulting Sprite will be
	 * 30px by 30px.
	 * 
	 * If a size of -1 is specified, then the resulting BufferedImage will not be resized. 
	 * 
	 * @param c - the Sprite's pictorial representation.
	 * @param x - X coordinate the Sprite will be set to.
	 * @param y - Y coordinate the Sprite will be set to.
	 * @param layer - The layer the Sprite resides on. (0 is the lowest layer)
	 * @param size - The resulting size of the Sprite
	 */
	public CharacterSprite(Character c, int x, int y, int layer, int size)
	{
		super(null, x, y, layer);

		BufferedImage img = createImage(c.toString());
		
		if(size > 0)
			this.setSprite(resizeImage(img, size));
		else
			this.setSprite(img);
	}
	
	/**
	 * Creates a new CharacterSprite which embodies the given String.
	 * The resulting BufferedImage will not be resized.
	 * 
	 * @param s - The String the CharacterSprite will represent.
	 * @param x - The X location of the CharacterSprite.
	 * @param y - The Y location of the CharacterSprite.
	 * @param layer - The Layer the CharacterSprite will be located on.
	 */
	public CharacterSprite(String s, int x, int y, int layer)
	{
		super(null, x, y, layer);
		
		BufferedImage img = createImage(s);
		this.setSprite(img);
	}

	/**
	 * Changes the Character that this Sprite represents.
	 * @param c - The new Character.
	 */
	public void setSprite(Character c) 
	{
		BufferedImage img = this.createImage(c.toString());

		this.setSprite(resizeImage(img, 30));
	}
	
	/**
	 * Changes the Character that this Sprite represents.
	 * @param c - The new Character.
	 * @param size - The size of the Sprite.
	 */
	public void setSprite(Character c, int size) 
	{
		BufferedImage img = createImage(c.toString());

		this.setSprite(resizeImage(img, size));
	}
	
	private BufferedImage resizeImage(BufferedImage img, int size)
	{
		if(size < 0)
			return img;
		
		int width = size;
		int height = size;
		int type = img.getType() == 0? BufferedImage.TYPE_INT_ARGB : img.getType();
		BufferedImage resizedImage = new BufferedImage(width, height, type);
		Graphics2D g = resizedImage.createGraphics();
		g.setComposite(AlphaComposite.Src);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawImage(img, 0, 0, width, height, null);
		
		return resizedImage;
	}
	
	private BufferedImage createImage(String c)
	{
		Font f = new Font(Font.MONOSPACED, Font.PLAIN, 24);

		BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics g = img.getGraphics();
		g.setFont(f);
		FontRenderContext frc = g.getFontMetrics().getFontRenderContext();

		Rectangle2D rect = f.getStringBounds(c.toString(), frc);
		// release resources
		g.dispose();

		img = new BufferedImage((int)Math.ceil(rect.getWidth()), (int)Math.ceil(rect.getHeight()), BufferedImage.TYPE_4BYTE_ABGR);

		g = img.getGraphics();
		g.setColor(Color.BLUE);
		g.setFont(f);
		g.drawString(c, 0, (int)Math.ceil(rect.getHeight()));
		
		// release resources
		g.dispose();
		
		return img;
	}
}

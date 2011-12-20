package sprites;

import org.newdawn.slick.opengl.Texture;

import util.ImageData;

/**
 * The Animated Sprite takes in an array of images and rotates through them in order
 * to create an animation.
 * @author Tim Butram
 *
 */
@SuppressWarnings("serial")
public class AnimatedSprite extends Sprite implements Runnable
{
	public final int serial = 3;

	protected ImageData[] textures = null;
	protected long time = 200;

	protected int currentImg = 0;

	/**
	 * Creates a default AnimatedSprite.  This sprite has no animation as it is
	 * simple a null image.
	 */
	public AnimatedSprite()
	{
		super();
		
		new Thread(this).start();
	}

	public AnimatedSprite(ImageData texture, int x, int y, int layer)
	{
		super(texture, x, y, layer);

		textures = new ImageData[] {texture};
		
		new Thread(this).start();
	}

	/**
	 * Creates a new Animated Sprite with the animation being defined as the array of
	 * Images.  Each frame persists for 200 milliseconds (1/5th of a second).
	 * @param img - The array of images the Animated Sprite will flip through. 
	 * @param x - The initial X coordinate of the Sprite.
	 * @param y - The initial Y coordinate of the Sprite.
	 * @param layer - The initial Layer position of the Sprite.
	 */
	public AnimatedSprite(ImageData[] tex, int x, int y, int layer)
	{
		super(tex[0], x, y, layer);

		textures = tex;
		
		new Thread(this).start();
	}

	/**
	 * Creates a new Animated Sprite with the animation being defined as the array of
	 * Images.  Each from persists for the user specified time of <i>time</i>
	 * @param img - The array of images the Animated Sprite will flip through.
	 * @param x - The initial X coordinate of the Sprite.
	 * @param y - The initial Y coordinate of the Sprite.
	 * @param layer - The initial Layer position of the Sprite.
	 * @param time - The period of time that each from persists.
	 */
	public AnimatedSprite(ImageData[] tex, int x, int y, int layer, long time)
	{
		super(tex[0], x, y, layer);

		textures = tex;
		this.time = time;
		
		new Thread(this).start();
	}

	/**
	 * Sets a new animation sequence for the Animated Sprite.
	 * @param im - The array of images to replace the old array.
	 */
	public void setSprite(ImageData[] tex)
	{
		textures = tex;
	}

	private void nextFrame()
	{
		currentImg++;

		if(currentImg >= textures.length)
		{
			currentImg = 0;
		}
	}

	@Override
	public void run() 
	{
		while(true)
		{
			try 
			{
				Thread.sleep(time);
				nextFrame();
			} 
			catch (InterruptedException e){}
		}
	}
}

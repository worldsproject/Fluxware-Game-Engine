package test.ppctest;

import sprites.Sprite;
import util.ImageUtil;

public class CombinedBitMaskTest {

	public static void main(String args[])
	{
		Sprite a = new Sprite(ImageUtil.getBufferedImage("/resources/purple square.png"),0,0,0);
		Sprite b = new Sprite(ImageUtil.getBufferedImage("/resources/green square.png"),0,0,0);

		int[] amask = ImageUtil.getBitMask(a.print().getRGB(0, 0, a.getWidth(), a.getHeight(), null, 0, a.getWidth()));
		int[] bmask = ImageUtil.getBitMask(b.print().getRGB(0, 0, b.getWidth(), b.getHeight(), null, 0, b.getWidth()));

		int[] cmask = ImageUtil.getCombinedBitMask(amask, bmask);

		for(int i = 0; i<cmask.length; i++)
		{
			if((amask[i]==0x1)&&(bmask[i]==0x1)&&(cmask[i]==0x0))
			{
				System.out.println("Test failed: " + amask[i] + " : " + bmask[i] + " : " + cmask[i]);
				System.exit(0);
			}
			else if(((amask[i]!=0x1)||(bmask[i]!=0x1))&&cmask[i]==0x1)
			{
				System.out.println("Test failed: " + amask[i] + " : " + bmask[i] + " : " + cmask[i]);
				System.exit(0);
			}
		}
		
		System.out.println("Test passed.");
	}

}

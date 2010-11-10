package test.ppctest;

import sprites.Sprite;
import util.ImageUtil;

public class BitMaskTest {

	public static void main(String args[])
	{
		Sprite a = new Sprite(ImageUtil.getBufferedImage("/resources/purple square.png"),0,0,0);

		int[] pixels = a.print().getRGB(0, 0, a.getWidth(), a.getHeight(), null, 0, a.getWidth());
		int[] bitmask = ImageUtil.getBitMask(pixels);
		
		int[] p = a.print().getRGB(0, 0, a.getWidth(), a.getHeight(), null, 0, a.getWidth());
		
		for(int i = 0; i<bitmask.length; i++)
		{
			if(((p[i]>>>24)!=0x0)&&(bitmask[i]==0x0))
			{
				System.out.println("Test Failed: " + p[i] + " : " + bitmask[i]);
				System.exit(0);
			}
			else if(((p[i]>>>24)==0x0)&&(bitmask[i]==0x1))
			{
				System.out.println("Test Failed: " + p[i] + " : " + bitmask[i]);
				System.exit(0);
			}
		}
		System.out.println("Test passed.");
	}
}
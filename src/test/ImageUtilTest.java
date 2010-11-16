package test;
import static org.junit.Assert.assertEquals;

import java.awt.image.BufferedImage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.ImageUtil;


public class ImageUtilTest {

	public BufferedImage a,b = null;
	public int[] amask,bmask;
	
	@Before
	public void setUp()
	{
		a = ImageUtil.getBufferedImage("/resources/purple square.png");
		b = ImageUtil.getBufferedImage("/resources/blue square.png");
		amask = ImageUtil.getBitMask(a.getRGB(0, 0, a.getWidth(), a.getHeight(), null, 0, a.getWidth()));
		bmask = ImageUtil.getBitMask(b.getRGB(0, 0, b.getWidth(), b.getHeight(), null, 0, b.getWidth()));
	}
	
	@After
	public void tearDown()
	{
		a = null;
		b = null;
		amask = null;
		bmask = null;
	}
	
	@Test
	public void testGetBitMask1()
	{
		int[] amask2 = ImageUtil.getBitMask(a.getRGB(0, 0, a.getWidth(), a.getHeight(), null, 0, a.getWidth()));
		amask = ImageUtil.getBitMask(amask);
		for(int i = 0; i<amask.length;i++)
		{
			if((amask2[i]>>>24)!=0x0)
			{
				assertEquals(0x1, amask[i]);
			}
			else if((amask2[i]>>>24)==0x0)
			{
				assertEquals(0x0, amask[i]);
			}
		}
	}
	
	@Test
	public void testGetCombinedBitMask()
	{
		int[] cmask = ImageUtil.getCombinedBitMask(amask, bmask);
		
		for(int i=0;i<cmask.length;i++)
		{
			if((amask[i]==0x1)&&(bmask[i]==0x1))
			{
				assertEquals(0x1, cmask[i]);
			}
			else
			{
				assertEquals(0x0, cmask[i]);
			}
		}
	}
}

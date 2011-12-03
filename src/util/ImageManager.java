package util;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Hashtable;

import javax.swing.ImageIcon;

import org.lwjgl.BufferUtils;

/**
 * Handles Loading of images, along with some simple image functions.
 * 
 * @author Tim Butram
 *
 */
public class ImageManager 
{	
	private HashMap<String, Texture> cache = new HashMap<String, Texture>(); //Cache of previously used Textures.
	private ColorModel glAlphaColorModel; //Color model with Alpha.
	private ColorModel glColorModel; //Color model without Alpha.

	private IntBuffer textureIDBuffer = BufferUtils.createIntBuffer(1);

	public ImageManager()
	{
		glAlphaColorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB),
				new int[] {8,8,8,8},
				true,
				false,
				ComponentColorModel.TRANSLUCENT,
				DataBuffer.TYPE_BYTE);

		glColorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB),
				new int[] {8,8,8,0},
				false,
				false,
				ComponentColorModel.OPAQUE,
				DataBuffer.TYPE_BYTE);
	}

	public Texture getTexture(String location)
	{
		try
		{
			Texture rv = cache.get(location);

			if(rv != null)
				return rv;

			rv = getTexture(location, GL_TEXTURE_2D, GL_RGBA, GL_LINEAR, GL_LINEAR);

			cache.put(location, rv);

			return rv;
		}
		catch(IOException e)
		{
			//TODO
			e.printStackTrace();
		}

		return null;
	}

	public Texture getTexture(String location, int target, int dstPixelFormat, int minFilter, int magFilter) throws IOException
	{
		int srcPixelFormat;
		int textureID = createTextureID();

		Texture texture = new Texture(target, textureID);

		//Binding the texture
		glBindTexture(target, textureID);

		BufferedImage buf = loadImage(location);
		texture.setHeight(buf.getHeight());
		texture.setWidth(buf.getWidth());

		if(buf.getColorModel().hasAlpha())
		{
			srcPixelFormat = GL_RGBA;
		}
		else
		{
			srcPixelFormat = GL_RGB;
		}

		ByteBuffer textureBuffer = convertImageData(buf, texture);

		if(target == GL_TEXTURE_2D)
		{
			glTexParameteri(target, GL_TEXTURE_MIN_FILTER, minFilter);
			glTexParameteri(target, GL_TEXTURE_MAG_FILTER, magFilter);
		}

		glTexImage2D(target,
				0,
				dstPixelFormat,
				(int)Math.pow(2, (32 - Integer.numberOfLeadingZeros(buf.getWidth() - 1))),
				(int)Math.pow(2, (32 - Integer.numberOfLeadingZeros(buf.getHeight() - 1))),
				0,
				srcPixelFormat,
				GL_UNSIGNED_BYTE,
				textureBuffer );

		return texture;
	}

	/*
	 * Creates a unique texture ID.
	 */
	private int createTextureID()
	{
		glGenTextures(textureIDBuffer);
		return textureIDBuffer.get(0);
	}

	private BufferedImage loadImage(String location) throws IOException
	{
		URL url = ImageManager.class.getClassLoader().getResource(location);

		if(url == null)
		{
			throw new IOException("Unable to find " + location);
		}

		Image img = new ImageIcon(url).getImage();
		BufferedImage buf = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics g = buf.getGraphics();
		g.drawImage(img, 0, 0, null);
		g.dispose();

		return buf;
	}

	private ByteBuffer convertImageData(BufferedImage buf, Texture texture)
	{
		ByteBuffer imageBuffer;
		WritableRaster raster;
		BufferedImage texImage;

		int texWidth = (int)Math.pow(2, (32 - Integer.numberOfLeadingZeros(buf.getWidth() - 1)));
		int texHeight = (int)Math.pow(2, (32 - Integer.numberOfLeadingZeros(buf.getHeight() - 1)));

		texture.setHeight(texHeight);
		texture.setWidth(texWidth);

		if (buf.getColorModel().hasAlpha())
		{
			raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, texWidth, texHeight, 4, null);
			texImage = new BufferedImage(glAlphaColorModel, raster, false, new Hashtable());
		}
		else
		{
			raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE,texWidth,texHeight,3,null);
			texImage = new BufferedImage(glColorModel, raster, false, new Hashtable());
		}

		Graphics g = texImage.getGraphics();
		g.setColor(new Color(0f, 0f, 0f, 0f));
		g.fillRect(0, 0, texWidth, texHeight);
		g.drawImage(buf, 0, 0, null);

		byte[] data = ((DataBufferByte) texImage.getRaster().getDataBuffer()).getData();

		imageBuffer = ByteBuffer.allocateDirect(data.length);
		imageBuffer.order(ByteOrder.nativeOrder());
		imageBuffer.put(data, 0, data.length);
		imageBuffer.flip();

		return imageBuffer;
	}
}

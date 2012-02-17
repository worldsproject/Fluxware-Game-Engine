package sound;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import org.lwjgl.openal.AL10;

import exception.IncorrectFormatException;

public class WaveLoader extends SoundLoader
{
	private boolean loaded_correctly = false;

	private int sample_rate;
	private int number_of_channels;
	private int byte_rate;
	private int block_align;
	private int sample_size;
	private int format;

	private final IntBuffer RIFF = IntBuffer.wrap(new int[]{0x52, 0x49, 0x46, 0x46});
	private final IntBuffer WAVE = IntBuffer.wrap(new int[]{0x57, 0x41, 0x56, 0x45});
	private final IntBuffer FMT_ = IntBuffer.wrap(new int[]{0x66, 0x6D, 0x74, 0x20});
	private final IntBuffer DATA = IntBuffer.wrap(new int[]{0x64, 0x61, 0x74, 0x61});

	private FileInputStream dir;

	public WaveLoader(String path)
	{
		path = WaveLoader.class.getResource(path).getPath();
		
		File potential_wav = new File(path);
		
		try
		{
			dir = new FileInputStream(path);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			return;
		}

		try
		{	

			//Read in the first 4 bytes. These bytes should spell RIFF.
			byte[] buffer = new byte[4];
			dir.read(buffer);

			//Unsign the bytes and put it into a IntBuffer for comparison to the IntBuffer RIFF
			IntBuffer is_riff = IntBuffer.wrap(this.unsign_byte(buffer));

			if(RIFF.compareTo(is_riff) != 0)
			{
				new IncorrectFormatException(IncorrectFormatException.NOT_A_RIFF);
				return;
			}
			
			//The next 4 bytes describe the total filesize minus 8.
			dir.read(buffer);
			
			int length = turn_byte_buffer_into_int(buffer);
			
			if(length+8 != potential_wav.length())
			{
				new IncorrectFormatException(IncorrectFormatException.INCORRECT_FILESIZE);
				return;
			}
			
			//The next 4 bytes should read WAVE
			dir.read(buffer);
			IntBuffer is_wave = IntBuffer.wrap(this.unsign_byte(buffer));
			
			if(WAVE.compareTo(is_wave) != 0)
			{
				new IncorrectFormatException(IncorrectFormatException.NOT_A_WAVE);
				return;
			}
			
			//As the format is WAVE, the next 4 bytes should read "fmt ". Includes the space.
			dir.read(buffer);
			IntBuffer is_fmt = IntBuffer.wrap(this.unsign_byte(buffer));
			
			if(FMT_.compareTo(is_fmt) != 0)
			{
				new IncorrectFormatException(IncorrectFormatException.DOES_NOT_HAVE_FMT);
				return;
			}
			
			//Next we read the subchunk size. As we are reading an uncompressed .wav, it should be 16.
			dir.read(buffer);
			
			length = turn_byte_buffer_into_int(buffer);
			
			if(length != 16)
			{
				new IncorrectFormatException(IncorrectFormatException.SUBCHUNK_SIZE_INCORRECT);
				return;
			}
			
			clear(buffer);
			
			//Next we read in the AudioFormat. This must equal 1 for PCM
			dir.read(buffer, 0, 2);
			
			length = turn_byte_buffer_into_int(buffer);
			
			if(length != 1)
			{
				new IncorrectFormatException(IncorrectFormatException.COMPRESSION_DETECTED);
				return;
			}
				
			//Now we need to read in the number of channels
			clear(buffer);
			dir.read(buffer, 0, 2);			
			number_of_channels = turn_byte_buffer_into_int(buffer);
			
			//Next is the sample rate;
			dir.read(buffer);
			
			sample_rate = turn_byte_buffer_into_int(buffer);
			
			//Now the byte rate
			dir.read(buffer);
			byte_rate = turn_byte_buffer_into_int(buffer);
			
			//The block align size (Size of one sample with all channels.
			clear(buffer);
			dir.read(buffer, 0, 2);
			block_align = turn_byte_buffer_into_int(buffer);
			
			//Read in the bits per sample;
			clear(buffer);
			dir.read(buffer, 0, 2);
			sample_size = turn_byte_buffer_into_int(buffer);
			
			//The next thing should be the value data.
			dir.read(buffer);
			IntBuffer is_data = IntBuffer.wrap(this.unsign_byte(buffer));
			
			if(DATA.compareTo(is_data) != 0)
			{
				new IncorrectFormatException(IncorrectFormatException.DATA_NOT_FOUND);
				return;
			}
			
			//remainder filesize. Don't care about it.
			dir.read(buffer);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		if(sample_size == 8)
		{
			if(number_of_channels == 1)
			{
				format = AL10.AL_FORMAT_MONO8;
			}
			else if(number_of_channels == 2)
			{
				format = AL10.AL_FORMAT_STEREO8;
			}
			else
			{
				new IncorrectFormatException(IncorrectFormatException.UNSUPPORTED_CHANNEL_NUMBER);
				return;
			}
		}
		else if(sample_size == 16)
		{
			if(number_of_channels == 1)
			{
				format = AL10.AL_FORMAT_MONO16;
			}
			else if(number_of_channels == 2)
			{
				format = AL10.AL_FORMAT_STEREO16;
			}
			else
			{
				new IncorrectFormatException(IncorrectFormatException.UNSUPPORTED_CHANNEL_NUMBER);
				return;
			}
		}
		else
		{
			new IncorrectFormatException(IncorrectFormatException.UNSUPPORTED_SAMPLE_SIZE);
			return;
		}
		
		loaded_correctly = true;
	}

	@Override
	public int read(ByteBuffer buffer)
	{
		buffer.clear();
		byte[] buf = new byte[buffer.capacity()];
		int amount_read = -1;
		
		try
		{
			amount_read = dir.read(buf);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		buffer.put(buf);
		
		return amount_read;
	}
	
	public int getFormat()
	{
		return format;
	}
	
	public int getSampleRate()
	{
		return sample_rate;
	}
	
	public boolean readCorrectly()
	{
		return loaded_correctly;
	}

	private int turn_byte_buffer_into_int(byte[] buf)
	{
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put(buf);
		bb.rewind();
		return bb.getInt();
	}
	
	private void clear(byte[] buffer)
	{
		for(int i = 0; i < buffer.length; i++)
			buffer[i] = 0;
	}
	
	private int[] unsign_byte(byte[] b)
	{
		int[] rv = new int[b.length];
		
		for(int i = 0; i < rv.length; i++)
		{
			rv[i] = 0xFF & b[i];
		}
		
		return rv;
	}

}

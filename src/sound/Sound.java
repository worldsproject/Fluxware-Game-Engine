package sound;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

public class Sound 
{
	/** Buffers hold sound data. */
	IntBuffer buffer = BufferUtils.createIntBuffer(1);

	/** Sources are points emitting sound. */
	IntBuffer source = BufferUtils.createIntBuffer(1);
	
	/** Buffer for Streaming files */
	private ByteBuffer dataBuffer = ByteBuffer.allocateDirect(4096*8);

	/** Position of the source sound. */
	FloatBuffer sourcePos = (FloatBuffer)BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f }).rewind();

	/** Velocity of the source sound. */
	FloatBuffer sourceVel = (FloatBuffer)BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f }).rewind();

	/** Position of the listener. */
	FloatBuffer listenerPos = (FloatBuffer)BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f }).rewind();

	/** Velocity of the listener. */
	FloatBuffer listenerVel = (FloatBuffer)BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f }).rewind();

	/** Orientation of the listener. (first 3 elements are "at", second 3 are "up") */
	FloatBuffer listenerOri = (FloatBuffer)BufferUtils.createFloatBuffer(6).put(new float[] { 0.0f, 0.0f, -1.0f,  0.0f, 1.0f, 0.0f }).rewind();

	public Sound(String path) throws IOException
	{
		loadALData(path);
		setListenerValues();
	}

	/**
	 * boolean LoadALData()
	 *
	 *  This function will load our sample data from the disk using the Alut
	 *  utility and send the data into OpenAL as a buffer. A source is then
	 *  also created to play that buffer.
	 * @throws IOException 
	 */
	private void loadALData(String path) throws IOException 
	{
		// Load wav data into a buffer.
		AL10.alGenBuffers(buffer);

		checkError();

		if(path.endsWith("wav"))
		{
			//Loads the wave file from this class's package in your classpath
			WaveData waveFile = WaveData.create(path);

			AL10.alBufferData(buffer.get(0), waveFile.format, waveFile.data, waveFile.samplerate);
			waveFile.dispose();
		}
		else if(path.endsWith("ogg"))
		{
			InputStream in = this.getClass().getResourceAsStream(path);
			System.out.println(in == null);
			OggInputStream ois = new OggInputStream(in);
			
			int bytesRead = ois.read(dataBuffer, 0, dataBuffer.capacity());
			
			if(bytesRead >= 0)
			{
				dataBuffer.rewind();
				boolean mono = (ois.getFormat() == OggInputStream.FORMAT_MONO16);
				int format = (mono ? AL10.AL_FORMAT_MONO16 : AL10.AL_FORMAT_STEREO16);
				AL10.alBufferData(buffer.get(0), format, dataBuffer, ois.getRate());
				checkError();
			}
		}

		// Bind the buffer with the source.
		AL10.alGenSources(source);


		checkError();

		AL10.alSourcei(source.get(0), AL10.AL_BUFFER,   buffer.get(0) );
		AL10.alSourcef(source.get(0), AL10.AL_PITCH,    1.0f          );
		AL10.alSourcef(source.get(0), AL10.AL_GAIN,     1.0f          );
		AL10.alSource (source.get(0), AL10.AL_POSITION, sourcePos     );
		AL10.alSource (source.get(0), AL10.AL_VELOCITY, sourceVel     );

		checkError();
	}

	private void checkError()
	{
		if(AL10.alGetError() != AL10.AL_NO_ERROR)
			System.err.println(AL10.AL_FALSE);
	}

	/**
	 * void setListenerValues()
	 *
	 *  We already defined certain values for the Listener, but we need
	 *  to tell OpenAL to use that data. This function does just that.
	 */
	public void setListenerValues() 
	{
		AL10.alListener(AL10.AL_POSITION,    listenerPos);
		AL10.alListener(AL10.AL_VELOCITY,    listenerVel);
		AL10.alListener(AL10.AL_ORIENTATION, listenerOri);
	}

	/**
	 * void killALData()
	 *
	 *  We have allocated memory for our buffers and sources which needs
	 *  to be returned to the system. This function frees that memory.
	 */
	public void killALData() 
	{
		AL10.alDeleteSources(source);
		AL10.alDeleteBuffers(buffer);
	}

	public static void main(String[] args) throws IOException 
	{
		try
		{
			AL.create();
		}
		catch (LWJGLException le) 
		{
			le.printStackTrace();
			return;
		}

		AL10.alGetError();

		Sound one = new Sound("tests/resources/sounds/boo.ogg");
		one.play();

		while(one.isPlaying());

	}

	public void play()
	{
		AL10.alSourcePlay(source.get(0));
	}

	public void pause()
	{
		AL10.alSourcePause(source.get(0));
	}

	public void stop()
	{
		AL10.alSourceStop(source.get(0));
	}

	public boolean isPlaying()
	{
		return AL10.alGetSourcei(source.get(0), AL10.AL_SOURCE_STATE) == AL10.AL_PLAYING;
	}
}

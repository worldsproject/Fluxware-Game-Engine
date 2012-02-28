package sound;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;

public class Sound 
{
	/** Buffers hold sound data. */
	public static IntBuffer buffer = BufferUtils.createIntBuffer(1);

	/** Sources are points emitting sound. */
	IntBuffer source = BufferUtils.createIntBuffer(1);
	
	/** Buffer for Streaming files */
	private ByteBuffer dataBuffer[] = new ByteBuffer[3];

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
	
	SoundLoader sound;

	public Sound(String path) throws IOException
	{
		for(int i = 0; i < dataBuffer.length; i++)
		{
			dataBuffer[i] = ByteBuffer.allocateDirect(4096);
		}
		
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
			sound = new WaveLoader(path);
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
		
		continuePlaying();
	}
	
	private void continuePlaying()
	{	
		while(sound.readCorrectly() && sound.read(dataBuffer[0]) != -1)
		{
			AL10.alBufferData(buffer.get(0), sound.getFormat(), dataBuffer[0], sound.getSampleRate());
		}
	}

	private void checkError()
	{
		int error = 0;
		
		if((error = AL10.alGetError()) != AL10.AL_NO_ERROR)
			System.err.println(error);
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

		Sound one = new Sound("/tests/resources/sounds/boo16.wav");
		one.play();

		while(one.isPlaying())
		{
			System.out.println("Playing");
		}
		
		one.killALData();

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
	
	private class Streamer extends Thread
	{
		SoundLoader sl;
		
		public Streamer(SoundLoader sl)
		{
			this.sl = sl;
		}
		
		public void run()
		{
			
		}
	}
}

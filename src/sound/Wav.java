package sound;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Wav extends Thread implements LineListener
{

	private AudioInputStream inputStream;
	private Clip clip;
	private boolean soundFinished;

	public enum Measurement{SECONDS, FRAMES} 

	/**
	 * Initializes the clip and inputstream and starts the thread;
	 * @param file - Location of wav file. 
	 */
	public Wav(String file)
	{
		getClip();
		getAudioInputStream(new File(file));
		openInputStream();
		clip.addLineListener(this);
		this.start();
	}

	public void run()
	{
		while(!soundFinished){}

		try {
			clip.close();
			inputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		clip = null;
		inputStream = null;
	}

	/**
	 * The wav file is stopped and is reset to resume playing from the beginning of the file.
	 */
	public void stopPlayback()
	{
		pausePlayback();
		clip.setFramePosition(0);
	}

	/**
	 * Seeks forward to a designated frame and continues playing from that position. 
	 * @param framePosition - Frame position to seek to. 
	 */
	public void seek(int position, Measurement m)
	{
		if(m == Measurement.SECONDS)
		{
			clip.setFramePosition(clip.getFrameLength()/position);
		}
		else{
			clip.setFramePosition(position);
		}

		play();
	}


	/**
	 * The wav file pauses at its current frame position. 
	 * If file is already paused, nothing happens.
	 */
	public void pausePlayback()
	{
		if(clip!= null && clip.isOpen() && clip.isRunning())
		{
			clip.stop();
		}
	}

	/**
	 * Plays the wav file if the clip. 
	 * If the clip is null, does not contain an audio stream, or is running
	 * nothing will happen.
	 */
	public void play()
	{
		if(clip!= null && clip.isOpen() && !clip.isRunning())
		{
			clip.start();
		}
	}

	/**
	 * Loops through the wav file a specific number of times
	 * @param count - number of times to loop the wav file
	 */
	public void loop(int count)
	{
		if(clip!= null && clip.isOpen() && !clip.isRunning())
		{
			clip.loop(count);
		}
	}

	/**
	 * 
	 * @param count
	 * @param start
	 * @param end
	 * @param m
	 */
	public void loop(int count, int start, int end, Measurement m)
	{
		if(clip!= null && clip.isOpen() && !clip.isRunning())
		{
			if(m == Measurement.SECONDS)
			{
				float frames = clip.getFormat().getFrameRate();
				start = (int)((start != 0) ? frames*start : 0);
				end = (int)((end != 0) ? frames*end : -1);
				clip.setLoopPoints(start, end);
			}
			else
			{
				clip.setLoopPoints(start, end);
			}
			clip.loop(count);
		}
	}
	
	/**
	 * Returns a boolean determining if the sound has finished playback.
	 * @return - True if the song is at the end frame position and stop event has been thrown. 
	 */
	public boolean soundFinished()
	{
		return soundFinished;
	}

	private void getAudioInputStream(File file)
	{
		try
		{
			inputStream = AudioSystem.getAudioInputStream(file);
		}
		catch(UnsupportedAudioFileException el)
		{
			el.printStackTrace();
		}
		catch(IOException el)
		{
			el.printStackTrace();
		}
	}

	private void getClip()
	{
		try 
		{
			clip = AudioSystem.getClip();
		} 
		catch (LineUnavailableException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void openInputStream()
	{
		if(clip!=null && inputStream !=null)
		{
			try
			{
				clip.open(inputStream);
			} 
			catch (LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void update(LineEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getType() == LineEvent.Type.STOP)
		{
			if(clip.getFramePosition()==clip.getFrameLength())
			{
				clip.setFramePosition(0);
				soundFinished = true;
			}
		}

	}

}

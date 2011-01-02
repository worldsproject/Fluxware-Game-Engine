package sound;

import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Supports the playback of wave files. 
 * If a "unsupported format" problem arises,
 * use an external program (such as audacity)
 * to export the song in the following format:
 * Compression: Uncompressed PCM
 * Samplerate: 44100Hz
 * Bit Depth: 16
 * 
 * Also has trouble playing 24 bit with
 * pulseaudio on default configuration.
 * Although it is not recommended,
 * disabling pulseaudio withe following
 * command may solve the problem
 * 
 * killall -9 pulseaudio
 * 
 * @author cj
 *
 */
public class Sound implements LineListener, Runnable
{
	protected AudioInputStream inputStream;
	protected AudioFormat format;
	protected long position, leftFrame, rightFrame;
	protected int frameSize;
	protected SourceDataLine line;
	protected byte[] data;
	protected int bytesRead, bytesWritten;
	protected boolean paused,close,loop;
	protected String file;


	public Sound(){}

	public Sound(String file)
	{
		this.file = file;
	}

	public void run()
	{
		openInputStream();
		getLine();
		line.start();
		if(line!=null)
		{
			frameSize = line.getFormat().getFrameSize();
			data = new byte[1024*frameSize];

			while(!close)
			{
				try {

					if(line.isActive())
					{
						if((bytesRead = inputStream.read(data, 0, data.length)) != -1)
						{
							line.write(data, 0, bytesRead);
							position = line.getLongFramePosition();
						}
					}
					else if(paused)
					{
						try {

							Thread.sleep(0,1);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				} 
			}
			
			try {
				inputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			line.flush();
			line.close();

			inputStream = null;
			line = null;
			
			if(loop)
			{
				close = false;
				new Thread(this).start();
			}

		}
	}

	/**
	 * Starts playback of the file.
	 */
	public void play()
	{		
		if(paused)
		{
			paused = false;
		}
		else
		{
			new Thread(this).start();
		}
	}

	/**
	 * Stops playback of the file. 
	 * SourceDataLine is closed when called, 
	 * also terminating the thread.
	 */
	public void stop()
	{
		if(line != null)
		{
			line.stop();
		}
	}

	/**
	 * 
	 */
	public void stopLoop()
	{
		loop = false;
		stop();
	}

	/**
	 * Pauses playback of the file.
	 */
	public void pause()
	{
		if(line != null)
		{
			paused = true;
			line.stop();
		}
	}

	/**
	 * 
	 */
	public void loop()
	{
		loop = true;
		play();
	}

	protected void getLine()
	{
		for(Mixer.Info mixerInfo: AudioSystem.getMixerInfo())
		{
			for(Line.Info lineInfo: AudioSystem.getMixer(mixerInfo).getSourceLineInfo())
			{
				try
				{
					if(lineInfo instanceof SourceDataLine.Info)
					{
						if(((SourceDataLine.Info) lineInfo).isFormatSupported(inputStream.getFormat()))
						{
							line = (SourceDataLine)AudioSystem.getMixer(mixerInfo).getLine(lineInfo);
							line.addLineListener(this);
							line.open(inputStream.getFormat(), inputStream.getFormat().getFrameSize()*1024*20);
						}
					}
				}

				catch(Exception ex)
				{
					line = null;
					continue;
				}
				break;
			}
			if(line!=null)
			{
				break;
			}
		}
	}

	protected void openInputStream()
	{
		try {
			inputStream = AudioSystem.getAudioInputStream(Sound.class.getResource(file));

			if(file.endsWith(".ogg") || file.endsWith(".mp3"))
			{				
				AudioFormat baseFormat = inputStream.getFormat();

				AudioFormat decodedFormat = new AudioFormat(
						AudioFormat.Encoding.PCM_SIGNED,
						baseFormat.getSampleRate(),
						16,
						baseFormat.getChannels(),
						baseFormat.getChannels()*2,
						baseFormat.getSampleRate(),
						false);

				inputStream = AudioSystem.getAudioInputStream(decodedFormat, inputStream);
			}
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	@Override
	public void update(LineEvent event) {
		// TODO Auto-generated method stub

		System.out.println(event.getType());

		if(event.getType() == LineEvent.Type.STOP)
		{
			if(!paused)
			{
				close = true;
			}
		}
	}
}


package sound;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.LineEvent.Type;

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
public class Wav extends Thread implements LineListener
{
	protected AudioInputStream inputStream;
	protected long position, leftFrame, rightFrame;
	protected SourceDataLine line;
	protected byte[] data;
	protected int bytesRead, bytesWritten;
	protected boolean paused,close;
	protected String file;

	public Wav(){}

	public Wav(String file)
	{
		this.file = file;
		super.setPriority(MIN_PRIORITY);
		openInputStream();
		getLine();

	}

	public void run()
	{
		if(line!=null)
		{
			while(!close)
			{
				try {
					data = new byte[1024*line.getFormat().getFrameSize()];
					if((bytesRead = inputStream.read(data, 0, data.length)) != -1)
					{
						line.write(data, 0, bytesRead);
					}
					else if(paused)
					{
						System.out.println("hello");
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

			line.flush();
			line.close();
		}

		try {
			inputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**
	 * Starts playback of the file.
	 */
	public void play()
	{
		if(line!=null)
		{
			line.start();
		}
	}

	/**
	 * Stops playback of the file. 
	 * SourceDataLine is closed when called, 
	 * also terminating the thread.
	 */
	public void stopPlayback()
	{
		if(line != null)
		{
			line.stop();
		}
	}

	/**
	 * Pauses playback of the file.
	 */
	public void pausePlayback()
	{
		if(line != null)
		{
			paused = true;
			line.stop();
		}
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
			inputStream = AudioSystem.getAudioInputStream(new File(file));
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void update(LineEvent event) {
		// TODO Auto-generated method stub
		System.out.println(event.getType());
		if(event.getType() == LineEvent.Type.OPEN)
		{
			start();
		}
		else if(event.getType() == LineEvent.Type.START)
		{
			paused = false;
		}
		else if(event.getType() == LineEvent.Type.STOP)
		{
			if(!paused)
			{
				close = true;
			}
		}
	}

}

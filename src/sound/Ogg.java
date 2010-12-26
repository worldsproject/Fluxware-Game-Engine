package sound;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Ogg extends Wav{

	public Ogg(String file)
	{
		super();
		try {
			inputStream = AudioSystem.getAudioInputStream(new File(file));
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		getLine();
		super.setPriority(MIN_PRIORITY);
		setPriority(MIN_PRIORITY);
		
	}

}

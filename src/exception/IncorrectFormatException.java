package exception;

public class IncorrectFormatException extends Exception
{
	public static final int NOT_A_RIFF = 0;
	public static final int INCORRECT_FILESIZE = 1;
	public static final int NOT_A_WAVE = 2;
	public static final int DOES_NOT_HAVE_FMT = 3;
	public static final int SUBCHUNK_SIZE_INCORRECT = 4;
	public static final int COMPRESSION_DETECTED = 5;
	public static final int DATA_NOT_FOUND = 6;
	public static final int UNSUPPORTED_SAMPLE_SIZE = 7;
	public static final int UNSUPPORTED_CHANNEL_NUMBER = 8;
	
	public IncorrectFormatException(int type)
	{
		switch(type)
		{
			case 0: System.err.println("The .wav files header is not RIFF."); break;
			case 1: System.err.println("The .wav files size is reported incorrectly."); break;
			case 2: System.err.println("The format of the .wav file is not WAVE"); break;
			case 3: System.err.println("The .wav header does not have fmt "); break;
			case 4: System.err.println("Subchunk size indicates it is not PCM"); break;
			case 5: System.err.println("Compressed .wav files are not supported."); break;
			case 6: System.err.println("The value 'data' has not been found in the .wav header"); break;
			case 7: System.err.println("Currently only supports 8 and 16 bit sample sizes."); break;
			case 8: System.err.println("Currently only support 1 or 2 channels."); break;
		}
	}
}

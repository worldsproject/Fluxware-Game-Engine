package sound;

import java.nio.ByteBuffer;


public abstract class SoundLoader
{
	public abstract int read(ByteBuffer buffer);
	public abstract int getFormat();
	public abstract int getSampleRate();
	public abstract boolean readCorrectly();
}

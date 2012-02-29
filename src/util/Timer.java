package util;

/**
 * The timer is a millisecond resolution timer.
 * 
 * @author tbutram
 */
public class Timer 
{
	long previousTime;
	long tickTime;
	long stopDelay;
	boolean timerStopped;

	/**
	 * Creates a new Timer with a alarm that goes off every <i>tick</i> milliseconds.
	 * 
	 * @param tick - The length between alarms.
	 */
	public Timer(long tick)
	{
		previousTime = System.currentTimeMillis();
		tickTime = tick;
	}

	/**
	 * Resets the elapsed time to 0.
	 */
	public void reset()
	{
		previousTime = System.currentTimeMillis();
	}

	/**
	 * Changes the tick time.
	 * 
	 * @param tick - The new tick time for the alarm.
	 */
	public void setTickTime(long tick)
	{
		tickTime = tick;
	}

	/**
	 * Checks to see if the alarm has rung.
	 * 
	 * @return - true if the alarm has rung, false if otherwise.
	 */
	public boolean hasRung()
	{
		if(!timerStopped)
		{
			if((System.currentTimeMillis() - stopDelay - previousTime)> tickTime)
			{
				reset();
				stopDelay = 0;
				return true;
			}
			else
			{
				stopDelay = 0;
				return false;
			}	
		}
		return false;
	}

	/**
	 * Stops the class from checking if the timer has rung
	 */
	public void stop()
	{
		timerStopped = true;
	}
	
	/**
	 * Allows the class to check if the timer has rung and accounts
	 * for delay between stop and start time.
	 */
	public void start()
	{
		timerStopped = false;
		stopDelay += System.currentTimeMillis() - previousTime;
	}
}

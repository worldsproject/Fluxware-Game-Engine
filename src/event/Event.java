package event;

public class Event 
{

	protected Object source;
	
	/**
	 * Blank Event, with a null Source.
	 */
	public Event()
	{
		source = null;
	}
	
	/**
	 * Creates an Event with a given source.
	 * @param source - The source of the Event.
	 */
	public Event(Object source)
	{
		this.source = source;
	}
	
	/**
	 * Gets the source Object.
	 * @return The source Object.
	 */
	public Object getSource()
	{
		return source;
	}
	
	/**
	 * Sets the souce of the Event.
	 * @param source - The new source of the Event.
	 */
	public void setSource(Object source)
	{
		this.source = source;
	}
}

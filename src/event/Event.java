package event;

public class Event 
{

	protected Object source;
	
	public Event()
	{
		source = null;
	}
	
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
	
	public void setSource(Object source)
	{
		this.source = source;
	}
}

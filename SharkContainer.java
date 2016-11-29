public class SharkContainer	{
	
	private String time;
	private String sharkName;
	
	public SharkContainer (String x,String y) 
	{
		time = x;
		sharkName = y;
	}
	
	
	/**
	 * @return the time
	 */
	public String getTime () 
	{
		return time;
	}
	
	
	/**
	 * @return the shark's name
	 */
	public String getSharkName () 
	{
		return sharkName;
	}
	
	/**
	 * Boolean method that returns true if the sharks' names are the same.
	 */
	public boolean equals (Object s) 
	{
		if (sharkName.equals(((SharkContainer)s).getSharkName())) 
		{
			return true;
		}
		else 
		{
			return false;
		}
	}
}
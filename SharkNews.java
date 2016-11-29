import java.util.ArrayList;
import api.jaws.Jaws;

public class SharkNews {

	private Jaws jawsAPI;
	private SearchResults sr;
	ArrayList <SharkContainer> scList;
	
	public SharkNews()
	{
		jawsAPI = new Jaws ("dZsoDoLjG9TfEzRn","MSY2KotwQPji1138");
		sr = new SearchResults();
		scList = sr.getContainer();
	}
	
	/**
	 * If the specified name is of a shark, that has been tracked during the past month,
	 * get its last ping time.
	 * @param s
	 * @return last ping
	 */
	public String getNews(String s) 
	{
		String news = "Sorry, we haven't seen " + s + " the past month.";
		sr.getSharks(jawsAPI.pastMonth());
		
		for (SharkContainer sc : scList)
		{
			if (s.equals(sc.getSharkName()))
			{
				news = "The last time we saw " + sc.getSharkName() + " was " + sc.getTime() + ".";
			}
		}
		return news;
	}
	
}

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import api.jaws.Jaws;
import api.jaws.Ping;
import api.jaws.Shark;

public class SearchResults {

	private HashMap<String,String> dropboxSelections;
	private ArrayList<SharkContainer> sharkContainer;
	private ArrayList<SharkContainer> removedSharks;
	private Jaws jawsAPI;
	
	
	public SearchResults() 
	{
		dropboxSelections = new HashMap<String,String>();
		sharkContainer = new ArrayList<SharkContainer>();
		removedSharks = new ArrayList<SharkContainer>();
		jawsAPI = new Jaws ("dZsoDoLjG9TfEzRn","MSY2KotwQPji1138");
		dropboxSelections.put("Tracking range", "Last 24 Hours");
		dropboxSelections.put("Stage of Life", "All");
		dropboxSelections.put("Gender", "All");
		dropboxSelections.put("Tag Location", "All");	
	}
	

	/**
	 * Adds combo box selections in a Hash map.
	 * @param a
	 * @param b
	 */
	public void addSelection (String a,String b) 
	{
		dropboxSelections.put(a,b);
	}
	
	
	/**
	 * Filters the sharks based on the filtering options.
	 */
	public void filterResults () 
	{
		sharkContainer.clear();
		removedSharks.clear();
		filterTrackingRange();
		filterGender();
		filterStageOfLife();
		filterTagLocation();
		for (SharkContainer sc : removedSharks)
		{
			sharkContainer.remove(sc);
		}
	}
	/**
	 * Gets the sharks according to the tracking range selection.
	 */
	public void filterTrackingRange() 
	{	
		String range = dropboxSelections.get("Tracking range");
		ArrayList<Ping> pingsList=null;
		
		if (range.equals("Last 24 Hours"))
		{
			pingsList = jawsAPI.past24Hours();
		}	
		
		else if (range.equals("Last Week"))
		{
			pingsList = jawsAPI.pastWeek();
		}
		
		else if (range.equals("Last Month"))
		{
			pingsList = jawsAPI.pastMonth();
		}
		getSharks(pingsList);

	}

	
	/**
	 * Filters the sharks by their gender.
	 */
	public void filterGender() 
	{
		if (!(dropboxSelections.get("Gender").equals("All")))
		{
			for (SharkContainer s : sharkContainer) 
			{
				Shark desiredShark = jawsAPI.getShark(s.getSharkName());
				if (!(desiredShark.getGender().equals(dropboxSelections.get("Gender"))))
				{
					removedSharks.add(s);
				}
			}
		}
	}
	
	/**
	 * Filters the sharks by their stage of life.
	 */
	public void filterStageOfLife() 
	{
		if (!(dropboxSelections.get("Stage of Life").equals("All")))
		{
			for (SharkContainer s : sharkContainer)
			{
				Shark desiredShark = jawsAPI.getShark(s.getSharkName());
				if (!(desiredShark.getStageOfLife().equals(dropboxSelections.get("Stage of Life"))))
				{
					removedSharks.add(s);
				}
			}
		}
	}
	
	
	/**
	 * Filters the sharks by their tag location.
	 */
	public void filterTagLocation() 
	{
		if (!(dropboxSelections.get("Tag Location").equals("All")))
		{
			for (SharkContainer s : sharkContainer)
			{
				Shark desiredShark = jawsAPI.getShark(s.getSharkName());
				if (!(desiredShark.getTagLocation().equals(dropboxSelections.get("Tag Location"))))
				{
					removedSharks.add(s);
				}
			}
		}	
	}

	/**
	 * Sorts the pings by their date.
	 * @param pingList
	 */
	public void sortPings(ArrayList<Ping> pingList)
	{
		Collections.sort(pingList,new Comparator<Ping>(){
				@Override
				public int compare(Ping p1, Ping p2) {
					return p2.getTime().compareTo(p1.getTime());
				}
			});
	}
	
	
	
	/**
	 * Puts sharks and their pings in an array list.
	 * @param pingsList
	 */
	public void getSharks(ArrayList<Ping> pingsList) 
	{
		sortPings(pingsList);
		for (Ping p:pingsList)
		{
			SharkContainer s = new SharkContainer (p.getTime(),p.getName());
			// if array list contains shark container with same shark name,don't add it to array list 
			if (!(sharkContainer.contains(s)))
			{
				sharkContainer.add(s);
			}
		}
	}
		
	
	/**
	 * @return the shark container array list
	 */
	public ArrayList<SharkContainer> getContainer () 
	{
		return sharkContainer;
	}

}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class Sharknado {
	
	/**
	 * 
	 * takes the location of a shark in String format, uses the google geocode api to check
	 * if this location is on land or not.
	 * returns a String, checked in Followed Class
	 * 
	 * @param lat
	 * @param lon
	 * @return
	 * @throws IOException
	 */
	
	public static String getLocat(String lat, String lon) throws IOException{
				
		  String s = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + lat + "," + lon + "&result_type" + "&key=AIzaSyAPUxdBNSbOZACdwq26JShc5NeM_254sUk";
		  URL google = new URL(s);
	      BufferedReader in = new BufferedReader(
	      new InputStreamReader(google.openStream()));
	      String inputLine;
	      ArrayList<String> lines = new ArrayList();
	      while ((inputLine = in.readLine()) != null)
	      lines.add(inputLine);
	      String x = lines.get(2);
	      lines.clear();
	      in.close();
	      return x;
	      	
	}

}
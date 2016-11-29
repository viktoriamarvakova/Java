import java.net.MalformedURLException;

import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import api.jaws.Jaws;
import api.jaws.Location;

public class SharkMap extends JFrame {
	
	private Jaws jawsAPI = new Jaws ("dZsoDoLjG9TfEzRn","MSY2KotwQPji1138");;
	
	/**
	 * Uses Google Static Maps api
	 * Takes 3 Strings, Shark name + String representation of location
	 * Creates a Frame containing a World Map with a marker showing the sharks location
	 * 
	 * @param Name
	 * @param lat
	 * @param lon
	 * @throws MalformedURLException
	 */
	public SharkMap(String Name, String lat, String lon) throws MalformedURLException{
		
		super(Name + " is currently here!");
		String imageUrl = "https://maps.googleapis.com/maps/api/staticmap?maptype=satellite&center=" + lat + "," + lon + "&markers=color:red%7Clabel:G%7C" + lat + "," + lon + "&zoom=3&size=1080x720&key=AIzaSyDR0y3LKBdUQ1PUovsgLs_VW5GpL3LqAG8";
	    URL url = new URL(imageUrl);
	    add( new JLabel(new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(630, 600, java.awt.Image.SCALE_SMOOTH))));
	    setVisible(true);
	    pack();
		setSize(630,500);
	}
	
	/**
	 * Uses Google Static Maps api
	 * Takes an ArrayList of Strings (Shark Names), finds their locations
	 * creates the google static maps code to position a marker with the sharks location
	 * adds markers until all sharks in the array have been added
	 * Creates a frame containing a world map with all these markers added
	 * 
	 * @param names
	 * @throws MalformedURLException
	 */
	
	SharkMap(ArrayList<String> names) throws MalformedURLException{
		
		super("Your sharks are currently here!");
		
		ArrayList<String> name = new ArrayList<>();
		for (int i = 0; i < names.size(); i++){
			Location l = jawsAPI.getLastLocation(names.get(i));
			String c = String.valueOf(names.get(i).charAt(0));
			String la = Double.toString(l.getLatitude());
			String lo = Double.toString(l.getLongitude());
			String marker = "&markers=color:red%7Clabel:" + c + "%7C" + la + "," + lo;
			name.add(marker);
		}
		String n = "";
		for(int j = 0; j < name.size(); j++){
			n = n + name.get(j);
		}
		
		String imageUrl = "https://maps.googleapis.com/maps/api/staticmap?maptype=satellite&center=0,0" + n + "&zoom=1&size=1080x720&key=AIzaSyDR0y3LKBdUQ1PUovsgLs_VW5GpL3LqAG8";
		URL url = new URL(imageUrl);
	    add( new JLabel(new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(630, 600, java.awt.Image.SCALE_SMOOTH))));
	    setVisible(true);
	    pack();
		setSize(630,500);
		
	}

}

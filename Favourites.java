import java.awt.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import javax.swing.JButton;


import api.jaws.Jaws;
import api.jaws.Location;
import api.jaws.Shark;

public class Favourites {						//adding values only visible for this class

	UserManager usermanager;
    private Jaws jawsAPI = new Jaws ("dZsoDoLjG9TfEzRn","MSY2KotwQPji1138");
	private Location d ;
	private static double kingslat = 51.4699;
	private static double kingslong = -.0891;
	private double newDist;
	private String fileName = "data/temp.txt";

    public Favourites(UserManager usermanager) {
		this.usermanager = usermanager;
		fileName = usermanager.getCurrentUserProfile().getName();
		//fileName = usermanager.getCurrentUserProfile().getName();
	}


	public void followShark(JButton followBtn, Shark s){			//method follow shark
    	
    	String text = followBtn.getText();
		d = jawsAPI.getLastLocation(s.getName());
		Math.round(newDist);
		Math.round(kingslat);
		Math.round(kingslong);
		newDist = Double.valueOf((distance(d.getLatitude(),d.getLongitude(), kingslat, kingslong)));

		   ArrayList<Double> grades = new ArrayList<Double>(); 
			  grades.add(newDist);  
			  Collections.sort(grades);			//ordering the array in ascending order
			     Collections.sort(grades, Collections.reverseOrder());
	
        if (text.equals("Follow")) {  	
   
	try {
		

	   PrintWriter outputStream = new PrintWriter(new FileWriter(fileName,true));
		 
	outputStream.println(s.getName()  + ": " + grades + " Nautical Miles");		
		outputStream.close(); 


	BufferedReader reader = new BufferedReader(new FileReader(fileName));
	Set<String> lines = new HashSet<String>(10000); 		//readinf the file and then creating a new one without the duplicates
	String line;
	while ((line = reader.readLine()) != null) {
	    lines.add(line);
	}

	BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
	for (String unique : lines) {
	    writer.write(unique);
	    writer.newLine();
	}


	writer.close();
	reader.close(); //keep it till now

	}
	
	catch(IOException ex) {
	System.out.println(
	    "Error writing file '"
	    + fileName + "'");
	}		
      
			
	Scanner scan = new Scanner (fileName);			//readin through the file and 
	while (scan.hasNextLine()) {
        String liner = scan.nextLine();
        
        if(liner.equals(s.getName() + "  : "  + newDist)) { 
            followBtn.setText("Followed");}
    }
	
	scan.close();	
	followBtn.setText("Followed");

        }
       
            
 if(text.equals("Followed")){

		removeLineFromFile(fileName, s.getName() + "  : "  + newDist);			//delete the line if it is unfollowed
	  followBtn.setText("Follow");}}


    public void removeLineFromFile(String file, String lineToRemove) {
    try {
 
      File inFile = new File(file);
      
      if (!inFile.isFile()) {
        System.out.println("Parameter is not an existing file");
        return;
      }
       
      //Construct the new file that will later be renamed to the original filename. 
      File tempFile = new File(inFile.getAbsolutePath() + ".tmp");
      
      BufferedReader br = new BufferedReader(new FileReader(file));
      PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
      
      String line = null;
 
      //Read from the original file and write to the new 
      //unless content matches data to be removed.
      while ((line = br.readLine()) != null) {
        
        if (!line.trim().equals(lineToRemove)) {
 
          pw.println(line);
          pw.flush();
        }
      }
      pw.close();
      br.close();
      
      //Delete the original file
      if (!inFile.delete()) {
        System.out.println("Could not delete file");
        return;
      } 
      
      //Rename the new file to the filename the original file had.
      if (!tempFile.renameTo(inFile))
        System.out.println("Could not rename file");
      
    }
    catch (FileNotFoundException ex) {
      ex.printStackTrace();
    }
    catch (IOException ex) {
      ex.printStackTrace();
    }
    }
	      
	private double distance(double lat1, double lon1, double lat2, double lon2) {				//creating a method to calculate distances
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		dist = dist * 0.8684;
	
		return (dist);
	}
	
	static double distance(double lat1, double lon1) {

		double theta = lon1 - kingslong;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(kingslat)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(kingslat)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		dist = dist * 0.8684;
	
		return (dist);
	}
	
	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}
	
	private static double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}
	 
	public String search(Shark s)  {				//creating a method for searching shark using Scanner to read through the txt file
		 String str = null;
		try {
		    File text = new File(fileName);
			 Scanner sc = new Scanner(text);
			
			while (sc.hasNextLine() == true)
			{ String shark = sc.nextLine();  
					if (shark.contains(s.getName())) 
					{
				        str = s.getName();
					}
					
				}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return str;
	
		
	}
		    
}



    

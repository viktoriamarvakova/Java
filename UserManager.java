import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JOptionPane;

public class UserManager {
    File text = new File("data/temp.txt");

	public UserManager(){
		//text = new File("temp.txt");
	}
	
	public File findUser(String name){
	    File favs = new File("data/"+name + ".txt");
	    boolean found = favs.exists();
	    if (found == true){
	    	System.out.println("Found user " + name + ".txt");
	    	return favs;
	    } else {
	    	System.out.println("Didn't find user " + name + ".txt");
	    	return null;
	    }
	}
	
	public void makeUser(String name) throws IOException{
	    File favs = new File("data/"+name + ".txt");
	    if (favs.exists()){
	    	System.out.println("Profile already exists!");
	    	JOptionPane.showMessageDialog(null, "Sorry, a profile with that name already exists!");
	    } else {
			System.out.println("Making userprofile " + name + ".txt");
			favs.createNewFile();
	    }
	}
	
	public void deleteUser(String name) throws IOException{
		if (name.equals("temp")){
			JOptionPane.showMessageDialog(null, "Sorry, you can't delete the default user profile.");
		} else {
			boolean delete = new File("data/"+name + ".txt").delete();
			System.out.println("Deleted file " + name + " is " + delete);
		}
	}
	
	public void deleteAllUsers(){
		File all = new File("data");
		File[] directoryListing = all.listFiles();
		  if (directoryListing != null) {
		    for (File child : directoryListing) {
		      if (child.getName().equals("temp.txt") == false)
		      { 
		    	  child.delete();
		      }
		    }
		  }
	}
	
	public File getDefaultUser(){
		return new File("data/temp.txt");
	}

	public void loadUser(String userInput) {
	    File favs = new File("data/"+userInput + ".txt");
	    boolean found = favs.exists();
	    if (found == true){
	    	System.out.println("Loading user " + userInput + ".txt");
	    	text = favs;
	    } else {
	    	System.out.println("Could not load user!");
	    }
	}

	public void loadDefaultUserProfile() {
		text = new File("data/temp.txt");
	}

	public File getCurrentUserProfile() {
		return text;
	}
}
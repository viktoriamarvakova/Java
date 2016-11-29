import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import api.jaws.Jaws;
import api.jaws.Location;


public class Followed extends JFrame{

	private Jaws jawsAPI = new Jaws ("dZsoDoLjG9TfEzRn","MSY2KotwQPji1138");;
	JPanel main = new JPanel(new BorderLayout());
    JPanel panel = new JPanel();
    JPanel panel1 = new JPanel();
    JLabel label = new JLabel();
    JLabel label1 = new JLabel();
    JButton but = new JButton("Open Shark Map");
    ArrayList<String> name = new ArrayList<>();
	File text;
    
    public Followed(UserManager usermanager){
    	this.text = usermanager.getCurrentUserProfile();
		
        panel.setBounds(800, 800, 200, 100);        

        label.setText("Your favourite sharks are this far from you right now:");
   
        panel.add(label);
        panel.add(label1);
        main.add(panel, BorderLayout.CENTER);
        panel1.add(but);
        main.add(panel1, BorderLayout.SOUTH);
        add(main);
   
        try{
            BufferedReader br = new BufferedReader(new FileReader(text));
           // BufferedWriter writer = new BufferedWriter(new FileWriter(fileName,true));

            String  thisLine = null;
            	      
            	         while ((thisLine = br.readLine()) != null) {
            	            //System.out.println(thisLine);
            	        	 DecimalFormat df = new DecimalFormat();
 	    	    			 df.setMaximumFractionDigits(2);
 	    	    			 Location d = jawsAPI.getLastLocation(thisLine);
 	    	    			 String y = "";
 	    	    			 String x = Sharknado.getLocat(Double.toString(d.getLatitude()), Double.toString(d.getLongitude()));
 	    	    		     if(x.contains("ZERO_RESULTS")){
 	    	    		    	  y = "";
 	    	    		      } else {
 	    	    		    	  y = "Sharknado! Your shark is currently on land!";
 	    	    		      }
 	    	    			 String newDist = df.format(Favourites.distance(d.getLatitude(), d.getLongitude()));
            	        	 String labeltext = thisLine + " - " + newDist + " Nautical Miles" + y;
            	             JLabel label = new JLabel(labeltext);
            	             panel.add(label);
            	             name.add(thisLine);
            	          //  br.close();
            	         }       
            	      }catch(Exception e){
            	         e.printStackTrace();
            	      }
        String fileName = usermanager.getCurrentUserProfile().getName();
        
        try{
            BufferedReader br = new BufferedReader(new FileReader(usermanager.getCurrentUserProfile()));			//reading the text file and adding everything to the JLabel
            
            String  thisLine = null;
            	      
            	         while ((thisLine = br.readLine()) != null) {
            	            System.out.println(thisLine);
            	            
            	            JLabel bla = new JLabel(thisLine);
            	            panel.add(bla);
            	         }       
            	      }catch(Exception e){
            	         e.printStackTrace();
            	      }
        setSize(400, 400);
        setBackground(Color.BLACK);
        setTitle("Favourites");
        setVisible(true);
        
		but.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				
				try {
					SharkMap map = new SharkMap(name);
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				}
				
			}
		});
        
    }
        }


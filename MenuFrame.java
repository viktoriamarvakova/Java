import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import api.jaws.Jaws;
import api.jaws.Location;

public class MenuFrame extends JFrame {
	
	UserManager usermanager;
	private Jaws jawsAPI;
    File text;
    boolean areFaves;
    ArrayList<String> name;

	public MenuFrame() 
	{
		usermanager = new UserManager();
		jawsAPI = new Jaws ("dZsoDoLjG9TfEzRn","MSY2KotwQPji1138");
		text = usermanager.getCurrentUserProfile();
		createWidgets();
		System.out.println("menuconstructor FINAL" + usermanager);
		name = new ArrayList<String>();
	}

	/**
	 * Creates the widgets for the frame.
	 */
	private void createWidgets() 
	{
		System.out.println("createwidgets" + usermanager);
		setTitle("Amnity Police");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel mainPanel = new JPanel(new BorderLayout());
		JPanel btnPanel = new JPanel (new GridLayout(2,1,0,4));
		JLabel sharkImage = new JLabel (new ImageIcon("images/sharkTrackerLogo.png"));
		JButton searchBtn = new JButton ("Search");
		searchBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				setVisible(false);
				System.out.println("searchbtnactionlistener" + usermanager);
				new SearchFrame(usermanager, areFaves).setVisible(true);
			}
		});
		JButton favsBtn = new JButton ("Favourites");
		JButton statBtn = new JButton ("Statistics");
		JButton mapBtn = new JButton ("Shark Map");
		mapBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Trying sharkmap.");
				name.clear();
				text = usermanager.getCurrentUserProfile();
			     try{
			            BufferedReader br = new BufferedReader(new FileReader(text));
			           // BufferedWriter writer = new BufferedWriter(new FileWriter(fileName,true));
			            String  thisLine = null;		            	      
			            	         while ((thisLine = br.readLine()) != null) {
			 	    	    			 Location d = jawsAPI.getLastLocation(thisLine);
			            	             name.add(thisLine);
			            	          //  br.close();
			            	         }       
			            	      }catch(Exception e1){
			            	         e1.printStackTrace();
			            	      }
				try {
					System.out.println("Trying sharkmap.");
					SharkMap map = new SharkMap(name);
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				}
			}
		});
		favsBtn.setEnabled(false);
		btnPanel.add(searchBtn);
		btnPanel.add(favsBtn);
		btnPanel.add(statBtn);
		statBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				StatsFrame frame = new StatsFrame();
				frame.setVisible(true);
			}
		});
		btnPanel.add(mapBtn);
		mainPanel.add(sharkImage,BorderLayout.NORTH);
		mainPanel.add(btnPanel,BorderLayout.CENTER);
	
		getContentPane().add(mainPanel);
		pack();
		setSize(280,300);
		
		Scanner sc;
		try {
			sc = new Scanner(text);
			if (sc.hasNext() == true)
			{
					areFaves = true;
					favsBtn.setEnabled(true);
					favsBtn.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg1)
						{
							Followed fol = new Followed(usermanager);
						}
					});
			}
				
			else 
				
			{
				areFaves = false;
				favsBtn.setEnabled(false);
				favsBtn.addMouseListener(new MouseAdapter() {
				      public void mouseClicked(MouseEvent e) {
				    	//  JDialog.setDefaultLookAndFeelDecorated(true);
				    	    int response = JOptionPane.showConfirmDialog(null, "You're not following any sharks yet. Open Search to find some to follow?", "Favourites",
				    	        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				    	    if (response == JOptionPane.NO_OPTION) {
				    	    	//MenuFrame menu = new MenuFrame();
				    	    	//menu.setVisible(true);
				    	    } else if (response == JOptionPane.YES_OPTION) {
				    	    	SearchFrame search = new SearchFrame(usermanager, false);
				    	    	search.setVisible(true);
				    	    } else if (response == JOptionPane.CLOSED_OPTION) {
				    	      //MenuFrame menu = new MenuFrame();
				    	      //menu.setVisible(true);
				    	    }
				    	  }
				
				      }
			
				
			);
			
	
		}} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

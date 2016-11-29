import javax.swing.Box;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import api.jaws.Jaws;
import api.jaws.Location;
import api.jaws.Shark;


public class SearchFrame extends JFrame {

    UserManager usermanager;
	private Jaws jawsAPI;
	private ArrayList<String> dropboxSelections;
	private SearchResults filteredResults;
	JMenuBar menubar;
	JMenu menu, submenu;
	JMenuItem menuItem;
	JRadioButtonMenuItem rbMenuItem;
    ArrayList<String> name = new ArrayList<>();
    File text;
	private Favourites fav;
	boolean areFaves;
	JMenuItem favsmenubtn;

	JCheckBoxMenuItem cbMenuItem;{};{
	menubar = new JMenuBar();

	//Build the first menu.
	menu = new JMenu("Users");
	menubar.add(menu);

	menuItem = new JMenuItem("Load User Profile by Name");
	menu.add(menuItem);
	menuItem.addActionListener(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			UserPrompt frame;
			try {
				frame = new UserPrompt("Please enter the name of the user profile exactly as it was entered.", usermanager, "LOAD");
				frame.setVisible(true);
				if (frame.userfound == true){
					cbMenuItem.setState(false);
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	});
	
	menuItem = new JMenuItem("Make New User Profile by Name");
	menu.add(menuItem);
	menuItem.addActionListener(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			UserPrompt frame;
			try {
				frame = new UserPrompt("Please enter the name of the user profile you'd like to create.", usermanager, "MAKE");
				frame.setVisible(true);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	});

	//a group of check box menu items
	menu.addSeparator();
	
	cbMenuItem = new JCheckBoxMenuItem("Use Default User Profile");
	cbMenuItem.setState(true);
	cbMenuItem.addActionListener(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			if (cbMenuItem.getState() == false){
				System.out.println("Statechange, no longer use default user file");
				JOptionPane.showMessageDialog(null, "In order to no longer use the default user profile, you will need to load one. If one does not exist, please make one with the 'Make New User Profile by Name' option.");
				UserPrompt frame;
				try {
					frame = new UserPrompt("Please enter the name of an existing profile, exactly as it was entered.",usermanager,"LOAD");
					frame.setVisible(true);
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
					System.out.println("Unable to load the profile.");
					cbMenuItem.setState(true);
				}
			} else if (cbMenuItem.getState() == true){
				System.out.println("Statechange, use default user file ");
				//switch back to temp 
				text = usermanager.getDefaultUser();
				usermanager.loadDefaultUserProfile();
			}
		}
	});
	
	menu.add(cbMenuItem);

	menu.addSeparator();
	
	// SUBMENU USER MGMT.
	submenu = new JMenu("Manage User Profiles");

	menuItem = new JMenuItem("Delete User Profile by Name");
	submenu.add(menuItem);
	menuItem.addActionListener(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			UserPrompt frame;
			try {
				frame = new UserPrompt("Please enter the name of the user profile exactly as it was entered.",usermanager, "DEL");
				frame.setVisible(true);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	});

	menuItem = new JMenuItem("Delete All User Profiles");
	menuItem.addActionListener(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			int response = JOptionPane.showConfirmDialog(null, "Are you sure? This will delete ALL User profiles PERMANENTLY", "User Profile Deletion",
	    	        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	    	    if (response == JOptionPane.YES_OPTION) {
	    	    	usermanager.deleteAllUsers();
	    	    }
		}
	});
	submenu.add(menuItem);	
	menu.add(submenu);

	//FAVOURITES 
	menu = new JMenu("Favourites");
	menubar.add(menu);
	
	favsmenubtn = new JMenuItem("Open Favourites");
/*		if (areFaves == true)
		{
				System.out.println("AreFaves is true");
				favsmenubtn.setEnabled(true);
				favsmenubtn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg1)
					{
						Followed fol = new Followed(usermanager);
					}
				});
		}			
		else if (areFaves == false)			
		{		*/	
			favsmenubtn.setEnabled(false);
			favsmenubtn.addMouseListener(new MouseAdapter() {
			      public void mouseClicked(MouseEvent e) {
			    	  JOptionPane.showMessageDialog(null, "You're not following any sharks yet. Go follow some!");
			    	  }
			      });
	
	menu.add(favsmenubtn);
	
	//STATISTICS
	menu = new JMenu("Statistics");
	menubar.add(menu);
	
	menuItem = new JMenuItem("Open Statistics");
	menuItem.addActionListener(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			StatsFrame frame = new StatsFrame();
			frame.setVisible(true);
		}
	});
	menu.add(menuItem);
	
	//SHARK MAP
	menu = new JMenu("Shark Map");
	menubar.add(menu);
	
	menuItem = new JMenuItem("Open Shark Map View");
	menuItem.addActionListener(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
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
				SharkMap map = new SharkMap(name);
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
		}
	});
	menu.add(menuItem);
	
	//ACKNOWLEDGEMENTS 
	menu = new JMenu("About");
	menubar.add(menu);
	
	menuItem = new JMenuItem("Acknowledgements");
	menu.add(menuItem);
	menuItem.addActionListener(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			AckFrame frame = new AckFrame();
			frame.setVisible(true);
		}
	});
	this.setJMenuBar(menubar);}
	
/*	public SearchFrame(UserManager usermanager) 
	{
		this.usermanager = usermanager;
		text  = usermanager.getCurrentUserProfile();
		jawsAPI = new Jaws ("dZsoDoLjG9TfEzRn","MSY2KotwQPji1138");
		filteredResults = new SearchResults();
		createWidgets();
	}*/

	public SearchFrame(UserManager usermanager, boolean areFaves) {
		if (areFaves == true){
			enableFavesMenuButton();
		}
		this.usermanager = usermanager;
		text = usermanager.getCurrentUserProfile();
		fav = new Favourites(usermanager); 
		jawsAPI = new Jaws ("dZsoDoLjG9TfEzRn","MSY2KotwQPji1138");
		filteredResults = new SearchResults();
		createWidgets();
	}

	private void enableFavesMenuButton(){
		favsmenubtn.setEnabled(true);
		favsmenubtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg1)
			{
				Followed fol = new Followed(usermanager);
			}
		});
	}

	/**
	 * Creates the widgets for the frame.
	 */
	private void createWidgets() {

		setTitle("Search");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel mainPanel = new JPanel (new BorderLayout(20,0));
		mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
	
		JPanel options = new JPanel(new BorderLayout(0,20));
		JPanel searchResults = new JPanel();
		BoxLayout boxLayout = new BoxLayout(searchResults,BoxLayout.Y_AXIS);
		searchResults.setLayout(boxLayout);
		
		JScrollPane scrollPane = new JScrollPane(searchResults,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		mainPanel.add(scrollPane,BorderLayout.CENTER);
		mainPanel.add(options,BorderLayout.WEST);
		
		JLabel acknowledgement = new JLabel (jawsAPI.getAcknowledgement());
		mainPanel.add(acknowledgement,BorderLayout.SOUTH);

		JPanel optionsContent = new JPanel(new GridLayout(4,1,0,10));
		JPanel optionsImage = new JPanel(new BorderLayout());
		JPanel northPanel = new JPanel (new GridLayout(1,2));
		northPanel.add(new JLabel ("Shark Tracker"));
		JLabel sharkNews1 = new JLabel(new ImageIcon("images/news1.png"));
		JLabel sharkNews2 = new JLabel (new ImageIcon("images/news2.png"));
		sharkNews2.setToolTipText("Click me to receive news for selected sharks.");
		
		northPanel.add(sharkNews1);
		
		sharkNews2.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				new SharkNewsFrame().setVisible(true);
			}
			@Override
			public void mousePressed(MouseEvent e) {
			}
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			
				
			}
			@Override
			public void mouseExited(MouseEvent e) {
				northPanel.remove(sharkNews2);
				northPanel.add(sharkNews1);
				repaint();
				revalidate();
				
			}});
		
		sharkNews1.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
			@Override
			public void mousePressed(MouseEvent e) {
			}
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				northPanel.remove(sharkNews1);
				northPanel.add(sharkNews2);
				repaint();
				revalidate();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				
			}});
		options.add(northPanel,BorderLayout.NORTH);

		options.add(optionsContent, BorderLayout.CENTER);
		options.add(optionsImage, BorderLayout.SOUTH);
		JLabel image = new JLabel (new ImageIcon ("images/sharkTrackerLogoSmall.png"));
		optionsImage.add(image,BorderLayout.CENTER);
		JLabel sharkOTD = new JLabel (new ImageIcon ("images/SharkOfTheDay1.png"));
		JLabel sharkOTD2 = new JLabel (new ImageIcon("images/SharkOfTheDay2.png"));
		sharkOTD2.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				//setVisible(false);
				new SharkOfTheDayFrame().setVisible(true);
			}
			@Override
			public void mousePressed(MouseEvent e) {
			}
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {
				optionsImage.remove(sharkOTD2);
				optionsImage.add(sharkOTD,BorderLayout.SOUTH);
				repaint();
				revalidate();
			}});
		
		optionsImage.add(sharkOTD,BorderLayout.SOUTH);
		sharkOTD.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
			}
			@Override
			public void mousePressed(MouseEvent e) {
			}
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				optionsImage.remove(sharkOTD);
				optionsImage.add(sharkOTD2,BorderLayout.SOUTH);
				repaint();
				revalidate();
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}});
		
		
		
		
		GridLayout optionLayout = new GridLayout(2,1);
		JPanel trackingRangePanel = new JPanel(optionLayout);
		String [] trackingRangeOptions = {"Last 24 Hours", "Last Week", "Last Month"};
		JComboBox trackingRangeCB = new JComboBox(trackingRangeOptions);
		trackingRangeCB.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String selection = trackingRangeCB.getSelectedItem().toString();
				filteredResults.addSelection("Tracking range",selection);
				
			}});
		JLabel trackingRangeLabel = new JLabel ("Tracking Range");
		trackingRangePanel.add(trackingRangeLabel);
		trackingRangePanel.add(trackingRangeCB);
		optionsContent.add(trackingRangePanel);
		
		JPanel genderPanel = new JPanel(optionLayout);
		String [] genderOptions = {"All","Male","Female"};
		JComboBox genderCB = new JComboBox(genderOptions);
		genderCB.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String selection = genderCB.getSelectedItem().toString();
				filteredResults.addSelection("Gender",selection);
			
			}});
		JLabel genderLabel = new JLabel ("Gender");
		genderPanel.add(genderLabel);
		genderPanel.add(genderCB);
		optionsContent.add(genderPanel);
		
		
		JPanel stageOfLifePanel = new JPanel(optionLayout);
		String [] stageOfLifeOptions = {"All","Mature","Immature","Undetermined"};
		JComboBox stageOfLifeCB = new JComboBox (stageOfLifeOptions);
		stageOfLifeCB.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String selection = stageOfLifeCB.getSelectedItem().toString();
				filteredResults.addSelection("Stage of Life",selection);			
			}});
		JLabel stageOfLifeLabel = new JLabel ("Stage of Life");
		stageOfLifePanel.add(stageOfLifeLabel);
		stageOfLifePanel.add(stageOfLifeCB);
		optionsContent.add(stageOfLifePanel);
		
		JPanel tagLocationPanel = new JPanel(optionLayout);
		ArrayList tagLocations = jawsAPI.getTagLocations();
		tagLocations.add(0, "All");
		JComboBox tagLocationCB = new JComboBox (tagLocations.toArray());
		tagLocationCB.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String selection = tagLocationCB.getSelectedItem().toString();
				filteredResults.addSelection("Tag Location",selection);		
				
			}});
		JLabel tagLocationLabel = new JLabel("Tag Location");
		tagLocationPanel.add(tagLocationLabel);
		tagLocationPanel.add(tagLocationCB);
		optionsContent.add(tagLocationPanel);
		

		
		JButton searchBtn = new JButton ("Search");
		optionsImage.add(searchBtn,BorderLayout.NORTH);
		searchBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				filteredResults.filterResults();
				searchResults.removeAll();
				if (filteredResults.getContainer().size()==0)
				{
					JPanel imagePanel = new JPanel(new BorderLayout());
					imagePanel.add(new JLabel(new ImageIcon("images/NoResultsImage.png")),BorderLayout.CENTER);
					searchResults.add(imagePanel);
				}
				else 
				{
					//populates the results pane with shark entries
					for (SharkContainer shark : filteredResults.getContainer()) 
					{
						Shark s = jawsAPI.getShark(shark.getSharkName());
						JPanel sharkInformation = new JPanel (new BorderLayout(0,10));
						JPanel sharkSpecs = new JPanel (new GridLayout(6,2));
						sharkSpecs.add(new JLabel ("Name"));
						sharkSpecs.add(new JLabel (s.getName()));
						sharkSpecs.add(new JLabel ("Gender"));
						sharkSpecs.add(new JLabel (s.getGender()));
						sharkSpecs.add(new JLabel ("Stage of Life"));
						sharkSpecs.add(new JLabel (s.getStageOfLife()));
						sharkSpecs.add(new JLabel ("Species"));
						sharkSpecs.add(new JLabel (s.getSpecies()));
						sharkSpecs.add(new JLabel ("Length"));
						sharkSpecs.add(new JLabel (s.getLength()));
						sharkSpecs.add(new JLabel ("Weight"));
						sharkSpecs.add(new JLabel (s.getWeight()));
						sharkInformation.add(sharkSpecs,BorderLayout.NORTH);
						JPanel sharkDescription = new JPanel (new GridLayout(2,1,10,0));
						sharkDescription.add(new JLabel ("Description"));
						JTextArea sharkDescriptionText = new JTextArea(s.getDescription());
						sharkDescriptionText.setWrapStyleWord(true);
						sharkDescriptionText.setLineWrap(true);
						sharkDescriptionText.setEditable(false);
						sharkDescriptionText.setFocusable(false);
						sharkDescriptionText.setOpaque(false);
						sharkDescription.add(sharkDescriptionText);
						sharkInformation.add(sharkDescription,BorderLayout.CENTER);
						JPanel sharkLastPing = new JPanel(new BorderLayout());
						sharkLastPing.add(new JLabel ("Last ping: " + shark.getTime()));
						JButton followBtn = new JButton ("Follow");
						sharkLastPing.add(followBtn,BorderLayout.EAST);
						sharkInformation.add(sharkLastPing,BorderLayout.SOUTH);
						searchResults.add(sharkInformation,BorderLayout.WEST);
						searchResults.add(Box.createRigidArea(new Dimension(0,5)));
						searchResults.add(new JSeparator());
						followBtn.addActionListener(new ActionListener(){
							
							public void actionPerformed(ActionEvent e){
								
								fav.followShark(followBtn, s);
								
							}
						});
						
					}
				}	
					mainPanel.repaint();
					mainPanel.revalidate();
					
				}});
		

		
		getContentPane().add(mainPanel);
		pack();
		setSize(970,630);
		
		
	}

}
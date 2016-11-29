import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import api.jaws.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class StatsFrame extends JFrame {

	private JPanel contentPane;
	private Jaws jawsAPI;
	private Container panel;
	SearchResults filteredResults;
	ArrayList<String> resultsNames = new ArrayList<String>();
	ArrayList<String> resultsSex = new ArrayList<String>();
	ArrayList<String> resultsAge = new ArrayList<String>();
	ArrayList<String> resultsLocation = new ArrayList<String>();

	public StatsFrame() {
		this.setTitle("Shark Statistics");
		filteredResults = new SearchResults();
		jawsAPI = new Jaws ("dZsoDoLjG9TfEzRn","MSY2KotwQPji1138");
		setSize(1800,900);
		getContentPane().setLayout(new BorderLayout(0, 0));
		panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		
		JPanel options = new JPanel(new BorderLayout(0,20));
		getContentPane().add(options, BorderLayout.WEST);
		options.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		JPanel optionsContent = new JPanel(new GridLayout(4,1,0,10));
		JPanel optionsImage = new JPanel(new BorderLayout());
		options.add(new JLabel ("Shark Search"),BorderLayout.NORTH);

		options.add(optionsContent, BorderLayout.CENTER);
		options.add(optionsImage, BorderLayout.SOUTH);
		JLabel image = new JLabel (new ImageIcon ("resources/sharkTrackerLogoSmall.png"));
		optionsImage.add(image,BorderLayout.CENTER);
		
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
		
		JButton searchBtn = new JButton ("Search");
		searchBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				resultsNames.clear();
				resultsSex.clear();
				resultsAge.clear();
				resultsLocation.clear();
				filteredResults.filterResults();
				if (filteredResults.getContainer().size()==0)
				{
					JOptionPane.showMessageDialog(null, "No sharks found with those parameters.");
				}
				else 
				{
					for (SharkContainer shark : filteredResults.getContainer()) 
					{
						Shark s = jawsAPI.getShark(shark.getSharkName());
						System.out.println("Statsearch found: " + s.getName());
						
						resultsNames.add(s.getName());
						resultsSex.add(s.getGender());
						resultsAge.add(s.getStageOfLife());
						resultsLocation.add(s.getTagLocation());					
					}
				}	
				System.out.println("Redrawing Graphs.");
				RedrawGraphs();
				}});
		optionsImage.add(searchBtn,BorderLayout.NORTH);			
	}
	
	private void RedrawGraphs() {
		System.out.println("Redraw Graphs Called.");
		panel.removeAll();
		panel.repaint();
		panel.revalidate();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3);
		panel_3.add(createGenderPanel());
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.add(createLifeStagePanel());
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.add(createTagLocationPanel());	
		
		getContentPane().repaint();
		getContentPane().revalidate();
	}

	private CategoryDataset createSexDataset( ) 
	   {
	    	DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
	    		Integer malenum = 0;
	    		for (int x = 0; x<resultsNames.size(); x++){
	    			if (resultsSex.get(x).equals("Male")){
	    				malenum++;
	    			}
	    		}
	    		Integer femalenum;
	    		femalenum = resultsNames.size() - malenum;
	    		System.out.println(resultsNames);
	    		System.out.println(resultsSex);
	    		System.out.println("Males: "+malenum);
	    		System.out.println("Females: "+femalenum);
        	    dataset.addValue( malenum , "Male" , "Male" );
	    	    dataset.addValue( femalenum , "Female" , "Female" );        
	    	    //TODO FIX THIS REPETITION
	      return dataset;         
	   }
	
	private PieDataset createAgeDataset( ) 
	   {
  	  int mature = 0;
  	  int immature = 0;
  	  int undefined = 0;
	      DefaultPieDataset dataset = new DefaultPieDataset( );
	      for (int x = 0; x < resultsNames.size(); x++){
	    	  if (resultsAge.get(x).equals("Mature")){
	    		  mature++;
	    		  dataset.setValue("Mature", mature);
	    	  } else if (resultsAge.get(x).equals("Immature")){
	    		  immature++;
	    		  dataset.setValue("Immature", immature);	    		  
	    	  } else if (resultsAge.get(x).equals("Undefined")){
	    		  undefined++;
	    		  dataset.setValue("Undefined", undefined);	    		  
	    	  }
	      }
	      return dataset;         
	   }
	
	private CategoryDataset createLocDataset( ) 
	   {
	    	DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
	    		ArrayList<String> locsAdded = new ArrayList<String>();
	    		ArrayList<Integer> atLocNum = new ArrayList<Integer>();
	  	      for (int x = 0; x < resultsNames.size(); x++){
		    	  if (locsAdded.contains(resultsLocation.get(x)) == false){
		    	  locsAdded.add(resultsLocation.get(x));
		    	  atLocNum.add(1);
		    	  } else {
		    		  for (int y = 0; y < locsAdded.size(); y++){
		    			  if (locsAdded.get(y).equals(resultsLocation.get(x))){
		    				  int current = atLocNum.get(y);
		    				  atLocNum.remove(y);
		    				  atLocNum.add(y, current++);
		    			  }
		    		  }
		    	  }
		      }
	  	      for (int n = 0; n < locsAdded.size(); n++){
	  	    	  String location = locsAdded.get(n);
	  	    	  int number = atLocNum.get(n);
	  	    	  dataset.addValue(number, location, "Shark");
	  	    	  //TODO FIX THIS "Shark"
	  	      }
	      return dataset;         
	   }
	
	   private JFreeChart createSexChart( CategoryDataset dataset )
	   {
		   JFreeChart barChart = ChartFactory.createBarChart(
			         "Genders of Selected Sharks",           
			         "Category",            
			         "Score",            
			         createSexDataset(),          
			         PlotOrientation.VERTICAL,           
			         true, true, false);
			return barChart;
	   }
	   
	   private JFreeChart createAgeChart( PieDataset dataset )
	   {
	      JFreeChart chart = ChartFactory.createPieChart(      
	         "Stage of Life Breakdown of Selected Sharks", 
	         dataset,         
	         true,           
	         true, 
	         false);

	      return chart;
	   }
	   
	   private JFreeChart createLocChart( CategoryDataset dataset )
	   {
		   JFreeChart barChart = ChartFactory.createBarChart(
			         "Tag Locations of Selected Sharks",           
			         "Category",            
			         "Score",            
			         createLocDataset(),          
			         PlotOrientation.VERTICAL,           
			         true, true, false);
			return barChart;
	   }
	   
	   public JPanel createGenderPanel( )
	   {
	      JFreeChart genderchart = createSexChart(createSexDataset( ) );  
	      return new ChartPanel( genderchart ); 
	   }
	   
	   public JPanel createLifeStagePanel( )
	   {
	      JFreeChart lifechart = createAgeChart(createAgeDataset( ) );  
	      return new ChartPanel( lifechart ); 
	   }
	   
	   public JPanel createTagLocationPanel( )
	   {
	      JFreeChart locationchart = createLocChart(createLocDataset( ) );  
	      return new ChartPanel( locationchart ); 
	   }
}
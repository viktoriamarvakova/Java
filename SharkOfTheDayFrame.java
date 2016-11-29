import java.awt.BorderLayout;

import java.awt.Desktop;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import api.jaws.Jaws;

public class SharkOfTheDayFrame extends JFrame {

	private Jaws jawsAPI;
	private String currentDate;
	private String sharkOfTheDay;
	private File sharksOfDayList;
	
	public SharkOfTheDayFrame ()
	{
		jawsAPI = new Jaws ("dZsoDoLjG9TfEzRn","MSY2KotwQPji1138");
		sharksOfDayList = new File ("data/sharksOfTheDayList.txt");
		Calendar c = Calendar.getInstance(); 
		SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd");
		currentDate = formatter.format(c.getTime());
		createWidgets();
	}
	
	/**
	 * Creates the widgets of the frame.
	 */
	public void createWidgets() 
	{
		setTitle("Shark Of The Day");
		setLayout(new BorderLayout());
		String sharkOTD = getSharkOTD();
		JPanel labelsPanel = new JPanel (new GridLayout(2,1));
		try 
		{
			Font nameFont = Font.createFont(Font.TRUETYPE_FONT, new File("data/DK LEMON YELLOW SUN.OTF")).deriveFont(40f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("data/DK LEMON YELLOW SUN.OTF")));
		    JLabel message = new JLabel ("Shark of the day " , SwingConstants.CENTER);
		    JLabel shark = new JLabel (sharkOTD, SwingConstants.CENTER);
			message.setFont(nameFont);
			shark.setFont(nameFont.deriveFont (64.0f));
			labelsPanel.add(message);
			labelsPanel.add(shark);
			add(labelsPanel,BorderLayout.NORTH);
		} catch (IOException|FontFormatException e) {
		}
		
		
		ImageIcon videoImage = new ImageIcon("images/VideoClick.png");
		JLabel clickForVideoImage = new JLabel (videoImage);
		add(clickForVideoImage,BorderLayout.CENTER);
		clickForVideoImage.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				
			Desktop d=Desktop.getDesktop();
			try 
			{
				d.browse(new URI(jawsAPI.getVideo(sharkOTD)));
			} catch (IOException e1) {
					
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					remove(clickForVideoImage);
					ImageIcon noFootage = new ImageIcon("images/noFootage.png");
					add(new JLabel(noFootage),BorderLayout.CENTER);
					repaint();
					revalidate();
				}
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
			}});
		
			add(new JLabel(new ImageIcon("images/waves.png")),BorderLayout.SOUTH);
			pack();
	}
	
	
	
	/**
	 * Gets random shark name and prints it on a text file.
	 * @return random shark name
	 */
	private String generateRandomShark () 
	{
		int randomNumber = (int) Math.random();
		String randomShark = jawsAPI.getSharkNames().get(randomNumber);
	
		try 
		{
			if (!(sharksOfDayList.exists())) 
			{
				System.out.println("File created " + sharksOfDayList.createNewFile());
				sharksOfDayList.createNewFile();
			}
			FileWriter fw = new FileWriter(sharksOfDayList.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			BufferedReader br = new BufferedReader(new FileReader("data/sharksOfTheDayList.txt"));
			
			bw.write(currentDate + "\n" + randomShark );
			
			bw.close();
			fw.close();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return randomShark;
	}
	
	
	/**
	 * Goes through the text file and looks for the shark corresponding to the current date.
	 * @return shark of the day name
	 */
	private String getSharkOTD()  {
			try {
			Scanner sc = new Scanner(sharksOfDayList);
				if (sc.hasNextLine() == true)
				{
					while (sc.hasNextLine()) 
					{
						String date = sc.nextLine();
						String shark = sc.nextLine();
						if (date.equals(currentDate)) 
						{
							sharkOfTheDay = shark;
						}
						else 
						{
							sharkOfTheDay = generateRandomShark();
						}
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		
		return sharkOfTheDay;
	}
		
	
	

		
	
	


	
	
	
}

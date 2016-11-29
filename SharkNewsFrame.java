import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SharkNewsFrame extends JFrame{

	SharkNews sc;
	
	public SharkNewsFrame () 
	{
		createWidgets();
	}
	
	/**
	 * Creates the widgets for the frame.
	 */
	public void createWidgets() 
	{
		sc = new SharkNews();
		setTitle("Shark News");
		getContentPane().setLayout(new GridLayout(2,1));
		
		
		getContentPane().add(new JLabel(new ImageIcon("images/news.png")));
		JPanel p = new JPanel(new BorderLayout());
		JTextField text = new JTextField();
		text.setOpaque(false);
		text.setPreferredSize(new Dimension( 100, 50 ));  
		try {
			Font nameFont = Font.createFont(Font.TRUETYPE_FONT, new File("data/DK LEMON YELLOW SUN.OTF")).deriveFont(40f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("data/DK LEMON YELLOW SUN.OTF")));
			text.setFont(nameFont);
		} catch (FontFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		JLabel label = new JLabel ("Type in the name of shark you're looking for : ");
		p.add(label,BorderLayout.NORTH);
		p.add(text,BorderLayout.CENTER);
		JButton searchBtn = new JButton("Search");
		p.add(searchBtn,BorderLayout.SOUTH);
		getContentPane().add(p);
		searchBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String sharkName = text.getText();
				String news = sc.getNews(sharkName);
				label.setText(news);	
			}
		});
		
		
		setSize(340,480);
	}
	
	
}

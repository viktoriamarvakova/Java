import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class AckFrame extends JFrame {

	private JPanel contentPane;

	public AckFrame() {
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(10, 0, 0, 0));
		
		JLabel lblNewLabel = new JLabel("SharkTracker 0.1");
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Coding mostly by:");
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("V. Marvakova");
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("R. Naidenov");
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("S. Nowak");
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("R. Mason");
		contentPane.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("");
		contentPane.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("for 4CCS1PRA taught by:");
		contentPane.add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("Dr. Steffen Zschaler");
		contentPane.add(lblNewLabel_8);
		
		JLabel lblNewLabel_9 = new JLabel("Dr. Martin Chapman");
		contentPane.add(lblNewLabel_9);
	}

}

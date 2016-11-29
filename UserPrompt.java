import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;

public class UserPrompt extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	String promptString;
	String userInput;
	UserManager usermanager;
	boolean userfound;

	public UserPrompt(String text) {
		promptString = text;
		setResizable(false);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		textField = new JTextField();
		contentPane.add(textField, BorderLayout.CENTER);
		textField.setColumns(10);
		
		JLabel lblToSwitchTo = new JLabel(promptString);
		contentPane.add(lblToSwitchTo, BorderLayout.NORTH);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				userInput = textField.getText();
				setVisible(false);
			}		
		});
		contentPane.add(btnSearch, BorderLayout.SOUTH);
		
		setSize(500,100);
	}
	
	public UserPrompt(String string, UserManager usermanager, String action) throws IOException {
		this.usermanager = usermanager;
		promptString = string;
		setResizable(false);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		textField = new JTextField();
		contentPane.add(textField, BorderLayout.CENTER);
		textField.setColumns(10);
		
		JLabel lblToSwitchTo = new JLabel(promptString);
		contentPane.add(lblToSwitchTo, BorderLayout.NORTH);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				userInput = textField.getText();
				setVisible(false);
				if (action.equals("LOAD")){
					if (usermanager.findUser(getUserInput()).equals(null)){
						userfound = false;
					} else {
						usermanager.loadUser(getUserInput());
					}
				}
				if (action.equals("DEL")){
					try {
						usermanager.deleteUser(getUserInput());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				if (action.equals("MAKE")){
					try {
						usermanager.makeUser(getUserInput());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}		
		});
		contentPane.add(btnSearch, BorderLayout.SOUTH);
		
		setSize(500,100);
	}

	public String getUserInput(){
		return userInput;
	}

}

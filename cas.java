import static java.lang.System.out;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

 
@SuppressWarnings("serial")
public class cas extends JFrame {
	
	public int checkboxSpacing = 0;
	/**
	 * When user accounts are read from the text file, this will be used to space each option
	 */
	private JPanel contentPane;
	/**
	 * A container for the login widgets
	 */
	public static boolean isAdminUser;
	/**
	 * Used to check if administrator logs into program.
	 */
	public ArrayList<JCheckBox> userAccounts = new ArrayList<JCheckBox>();
	/**
	 * Stores all user accounts read from useraccounts.txt
	 */
	public static String selectedUser;
		
	/**
	 * Stores the user selected out of the checkbox options created.
	 */
	
	public cas() {
		/**
		 * Generates the user interface
		 * 
		 */
		JLabel titleLbl = new JLabel("Select User");
		JLabel errorLbl = new JLabel("User not selected. Please select a user:");
		JButton signInBtn = new JButton("Sign in");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 576);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(25, 25, 25));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		setTitle("CAS - Login");
		
		JLabel bottomLogoLbl = new JLabel("<\\ Computer Accessories Shop >");
		bottomLogoLbl.setForeground(Color.GREEN);
		bottomLogoLbl.setFont(new Font("Consolas", Font.PLAIN, 15));
		bottomLogoLbl.setBounds(45, 300, 250, 14);
		contentPane.add(bottomLogoLbl);
		
		
		titleLbl.setForeground(Color.WHITE);
		titleLbl.setFont(new Font("Candara", Font.BOLD, 25));
		titleLbl.setHorizontalAlignment(SwingConstants.LEFT);
		titleLbl.setBounds(380, 196, 127, 49);
		contentPane.add(titleLbl);
		
		createCheckboxesWithOptions(createUserOptions(readUserAccounts()));
		
		
		errorLbl.setForeground(Color.RED);
		errorLbl.setFont(new Font("Candara", Font.BOLD, 14));
		errorLbl.setBounds(380, 320, 551, 29);
		contentPane.add(errorLbl);
		errorLbl.setVisible(false);
		
		
		shop.styleBtns(signInBtn);
		signInBtn.setBounds(380, 360, 530, 29);
		contentPane.add(signInBtn);
		
		JLabel lblNewLabel = new JLabel("<CAS>");
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBackground(new Color(15,15,15));
		lblNewLabel.setForeground(Color.GREEN);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Consolas", Font.PLAIN, 75));
		lblNewLabel.setBounds(0, 0, 320, 537);
		contentPane.add(lblNewLabel);
		signInBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				for (int i = 0; i< userAccounts.size(); i++) {
					if (userAccounts.get(i).isSelected()) {
						shop.accountLoggedIn = userAccounts.get(i).getText();
						if (userAccounts.get(i).getText().contains("admin")) {
							cas.isAdminUser = true;
						} else {
							cas.isAdminUser = false;
						}
						cas.this.dispose();
						shop.runShop();
						
					} else {
						errorLbl.setVisible(true);
					}
				}
				
				createUserInstance(readUserAccounts());
				
			}
		});
	}
	
	
	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					cas frame = new cas();
					frame.setVisible(true);
					frame.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}}
			);
	}
	
	
	/**
	 * Runs the CAS login window using the cas frame and its components.
	 */
	public static ArrayList<String> readUserAccounts() {
		ArrayList<String> accounts = new ArrayList<String>();
		
		try {
			BufferedReader accountsBr = new BufferedReader (new FileReader("useraccounts.txt"));
			String userInfo = accountsBr.readLine();
			
			while (userInfo != null) {
				accounts.add(userInfo);
				userInfo = accountsBr.readLine();
			}
			
			accountsBr.close();
			
		} catch (Exception e) {
			out.println("Error!");
		}	
		
		
		return accounts;
		
		
	}
	
	/**
	 * Reads the user accounts text file and outputs the user accounts as an array list of strings.
	 * > accounts - an array list of each line from the user accounts text file
	 */
	
	public void createUserInstance(ArrayList<String> accounts) {
		for (String account: accounts) {
			if (account.contains(shop.accountLoggedIn.split(" - ")[0])) {
				customer.userID = Integer.parseInt(account.split(", ")[0]); 
				customer.postcode = account.split(", ")[4]; 
			}
		}
	} 
	
	/**
	 * From the accounts read from the useraccounts text file, this will identify which user
	 * is logged in and create an object using only the required attributes of the user class.
	 * > userID - the userID from the  user that logs in.
	 * > postcode - the postcode corresponding to user that logs in.
	 */
	
	public ArrayList<String> createUserOptions(ArrayList<String> allAccounts) {
		ArrayList<String> loginDetails = new ArrayList<String>();
		for (String acc: allAccounts) {
			new ArrayList<String>();
			String[] ad = acc.split(",");
			String specDetails = ad[0].trim() + " - " + ad[2].trim() + " - " + ad[1].trim() + " - " + ad[6].trim();
			loginDetails.add(specDetails);
		}
		return loginDetails;	
	}
	
	/** Uses the created user options to create checkboxes on the log in window to sign in with an account.
	 * > allAccounts
	 * > usersFound
	 */
	public void createCheckboxesWithOptions (ArrayList<String> usersFound) {	
		ButtonGroup allOptions = new ButtonGroup();
		int ylevel = 250;
		for (int i =0; i < usersFound.size(); i++) {
			JCheckBox option = new JCheckBox(usersFound.get(i));
			if (checkboxSpacing < 600) {
				option.setBounds(checkboxSpacing + 380 , ylevel, 200, 20);
			} else {
				option.setBounds(checkboxSpacing - 220, ylevel + 30, 200, 20);
			}
			
			option.setOpaque(true);
			option.setFont(new Font("Candara", Font.PLAIN, 12));
			option.setForeground(Color.WHITE);
			option.setBackground(new Color(25, 25, 25));
			option.setBorder(BorderFactory.createEmptyBorder());
			checkboxSpacing += 200;
			userAccounts.add(option);
			allOptions.add(option);
			contentPane.add(option);
		}
	}
}


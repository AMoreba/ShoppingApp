/*-------------------------------------------------------------
// AUTHORS: Emmanuel Cortes Castaneda, Abdulelah Bin Morebah, Stephanie Blossom, Basama Al Sulaimani
// FILENAME: Main.java
//-----------------------------------------------------------*/

package storeProject;

import java.awt.EventQueue;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.Color;

public class startupGUI extends Main {
	
	private JFrame frame;
	private JTextField usernameTF;
	private JPasswordField passTF;
	private String str;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					startupGUI window = new startupGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the application.
	 * @param user 
	 */
	public startupGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	void initialize() {
		// Many of JLabels and JTextFields are set in place to gather user information which will then be sent out to the database to be stored into.
		
		frame = new JFrame("Welcome");
		frame.setBounds(100, 100, 400, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel title = new JLabel("Welcome");
		title.setFont(new Font("Nunito", Font.PLAIN, 24));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setBounds(10, 11, 364, 34);
		frame.getContentPane().add(title);
		
		JLabel userLabel = new JLabel("Username");
		userLabel.setHorizontalAlignment(SwingConstants.CENTER);
		userLabel.setFont(new Font("Nunito", Font.PLAIN, 16));
		userLabel.setBounds(138, 56, 110, 31);
		frame.getContentPane().add(userLabel);
		
		usernameTF = new JTextField();
		usernameTF.setBounds(100, 98, 200, 20);
		frame.getContentPane().add(usernameTF);
		usernameTF.setColumns(10);
		
		JLabel passLabel = new JLabel("Password");
		passLabel.setHorizontalAlignment(SwingConstants.CENTER);
		passLabel.setFont(new Font("Nunito", Font.PLAIN, 16));
		passLabel.setBounds(138, 127, 110, 31);
		frame.getContentPane().add(passLabel);
		
		passTF = new JPasswordField();
		passTF.setBounds(100, 167, 200, 20);
		frame.getContentPane().add(passTF);
		
		JButton loginBtn = new JButton("Login");
		loginBtn.setBackground(Color.WHITE);
		
		
		loginBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				str = usernameTF.getText();
				String jdbcURL = "jdbc:postgresql://localhost:5432/cse-final";
				String admin = "postgres";
				String password = "lily26";
				Connection connection = null;
				
				// Establish connection to database
				try {
					connection = DriverManager.getConnection(jdbcURL, admin, password);
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				// Verify the user exists in the database
				try {
					Statement stmt = connection.createStatement();
					String userPass = String.valueOf(passTF.getPassword());
					
					String sql = "Select * from udata where username='" + usernameTF.getText() + "' and pass='" + userPass + "'";
					ResultSet rs = stmt.executeQuery(sql);
					
					if (rs.next()) {
						JOptionPane.showMessageDialog(null, "Login Successfully");
						
					} else {
						JOptionPane.showMessageDialog(null, "Invalid Credentials!");
					}
					rs.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					System.out.println("Error printing boolean or login successfull!");
					e1.printStackTrace();
				}
				
				// retreive users full name
				try {
					String userPass = String.valueOf(passTF.getPassword());
					Statement stmt = connection.createStatement();
					ResultSet rs = stmt.executeQuery("Select fname, lname from udata where username='" + usernameTF.getText() + "' and pass= '" + userPass + "'");
					
					while(rs.next()) {
						// Gets the users firstname from the given username textField and password which then stores it into the firstName and lastName variables
						firstName = rs.getString("fname");
						lastName = rs.getString("lname");
						
						System.out.println("Welcome back " + firstName + " " + lastName);
					}
					
					rs.close();
					stmt.close();
				} catch (SQLException  e1) {
					// TODO Auto-generated catch block
					System.out.println("Error in connecting!");
					e1.printStackTrace();
				}
				
				// retrieve if user is employee or not and take user to shopping page
				try {
					Statement stmt = connection.createStatement();
					String userPass = String.valueOf(passTF.getPassword());
					
					String employeeVerification = "Select employee from udata where pass='" + userPass + "'";
					ResultSet employee = stmt.executeQuery(employeeVerification);
					
					while (employee.next()) {
						// Following section of code will determine whether the user logging in is an employee or a customer. After finding out what the user is it will send them to an employee view of the page or a customer view of the page
						boolean emp = employee.getBoolean("employee");
						
						if (emp == true) {
							
							frame.setVisible(false);
							employeeShopping p2 = new employeeShopping();
							p2.setVisible(true);
							System.out.println("Logging into Employee View");
						} else if (emp == false) {
							frame.setVisible(false);
							customerShopping p2 = new customerShopping();
							p2.setVisible(true);
							System.out.println("Welcome " + firstName + " " + lastName);
						}
					}
					
					employee.close();
				} catch (SQLException | IOException  e1) {
					// TODO Auto-generated catch block
					System.out.println("Error in connecting!");
					e1.printStackTrace();
				}
				
				
			}
		});
		
		loginBtn.setBounds(159, 234, 89, 23);
		frame.getContentPane().add(loginBtn);
		
		// Clicking the sign up button will lead to a sign up page
		JButton signupBtn = new JButton("Sign Up");
		signupBtn.setBackground(Color.WHITE);
		
		signupBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				signupGUI p2 = new signupGUI();
			}
		});
		
		signupBtn.setBounds(159, 268, 89, 23);
		frame.getContentPane().add(signupBtn);
			
		frame.setVisible(true);
		
		
	}
}

/*-------------------------------------------------------------
// AUTHORS: Emmanuel Cortes Castaneda, Abdulelah Bin Morebah, Stephanie Blossom, Basama Al Sulaimani
// FILENAME: Main.java
//-----------------------------------------------------------*/

package storeProject;

import java.sql.*;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JComboBox;

public class employeeGUI {

	private JFrame frame;
	private JTextField f_Name;
	private JTextField username;
	private JTextField email;
	private JTextField l_Name;
	private JTextField pass;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					employeeGUI window = new employeeGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public employeeGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	void initialize() {		
		// Many JTextField were set to gather user information to send to the database
		frame = new JFrame("Signup Page");
		frame.setBounds(100, 100, 400, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Signup Page");
		lblNewLabel.setBounds(10, 11, 364, 23);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Nunito", Font.PLAIN, 16));
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("First Name");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(10, 45, 80, 23);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Username");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_1.setBounds(10, 130, 80, 23);
		frame.getContentPane().add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Email");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_2.setBounds(10, 216, 80, 23);
		frame.getContentPane().add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_3 = new JLabel("Last Name");
		lblNewLabel_1_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_3.setBounds(214, 45, 80, 23);
		frame.getContentPane().add(lblNewLabel_1_3);
		
		JLabel lblNewLabel_1_4 = new JLabel("Password");
		lblNewLabel_1_4.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_4.setBounds(214, 130, 80, 23);
		frame.getContentPane().add(lblNewLabel_1_4);
		
		JLabel position = new JLabel("Position");
		position.setFont(new Font("Tahoma", Font.PLAIN, 14));
		position.setBounds(214, 216, 80, 23);
		frame.getContentPane().add(position);
		
		f_Name = new JTextField();
		f_Name.setBounds(10, 67, 160, 20);
		frame.getContentPane().add(f_Name);
		f_Name.setColumns(10);
		
		username = new JTextField();
		username.setColumns(10);
		username.setBounds(10, 153, 160, 20);
		frame.getContentPane().add(username);
		
		email = new JTextField();
		email.setColumns(10);
		email.setBounds(10, 239, 160, 20);
		frame.getContentPane().add(email);
		
		l_Name = new JTextField();
		l_Name.setColumns(10);
		l_Name.setBounds(214, 67, 160, 20);
		frame.getContentPane().add(l_Name);
		
		pass = new JTextField();
		pass.setColumns(10);
		pass.setBounds(214, 153, 160, 20);
		frame.getContentPane().add(pass);
		
		JList list = new JList();
		list.setBounds(349, 258, -80, 35);
		frame.getContentPane().add(list);
		
		
		// Instead of asking the user for their address, employees will be asked for their position
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(214, 238, 160, 22);
		frame.getContentPane().add(comboBox);
		comboBox.addItem("Store Manager");
		comboBox.addItem("Inventory Specialist");
		
		JButton signup = new JButton("Signup");
		signup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String jdbcURL = "jdbc:postgresql://localhost:5432/cse-final";
				String admin = "postgres";
				String password = "lily26";
				
				try {
					Connection connection = DriverManager.getConnection(jdbcURL, admin, password);
					System.out.println("Connected successfully!");
					
					// Inserting the information from the textFields into the columns on a new row within the database, but only as ?
					String sql = "INSERT INTO udata (username, pass, fname, lname, email, address, position, employee, cartitem, cartprice, cartamount, orders)"
							+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
					
					// All ? will be overwritten by the textFields by using the getText() method.
					PreparedStatement stmt = connection.prepareStatement(sql);
					stmt.setString(1, username.getText());
					stmt.setString(2, pass.getText());
					stmt.setString(3, f_Name.getText());
					stmt.setString(4, l_Name.getText());
					stmt.setString(5, email.getText());
					stmt.setString(6, null);
					stmt.setString(7, comboBox.getSelectedItem().toString());
					stmt.setBoolean(8, true);
					stmt.setArray(9, null);
					stmt.setArray(10, null);
					stmt.setArray(11, null);
					stmt.setString(12, null);
					
					int rows = stmt.executeUpdate();
					
					if (rows > 0) {
						System.out.println("Account Created Successfully!");
						
					}
					connection.close();
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					System.out.println("Error in connecting!");
					e1.printStackTrace();
				}
			}
		});
		
		signup.setBounds(146, 270, 89, 23);
		frame.getContentPane().add(signup);
		
		JButton homeBtn = new JButton("Home");
		homeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				startupGUI p1 = new startupGUI();
			}
		});
		homeBtn.setBounds(146, 304, 89, 23);
		frame.getContentPane().add(homeBtn);
		
		frame.setVisible(true);
	}
}

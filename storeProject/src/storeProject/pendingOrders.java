/*-------------------------------------------------------------
// AUTHORS: Emmanuel Cortes Castaneda, Abdulelah Bin Morebah, Stephanie Blossom, Basama Al Sulaimani
// FILENAME: Main.java
//-----------------------------------------------------------*/

package storeProject;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Array;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class pendingOrders {

	private JFrame frame;
	public static ArrayList<String> orderID = new ArrayList<>(); 
	public int i = 0;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					pendingOrders window = new pendingOrders();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws SQLException 
	 */
	public pendingOrders() throws SQLException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() throws SQLException {
		String jdbcURL = "jdbc:postgresql://localhost:5432/cse-final";
		String admin = "postgres";
		String password = "lily26";
		Connection connection = null;
		
		connection = DriverManager.getConnection(jdbcURL, admin, password);
		
		try {
			Statement stmt = connection.createStatement();
			String sql = "Select orders from udata WHERE orders IS NOT NULL";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				String orderNum = rs.getString("orders");
				orderID.add(orderNum);
			}
			
		} catch (SQLException e3) {
			e3.printStackTrace();
		}
		
		frame = new JFrame();
		frame.setBounds(100, 100, 400, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel title = new JLabel("Pending Orders");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Nunito", Font.PLAIN, 14));
		title.setBounds(0, 28, 384, 16);
		frame.getContentPane().add(title);
		
		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				try {
					employeeShopping p2 = new employeeShopping();
					p2.setVisible(true);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		back.setBounds(140, 299, 117, 29);
		frame.getContentPane().add(back);
		
		JPanel orderPanel = new JPanel();
		orderPanel.setBackground(Color.ORANGE);
		orderPanel.setBounds(90, 55, 204, 171);
		
		frame.getContentPane().add(orderPanel);
		orderPanel.setLayout(null);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(10, 11, 184, 22);
		
		for (int k = 0; k < orderID.size(); k++) {
			comboBox.addItem(orderID.get(k));
		}
		
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				i = orderID.indexOf((String) comboBox.getSelectedItem());
			}
				});
		orderPanel.add(comboBox);
		
		JButton completed = new JButton("Completed");
		completed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String j = comboBox.getSelectedItem().toString();
				System.out.println(j);
				int k = orderID.indexOf((String) comboBox.getSelectedItem());
				
				String jdbcURL = "jdbc:postgresql://localhost:5432/cse-final";
				String admin = "postgres";
				String password = "lily26";
				Connection connection = null;
				
				try {
					connection = DriverManager.getConnection(jdbcURL, admin, password);
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				try {
					Statement stmt = connection.createStatement();
					JOptionPane.showMessageDialog(null, "Order Has Been Completed!");
					String sql = "UPDATE udata SET orders= " + null + "  where orders= '" + j + "'";
					stmt.executeUpdate(sql);
				} catch (SQLException e3) {
					e3.printStackTrace();
				}
				
				orderID.remove(k);
				for (int counter = 0; counter < orderID.size(); counter++) {
					comboBox.addItem(orderID.get(counter));
				}
			}
		});
		
		completed.setBounds(140, 259, 117, 29);
		frame.getContentPane().add(completed);
	}
	
	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		if (b = true) {
			frame.setVisible(true);
		} else {
			frame.setVisible(false);
		}
	}
}

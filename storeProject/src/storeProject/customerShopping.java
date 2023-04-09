/*-------------------------------------------------------------
// AUTHORS: Emmanuel Cortes Castaneda, Abdulelah Bin Morebah, Stephanie Blossom, Basama Al Sulaimani
// FILENAME: Main.java
//-----------------------------------------------------------*/

package storeProject;

import java.sql.*;
import java.text.DecimalFormat;
import java.io.*;
import java.util.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.MatteBorder;

public class customerShopping extends employeeShopping {
	private JLabel cartList;
	private JComboBox comboBox;
	private JFrame frame;
	private DefaultListModel demoList = new DefaultListModel();
	private DefaultListModel shoppingListItems = new DefaultListModel();
	private double cost;
	private String itemListSeparated;
	private String itemPriceSeparated;
	private String itemAmountSeparated;
	private Random r = new Random();


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					customerShopping window = new customerShopping();
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
	public customerShopping() throws IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	void initialize() {
		frame = new JFrame("Employee View");
		frame.setBounds(100, 100, 600, 420);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		 
		String selectedItem = "";
		int i = 0;
		
		JLabel availability = new JLabel("In stock: null" );
		if (amount.size() > 0) {
			availability.setText("In stock: " + String.valueOf(amount.get(i)));
		} else {
			availability.setText("Out of stock");
		}
		availability.setFont(new Font("Nunito", Font.PLAIN, 14));
		availability.setBounds(270, 83, 140, 22);
		frame.getContentPane().add(availability);
		
		JLabel itemCost = new JLabel("Price: $null");
		if (price.size() > 0) {
			itemCost.setText(String.valueOf("Price: $" + price.get(i)));
		}
		itemCost.setFont(new Font("Nunito", Font.PLAIN, 14));
		itemCost.setBounds(270, 98, 140, 22);
		frame.getContentPane().add(itemCost);
		
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(1, 0, 1000, 1));
		spinner.setBounds(396, 127, 40, 20);
		frame.getContentPane().add(spinner);
		
		comboBox = new JComboBox();
		comboBox.setForeground(Color.BLACK);
		for (int j = 0; j < name.size(); j++) {
			comboBox.addItem(name.get(j));
		}
		
		comboBox.setBackground(new Color(245, 245, 245));
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 12));
		comboBox.setToolTipText("Select Item");
		comboBox.setBounds(270, 50, 166, 22);
		frame.getContentPane().add(comboBox);
		
		selectedItem = (String) comboBox.getSelectedItem();
		
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int i = name.indexOf((String) comboBox.getSelectedItem());
				
				availability.setText("In stock: " + amount.get(i));
				itemCost.setText("Price: $" + price.get(i));
				
			}
				});
		
		JLabel totalCost = new JLabel("$0.00");
		totalCost.setFont(new Font("Nunito", Font.PLAIN, 12));
		totalCost.setHorizontalAlignment(SwingConstants.RIGHT);
		totalCost.setBounds(159, 27, 60, 23);
		frame.getContentPane().add(totalCost);
		
		JLabel shoppingCartText = new JLabel("Shopping Cart");
		shoppingCartText.setFont(new Font("Nunito", Font.PLAIN, 16));
		shoppingCartText.setBounds(20, 22, 165, 28);
		frame.getContentPane().add(shoppingCartText);
		
		JLabel notification = new JLabel("New label");
		notification.setFont(new Font("Tahoma", Font.ITALIC, 10));
		notification.setBounds(294, 177, 120, 16);
		frame.getContentPane().add(notification);
		notification.setVisible(false);
		
		JLabel amtSelected = new JLabel("Amount selected -");
		amtSelected.setFont(new Font("Nunito", Font.PLAIN, 14));
		amtSelected.setBounds(270, 126, 116, 20);
		frame.getContentPane().add(amtSelected);
		
		JList listfinal = new JList();
		listfinal.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		listfinal.setBackground(Color.ORANGE);
		listfinal.setBounds(10, 62, 209, 177);
		frame.getContentPane().add(listfinal);
		
		JButton addBtn = new JButton("Add to cart");
		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String jdbcURL = "jdbc:postgresql://localhost:5432/cse-final";
				String admin = "postgres";
				String password = "lily26";
				Connection connection = null;
				int i = comboBox.getSelectedIndex();
				
				DecimalFormat df = new DecimalFormat();
				df.setMaximumFractionDigits(2);
				int numSelected = (Integer) spinner.getValue();

				if ((int)spinner.getValue() > amount.get(i)) {
					notification.setVisible(true);
					notification.setText("Cannot order that amount");
					
				} else {
					nameList.add((String) comboBox.getSelectedItem());
					cost += price.get(i) * (Integer) spinner.getValue();
					cartList = new JLabel((String) comboBox.getSelectedItem() + " x" + numSelected);
					cartList.setText("");
					totalCost.setText(String.valueOf(df.format(cost))+"$");
					
					amtSelected.setFont(new Font("Nunito", Font.PLAIN, 14));
					
					amount.set(i, amount.get(i) - (int)spinner.getValue());
					if (amount.get(i) == 0) {
						availability.setText("Out of stock");
					}else {
						availability.setText("In stock: " + amount.get(i));
					}

					notification.setVisible(false);
					
					for (String itm: nameList) {
						if (!cartName.contains(itm)) {
							cartName.add(itm);
						}
					}
					
					if (cartAmount.size() != cartName.size()) {
						cartAmount.add((Integer) spinner.getValue());					
					} else if (cartPrice.size() == cartName.size()) {
						int originalAmount = cartAmount.get(i);
						cartAmount.set(i, ((Integer) spinner.getValue()) + originalAmount);
					}
					
					if (cartPrice.size() != cartName.size()) {
						cartPrice.add(price.get(i) * (Integer) spinner.getValue());					
					} else if (cartPrice.size() == cartName.size()) {
						double originalPrice = cartPrice.get(i);
						double newPrice = (price.get(i) * ((Integer) spinner.getValue())) + originalPrice;
						cartPrice.set(i, newPrice);
					}
					
					double subTotal = 0;
					for (int j = 0; j < cartPrice.size(); j++) {
						subTotal += cartPrice.get(j);
					}
					
//					System.out.println(cartName);
//					System.out.println(cartPrice);
//					System.out.println(cartAmount);
				
					demoList.addElement(comboBox.getSelectedItem()+ "|\t \t \t"+ " Amount: " +spinner.getValue() + " |\t \t \t"+ "$"+ price.get(i) * (Integer) spinner.getValue() );
					listfinal.setModel(demoList);
					
					// Update user shopping cart in postgres
					try {
						connection = DriverManager.getConnection(jdbcURL, admin, password);
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					
					try {
						Statement stmt = connection.createStatement();
						StringBuilder itemTracker = new StringBuilder("");
						StringBuilder priceTracker = new StringBuilder("");
						StringBuilder amountTracker = new StringBuilder("");
						
						for (String eachstring : cartName) {
							itemTracker.append(eachstring).append(",");
						}
						
						itemListSeparated = itemTracker.toString();
						
						if (itemListSeparated.length() > 0) {
							itemListSeparated = itemListSeparated.substring(0, itemListSeparated.length() -1);
						}
						String sql = "UPDATE udata SET cartitem= '{" + itemListSeparated + "}' where lname= '" + lastName + "'";
						stmt.executeUpdate(sql);
						
						for (Double eachstring : cartPrice) {
							priceTracker.append(eachstring).append(",");
						}
						
						itemPriceSeparated = priceTracker.toString();
						
						if (itemPriceSeparated.length() > 0) {
							itemPriceSeparated = itemPriceSeparated.substring(0, itemPriceSeparated.length() -1);
						}
						sql = "UPDATE udata SET cartprice= '{" + itemPriceSeparated + "}' where lname= '" + lastName + "'";
						stmt.executeUpdate(sql);
						
						for (Integer eachstring : cartAmount) {
							amountTracker.append(eachstring).append(",");
						}
						
						itemAmountSeparated = amountTracker.toString();
						
						if (itemAmountSeparated.length() > 0) {
							itemAmountSeparated = itemAmountSeparated.substring(0, itemAmountSeparated.length() -1);
						}
						sql = "UPDATE udata SET cartamount= '{" + itemAmountSeparated + "}' where lname= '" + lastName + "'";
						stmt.executeUpdate(sql);
						
						stmt.close();
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						System.out.println("Error printing boolean or login successfull!");
						e1.printStackTrace();
					}
					
					
				}

			}
		});
		
		listfinal.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listfinal.setLayoutOrientation(JList.VERTICAL);
		
		addBtn.setBackground(new Color(245, 245, 245));
		addBtn.setBounds(294, 198, 116, 23);
		frame.getContentPane().add(addBtn);
		
		
		
		JButton logoutBtn = new JButton("Logout");
		logoutBtn.setBackground(new Color(245, 245, 245));
		logoutBtn.setFont(new Font("Tahoma", Font.PLAIN, 10));
		logoutBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				startupGUI p1 = new startupGUI();
			}
		});
		logoutBtn.setBounds(20, 331, 89, 23);
		frame.getContentPane().add(logoutBtn);
		
		JButton cartModify = new JButton("Remove Item");
		cartModify.setBackground(new Color(245, 245, 245));
		cartModify.setFont(new Font("Tahoma", Font.PLAIN, 10));
		
		cartModify.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				DecimalFormat df = new DecimalFormat();
				df.setMaximumFractionDigits(2);
				String value = (String)listfinal.getSelectedValue();
				if(value != null) {
					double values = Double.parseDouble(value.substring(value.lastIndexOf("$") + 1));
					cost -=values;
					
					String amount1 = (String)listfinal.getSelectedValue();
					int amounts = Integer.parseInt(amount1.substring(amount1.indexOf(": ") + 2, amount1.indexOf(" |")));
					
					value = value.substring(0, value.indexOf('|'));
					
					int index1 = name.indexOf(value);
					System.out.println(index1);
					amount.set(index1,amount.get(index1) + amounts );
					
					totalCost.setText(String.valueOf(df.format(cost))+"$");
					demoList.removeElementAt(listfinal.getSelectedIndex());
					
					availability.setText("In stock: " + String.valueOf(amount.get(i)));
				}
			}
		});
		
		cartModify.setBounds(110, 251, 110, 25);
		frame.getContentPane().add(cartModify);
		
		JButton orderBtn = new JButton("Place Order");
		orderBtn.setBackground(new Color(245, 245, 245));
		orderBtn.setFont(new Font("Tahoma", Font.PLAIN, 10));
		orderBtn.setToolTipText("Place Order");
		orderBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
					int num = r.nextInt(999999);
					String formatted = "";
					formatted = String.format("%06d", num);
					int orderNum = Integer.parseInt(formatted);
					JOptionPane.showMessageDialog(null, "Order Placed!");
					String sql = "UPDATE udata SET orders= '" + orderNum + "' where fname= '" + firstName + "' and lname= '" + lastName + "'";
					stmt.executeUpdate(sql);
					
					cartName.clear();
					cartPrice.clear();
					cartAmount.clear();
				} catch (SQLException e3) {
					e3.printStackTrace();
				}
				
				demoList.clear();
				cost = 0;
				totalCost.setText(String.valueOf(cost) + "$");
			}
		});
		orderBtn.setBounds(10, 251, 90, 25);
		frame.getContentPane().add(orderBtn);
		
		JLabel userSC = new JLabel("");
		userSC.setText(firstName + "'s");
		userSC.setFont(new Font("Nunito", Font.PLAIN, 12));
		userSC.setBounds(20, 11, 107, 14);
		frame.getContentPane().add(userSC);
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

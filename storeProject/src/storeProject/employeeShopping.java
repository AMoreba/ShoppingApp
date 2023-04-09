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


public class employeeShopping extends startupGUI {
	private JLabel cartList;
	private JComboBox comboBox;
	private JFrame frame;
	private JTextField itemNameTF;
	private JTextField itemPriceTF;
	private DefaultListModel demoList = new DefaultListModel();
	private DefaultListModel shoppingListItems = new DefaultListModel();
	private double cost;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					employeeShopping window = new employeeShopping();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws IOException 
	 */
	public employeeShopping() throws IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */	
	
	void initialize() {
		// Most of the JLabel's, JFrames, etc were set on the page to gather user input when choosing items from the combo box
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
		
		//  Everytime an item from the comboBox is selected the JLabel's with the information of the item are updated.
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int i = name.indexOf((String) comboBox.getSelectedItem());
				
				availability.setText("In stock: " + amount.get(i));
				itemCost.setText("Price: $" + price.get(i));
				
			}
				});
		
		JSpinner itemSpinner = new JSpinner();
		itemSpinner.setBounds(540, 98, 35, 20);
		frame.getContentPane().add(itemSpinner);
		
		// Adds item to name, price, and amount arrylists
		JButton createItemBtn = new JButton("Create Item");
		createItemBtn.setBackground(new Color(245, 245, 245));
		createItemBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// The following code will add items to an arraylist that the comboBox gets the values from that will be added onto the list. Creating a new item will add to the original list and the combobox will be updated
				name.add(itemNameTF.getText());
				String amtPriceIn=itemPriceTF.getText();
				try {Double.parseDouble(amtPriceIn);
				} catch(NumberFormatException nfe) {
					JOptionPane.showMessageDialog(null, "Please inter numbers only", "Number Error", JOptionPane.ERROR_MESSAGE);
				 }

				price.add(Double.parseDouble(itemPriceTF.getText()));
				amount.add((Integer) itemSpinner.getValue());
				
				comboBox.addItem(itemNameTF.getText());
				System.out.println(name);
				
				if((Integer) itemSpinner.getValue()<=0) {
					availability.setText("Out of stock");
				}
			}
		});
		
		JLabel totalCost = new JLabel("$0.00");
		totalCost.setFont(new Font("Nunito", Font.PLAIN, 12));
		totalCost.setHorizontalAlignment(SwingConstants.RIGHT);
		totalCost.setBounds(159, 27, 60, 23);
		frame.getContentPane().add(totalCost);
		
		createItemBtn.setFont(new Font("Tahoma", Font.PLAIN, 10));
		createItemBtn.setBounds(482, 123, 89, 23);
		frame.getContentPane().add(createItemBtn);
		
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
		
		itemNameTF = new JTextField();
		itemNameTF.setBounds(485, 28, 86, 20);
		frame.getContentPane().add(itemNameTF);
		itemNameTF.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Item Name");
		lblNewLabel.setBounds(485, 11, 78, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Item Price");
		lblNewLabel_1.setBounds(485, 54, 78, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		itemPriceTF = new JTextField();
		itemPriceTF.setBounds(485, 67, 86, 20);
		frame.getContentPane().add(itemPriceTF);
		itemPriceTF.setColumns(10);
		
		JLabel itemJPanel = new JLabel("Item Stock");
		itemJPanel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		itemJPanel.setBounds(485, 98, 60, 14);
		frame.getContentPane().add(itemJPanel);
		
		JList listfinal = new JList();
		listfinal.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		listfinal.setBackground(Color.ORANGE);
		listfinal.setBounds(10, 62, 209, 177);
		frame.getContentPane().add(listfinal);
		
		JButton addBtn = new JButton("Add to cart");
		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// The followoing lines of code will add items to the shopping list panel towards the left of the screen
				
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
					
					System.out.println(cartName);
					System.out.println(cartPrice);
					System.out.println(cartAmount);
				
					// Update user shopping cart in postgres
					demoList.addElement(comboBox.getSelectedItem()+ "|\t \t \t"+ " Amount: " +spinner.getValue() + " |\t \t \t"+ "$"+ price.get(i) * (Integer) spinner.getValue() );
					listfinal.setModel(demoList);
				}
			}
		});
		
		listfinal.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listfinal.setLayoutOrientation(JList.VERTICAL);
		
		addBtn.setBackground(new Color(245, 245, 245));
		addBtn.setBounds(294, 198, 116, 23);
		frame.getContentPane().add(addBtn);
		
		JButton orderBtn = new JButton("Place Order");
		orderBtn.setBackground(new Color(245, 245, 245));
		orderBtn.setFont(new Font("Tahoma", Font.PLAIN, 10));
		orderBtn.setToolTipText("Place Order");
		orderBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(demoList);
			}
		});
		orderBtn.setBounds(10, 251, 90, 25);
		frame.getContentPane().add(orderBtn);
		
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
		
		JButton pendingBtn = new JButton("Pending Orders");
		pendingBtn.setBackground(new Color(245, 245, 245));
		pendingBtn.setFont(new Font("Tahoma", Font.PLAIN, 10));
		pendingBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				pendingOrders p4;
				try {
					p4 = new pendingOrders();
					p4.setVisible(true);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		pendingBtn.setBounds(131, 331, 107, 23);
		frame.getContentPane().add(pendingBtn);
		
		JButton editBtn = new JButton("Delete Item");
		editBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = name.indexOf((String) comboBox.getSelectedItem());
				name.remove(i);
				price.remove(i);
				amount.remove(i);
				comboBox.removeItem(comboBox.getSelectedItem());
				int listSize = name.size();
				int count = comboBox.getItemCount();
				
				if (count != listSize) {
				for (int j = 0; j < name.size(); j++) {
					comboBox.addItem(name.get(j));
				}}
			}
		});
		
		editBtn.setBackground(new Color(245, 245, 245));
		editBtn.setFont(new Font("Tahoma", Font.PLAIN, 10));
		editBtn.setBounds(248, 331, 89, 23);
		frame.getContentPane().add(editBtn);
		
		JButton addCustomerBtn = new JButton("Add Customer");
		addCustomerBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				customerGUI p3 = new customerGUI();
			}
		});
		addCustomerBtn.setBackground(new Color(245, 245, 245));
		addCustomerBtn.setFont(new Font("Tahoma", Font.PLAIN, 10));
		addCustomerBtn.setBounds(347, 331, 107, 23);
		frame.getContentPane().add(addCustomerBtn);
		
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
					System.out.println(values);
					System.out.println(value);
					System.out.println(amounts);
					
					int index1 = name.indexOf(value);
					amount.set(index1,amount.get(index1) + amounts );
					
					totalCost.setText(String.valueOf(df.format(cost))+"$");
					demoList.removeElementAt(listfinal.getSelectedIndex());
					
					availability.setText("In stock: " + String.valueOf(amount.get(i)));
				}
			}
		});
		
		cartModify.setBounds(110, 251, 110, 25);
		frame.getContentPane().add(cartModify);
		
		JLabel userSC = new JLabel("");
		userSC.setText(firstName + "'s");
		userSC.setFont(new Font("Nunito", Font.PLAIN, 14));
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

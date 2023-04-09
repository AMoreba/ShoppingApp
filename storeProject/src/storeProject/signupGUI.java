/*-------------------------------------------------------------
// AUTHORS: Emmanuel Cortes Castaneda, Abdulelah Bin Morebah, Stephanie Blossom, Basama Al Sulaimani
// FILENAME: Main.java
//-----------------------------------------------------------*/

package storeProject;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class signupGUI {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					signupGUI window = new signupGUI();
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
	public signupGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 400, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		// Two buttons were made to send the user to the customer/employee sign up pages
		JButton customer = new JButton("Customer Account");
		customer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				customerGUI p3 = new customerGUI();
			}
		});
		customer.setFont(new Font("Tahoma", Font.PLAIN, 16));
		customer.setBounds(62, 81, 260, 46);
		frame.getContentPane().add(customer);
		
		JButton employee = new JButton("Employee Account");
		employee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				employeeGUI p3 = new employeeGUI();
			}
		});
		employee.setFont(new Font("Tahoma", Font.PLAIN, 16));
		employee.setBounds(62, 176, 260, 46);
		frame.getContentPane().add(employee);
		
		frame.setVisible(true);
	}

}

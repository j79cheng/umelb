//Jialiang Cheng 1251403
package client;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.Insets;

import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.awt.event.ActionEvent;

public class MainWindow {

	public JFrame frame;
	public JTextField textField;


	/**
	 * Create the application.
	 */
	public MainWindow(String ip, int port) {
		initialize(ip, port);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String ip, int port) {
		frame = new JFrame("Client");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		JMenu menu = new JMenu("infomation");
		JMenuItem help = new JMenuItem("help");
		help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null,
						"1. For query a word, type the word in textfield and press add \n"
	                    + "2. For add a word, type the word and the word meaning in textfield (separate with |) and press add\n"
	                    + "3. For remove a word, type the word in textfield and press remove\n"
	                    + "4. For update a word, type the word and the word meaning in textfield (separate with |) and press update\n"
	                    + "5. For saving the current dictionary, press the save and the server will save the current dictionary file to initial path.\n"
	                    + "Notice: meaning can not be empty!",
	                    "help",
	                    JOptionPane.INFORMATION_MESSAGE);
			}
        });
		JMenuItem about = new JMenuItem("about");
		about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null,
						"Distributed system assignment 1\n"
	                    + "By Jialiang Cheng\n",
	                    "about",
	                    JOptionPane.INFORMATION_MESSAGE);
			}
        });
		menu.add(help);
		menu.add(about);
		menuBar.add(menu);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.weightx = 0.5;
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 0;
		frame.getContentPane().add(textField, gbc_textField);
		textField.setColumns(10);
		
		JButton btnQButton = new JButton("Query");

		GridBagConstraints gbc_btnQButton = new GridBagConstraints();
		gbc_btnQButton.insets = new Insets(0, 0, 0, 0);
		gbc_btnQButton.gridx = 1;
		gbc_btnQButton.gridy = 0;
		frame.getContentPane().add(btnQButton, gbc_btnQButton);
		
		JButton btnAButton = new JButton("Add");

		GridBagConstraints gbc_btnAButton = new GridBagConstraints();
		gbc_btnAButton.insets = new Insets(0, 0, -80, 0);
		gbc_btnAButton.gridx = 1;
		gbc_btnAButton.gridy = 0;
		frame.getContentPane().add(btnAButton, gbc_btnAButton);
		
		JButton btnRButton = new JButton("Remove");

		GridBagConstraints gbc_btnRButton = new GridBagConstraints();
		gbc_btnRButton.insets = new Insets(0, 0, -160, 0);
		gbc_btnRButton.gridx = 1;
		gbc_btnRButton.gridy = 0;
		frame.getContentPane().add(btnRButton, gbc_btnRButton);
		
		JButton btnUButton = new JButton("Update");

		GridBagConstraints gbc_btnUButton = new GridBagConstraints();
		gbc_btnUButton.insets = new Insets(0, 0, -240, 0);
		gbc_btnUButton.gridx = 1;
		gbc_btnUButton.gridy = 0;
		frame.getContentPane().add(btnUButton, gbc_btnUButton);
		
		JButton btnSButton = new JButton("save");

		GridBagConstraints gbc_btnSButton = new GridBagConstraints();
		gbc_btnSButton.insets = new Insets(0, 0, -320, 0);
		gbc_btnSButton.gridx = 1;
		gbc_btnSButton.gridy = 0;
		frame.getContentPane().add(btnSButton, gbc_btnSButton);
		
		JTextArea textArea = new JTextArea();
		JScrollPane scroll = new JScrollPane(textArea); 
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.insets = new Insets(0, 0, 0, 5);
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridx = 0;
		gbc_textArea.gridy = 1;
		frame.getContentPane().add(scroll, gbc_textArea);
		
		btnQButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("query "+ textField.getText());
				textArea.append("Trying to query the word " + textField.getText()+" in server...\n");
				try {
					Socket socket = new Socket(ip, port);
					DataInputStream input = new DataInputStream(socket.getInputStream());
				    DataOutputStream output = new DataOutputStream(socket.getOutputStream());
					String sendData = "Q" + textField.getText();
					output.writeUTF(sendData);
					output.flush();
					textArea.append(input.readUTF() + "\n");
					socket.close();
				} catch (Exception e) {
					textArea.append("ERROR: Connection error! Please check the server status!\n");
				}
			}
		});
		
		btnAButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("add "+ textField.getText().split("\\|")[0]);
				textArea.append("Trying to add the word " + textField.getText().split("\\|")[0]+" in server...\n");
				try {
					Socket socket = new Socket(ip, port);
					DataInputStream input = new DataInputStream(socket.getInputStream());
				    DataOutputStream output = new DataOutputStream(socket.getOutputStream());
					String sendData = "A" + textField.getText();
					output.writeUTF(sendData);
					output.flush();
					textArea.append(input.readUTF() + "\n");
					socket.close();
				} catch (Exception e) {
					textArea.append("ERROR: Connection error! Please check the server status!\n");
				}
			}
		});
		
		btnRButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("remove "+ textField.getText());
				textArea.append("Trying to remove the word " + textField.getText()+" in server...\n");
				try {
					Socket socket = new Socket(ip, port);
					DataInputStream input = new DataInputStream(socket.getInputStream());
				    DataOutputStream output = new DataOutputStream(socket.getOutputStream());
					String sendData = "R" + textField.getText();
					output.writeUTF(sendData);
					output.flush();
					textArea.append(input.readUTF() + "\n");
					socket.close();
				} catch (Exception e) {
					textArea.append("ERROR: Connection error! Please check the server status!\n");
				}
			}
		});
		
		btnUButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("update "+ textField.getText().split("\\|")[0]);
				textArea.append("Trying to update the word " + textField.getText().split("\\|")[0]+" in server...\n");
				try {
					Socket socket = new Socket(ip, port);
					DataInputStream input = new DataInputStream(socket.getInputStream());
				    DataOutputStream output = new DataOutputStream(socket.getOutputStream());
					String sendData = "U" + textField.getText();
					output.writeUTF(sendData);
					output.flush();
					textArea.append(input.readUTF() + "\n");
					socket.close();
				} catch (Exception e) {
					textArea.append("ERROR: Connection error! Please check the server status!\n");
				}
			}
		});
		
		btnSButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("save");
				textArea.append("Trying to save the dictionary on the server...\n");
				try {
					Socket socket = new Socket(ip, port);
					DataInputStream input = new DataInputStream(socket.getInputStream());
				    DataOutputStream output = new DataOutputStream(socket.getOutputStream());
					String sendData = "S" + textField.getText();
					output.writeUTF(sendData);
					output.flush();
					textArea.append(input.readUTF() + "\n");
					socket.close();
				} catch (Exception e) {
					textArea.append("ERROR: Connection error! Please check the server status!\n");
				}
			}
		});
	}

}

//Jialiang Cheng 1251403
package client;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;





public class Client {
	
	public JFrame frame;
	public JTextField textField;
	public JTextArea textArea;
	public JTextArea listArea;
	public String cur = "Line";
	public int curC = 0;
	public String texts = " ";
	public Socket socket;
	Color color1 = new Color (50,50,150);
	Color color2 = new Color (150,50,50);
	Color color3 = new Color (50,150,50);
	Color[] colors = {Color.black,Color.blue,Color.cyan,Color.darkGray,Color.gray,Color.green,Color.lightGray,Color.magenta,Color.orange,Color.pink,Color.red,Color.white,Color.yellow,color1,color2,color3};
	public Graphics g;
	public DataInputStream input;
	public DataOutputStream output;
	private int[] point = new int[4];
	public JPanel paintBoard;
	
	public static void main(String[] args) throws UnknownHostException, IOException 
	{
		if(args.length!=3){
			System.err.println("<host> <server-port><username> are required");
			System.exit(1);
		}
		
		String ip = args[0];
		int port = Integer.parseInt(args[1]);
		String username = args[2];
		Client c = new Client();
		c.create(ip, port, username);
	}
	public void create(String ip, int port, String name) throws IOException {
		
		try {
		socket = new Socket(ip, port);
		input = new DataInputStream(socket.getInputStream());
		output = new DataOutputStream(socket.getOutputStream());
		output.writeUTF(name);
		output.flush();
		} catch (Exception e) {
			System.out.println("connect fail, please check the server");
		}
	
		frame = new JFrame("Client");
		frame.setSize(1000, 800);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWeights = new double[]{1.0};
		gridBagLayout.rowWeights = new double[]{1.0, 0.1, 1.0};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		JMenu menu = new JMenu("Edit");
		JMenuItem help = new JMenuItem("new");
		menu.add(help);
		menuBar.add(menu);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 1;
		frame.getContentPane().add(textField, gbc_textField);
		
		paintBoard=new JPanel();
		paintBoard.setBackground(Color.white);
		GridBagConstraints gbc_paintField = new GridBagConstraints();
		gbc_paintField.insets = new Insets(0, 0, 3, 0);
		gbc_paintField.fill = GridBagConstraints.BOTH;
		gbc_paintField.gridx = 0;
		gbc_paintField.gridy = 0;
		frame.getContentPane().add(paintBoard, gbc_paintField);
		
		help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				paintBoard.paint(g);
				try {
					output.writeUTF("P");
					output.flush();
				} catch (IOException ex) {
					textArea.append("ERROR: Connection error! Please check the server status!\n");
				}
			}
        });
		
		JButton btnLButton = new JButton("Line");
		GridBagConstraints gbc_btnLButton = new GridBagConstraints();
		gbc_btnLButton.insets = new Insets(-500, 0, 0, 0);
		gbc_btnLButton.gridx = 1;
		gbc_btnLButton.gridy = 0;
		frame.getContentPane().add(btnLButton, gbc_btnLButton);
		btnLButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cur = "Line";
				System.out.println("cur change to "+cur);
			}
        });
		
		JButton btnCButton = new JButton("Circle");
		GridBagConstraints gbc_btnCButton = new GridBagConstraints();
		gbc_btnCButton.insets = new Insets(-420, 0, 0, 0);
		gbc_btnCButton.gridx = 1;
		gbc_btnCButton.gridy = 0;
		frame.getContentPane().add(btnCButton, gbc_btnCButton);
		btnCButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cur = "Circle";
				System.out.println("cur change to "+cur);
			}
        });
		
		JButton btnOButton = new JButton("Oval");
		GridBagConstraints gbc_btnOButton = new GridBagConstraints();
		gbc_btnOButton.insets = new Insets(-340, 0, 0, 0);
		gbc_btnOButton.gridx = 1;
		gbc_btnOButton.gridy = 0;
		frame.getContentPane().add(btnOButton, gbc_btnOButton);
		btnOButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cur = "Oval";
				System.out.println("cur change to "+cur);
			}
        });
		
		JButton btnRButton = new JButton("Rectangle");
		GridBagConstraints gbc_btnRButton = new GridBagConstraints();
		gbc_btnRButton.insets = new Insets(-260, 0, 0, 0);
		gbc_btnRButton.gridx = 1;
		gbc_btnRButton.gridy = 0;
		frame.getContentPane().add(btnRButton, gbc_btnRButton);
		btnRButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cur = "Rectangle";
				System.out.println("cur change to "+cur);
			}
        });
		
		JButton btnTButton = new JButton("Text");
		GridBagConstraints gbc_btnTButton = new GridBagConstraints();
		gbc_btnTButton.insets = new Insets(-180, 0, 0, 0);
		gbc_btnTButton.gridx = 1;
		gbc_btnTButton.gridy = 0;
		frame.getContentPane().add(btnTButton, gbc_btnTButton);
		btnTButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cur = "Text";
				texts = textField.getText();
				System.out.println("cur change to "+cur);
			}
        });
		
		JButton btnSButton = new JButton("Send");
		GridBagConstraints gbc_btnSButton = new GridBagConstraints();
		gbc_btnSButton.gridx = 1;
		gbc_btnSButton.gridy = 1;
		frame.getContentPane().add(btnSButton, gbc_btnSButton);
		
		JButton[]CjbtList=new JButton[colors.length];
		GridBagConstraints[]CgcbList=new GridBagConstraints[colors.length];
		for(int i=0;i<colors.length;i++){
			CjbtList[i]=new JButton();
			CjbtList[i].setActionCommand("c"+i);
			CjbtList[i].setBackground(colors[i]);
			CgcbList[i] = new GridBagConstraints();
			CgcbList[i].insets = new Insets(-120+i*40, 0, 0, 0);
			CgcbList[i].gridx = 1;
			CgcbList[i].gridy = 0;
			frame.getContentPane().add(CjbtList[i], CgcbList[i]);
		}
		
		textArea = new JTextArea();
		JScrollPane scroll = new JScrollPane(textArea); 
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridx = 0;
		gbc_textArea.gridy = 2;
		textArea.setEditable(false);
		frame.getContentPane().add(scroll, gbc_textArea);
		
		listArea = new JTextArea();
		JScrollPane listscroll = new JScrollPane(listArea); 
		GridBagConstraints gbc_listArea = new GridBagConstraints();
		gbc_listArea.insets = new Insets(0, 0, 0, 0);
		gbc_listArea.fill = GridBagConstraints.BOTH;
		gbc_listArea.gridx = 1;
		gbc_listArea.gridy = 2;         
		listArea.setEditable(false);
		frame.getContentPane().add(listscroll, gbc_listArea);

		frame.setVisible(true);
		
		Receive rec=new Receive();
		rec.start();
		
		g = paintBoard.getGraphics();
		for(int i=0;i<colors.length;i++){
			int pos = i;
			Color c = colors[i];
			CjbtList[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					curC = pos;
					g.setColor(c);
					System.out.println("color change to "+c);
				}
	        });
		}
		
		btnSButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String sendData = "S" + name + "|" +textField.getText();
				try {
					output.writeUTF(sendData);
					output.flush();
				} catch (IOException e) {
					textArea.append("ERROR: Connection error! Please check the server status!\n");
				}
				System.out.println("Send message: "+sendData);
			}
        });
		MListener lis=new MListener(textArea);
		
		paintBoard.addMouseListener(lis);
		paintBoard.addMouseMotionListener(lis);

	}
	class MListener implements MouseListener,MouseMotionListener{
		private JTextArea textArea;
		
		public MListener(JTextArea textArea){
			this.textArea=textArea;
		}
		
		public void mouseClicked(MouseEvent e) {
			
		}

		public void mousePressed(MouseEvent e) {
			point[0]=e.getX();
			point[1]=e.getY();
		}

		public void mouseReleased(MouseEvent e) {
			point[2]=e.getX();
			point[3]=e.getY();
			
			switch(cur){
			case"Line":
				System.out.println("Drawing Line...");
				g.drawLine(point[0],point[1],point[2],point[3]);
				try {
					output.writeUTF("L"+ curC + "|" + point[0]+"|"+point[1]+"|"+point[2]+"|"+point[3]);
					output.flush();
				} catch (IOException ex) {
					textArea.append("ERROR: Connection error! Please check the server status!\n");
				}
				break;
			case"Circle":
				System.out.println("Drawing Circle...");
				int x1=Math.min(point[0],point[2]);
				int y1=Math.min(point[1],point[3]);
				int width=Math.abs(point[0]-point[2]);
				int height=Math.abs(point[1]-point[3]);
				int d = Math.min(width, height);
				point[0]=x1;
				point[1]=y1;
				point[2]=d;
				point[3]=d;
				g.fillOval(point[0],point[1],point[2],point[3]);
				try {
					output.writeUTF("Y"+ curC + "|" + point[0]+"|"+point[1]+"|"+point[2]+"|"+point[3]);
					output.flush();
				} catch (IOException ex) {
					textArea.append("ERROR: Connection error! Please check the server status!\n");
				}
				break;
			case"Oval":
				System.out.println("Drawing Oval...");
				x1=Math.min(point[0],point[2]);
				y1=Math.min(point[1],point[3]);
				width=Math.abs(point[0]-point[2]);
				height=Math.abs(point[1]-point[3]);
				point[0]=x1;
				point[1]=y1;
				point[2]=width;
				point[3]=height;
				g.fillOval(point[0],point[1],point[2],point[3]);
				try {
					output.writeUTF("O"+ curC + "|" + point[0]+"|"+point[1]+"|"+point[2]+"|"+point[3]);
					output.flush();
				} catch (IOException ex) {
					textArea.append("ERROR: Connection error! Please check the server status!\n");
				}
				break;
			case"Rectangle":
				System.out.println("Drawing Rectangle...");
				x1=Math.min(point[0],point[2]);
				y1=Math.min(point[1],point[3]);
				width=Math.abs(point[0]-point[2]);
				height=Math.abs(point[1]-point[3]);
				point[0]=x1;
				point[1]=y1;
				point[2]=width;
				point[3]=height;
				g.fillRect(point[0],point[1],point[2],point[3]);
				try {
					output.writeUTF("R"+ curC + "|" + point[0]+"|"+point[1]+"|"+point[2]+"|"+point[3]);
					output.flush();
				} catch (IOException ex) {
					textArea.append("ERROR: Connection error! Please check the server status!\n");
				}
				break;
			case"Text":
				System.out.println("Drawing Text...");
				x1=Math.min(point[0],point[2]);
				y1=Math.min(point[1],point[3]);
				point[0]=x1;
				point[1]=y1;
				g.drawString(texts,point[0],point[1]);
				try {
					output.writeUTF("T"+ curC + "|" + point[0]+"|"+point[1]+"|"+texts+" ");
					output.flush();
				} catch (IOException ex) {
					textArea.append("ERROR: Connection error! Please check the server status!\n");
				}
				break;
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			
		}

		public void mouseDragged(MouseEvent e) {
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			
		}

	}
	
	class Receive extends Thread{
		
		public void run(){
				while(true){
					try {
						String inputS = input.readUTF();
						String[] points = inputS.substring(1).split("\\|");
						switch((inputS.charAt(0))){
						case'L':
							g.setColor(colors[Integer.parseInt(points[0])]);
							g.drawLine(Integer.parseInt(points[1]),Integer.parseInt(points[2]),Integer.parseInt(points[3]),Integer.parseInt(points[4]));
							g.setColor(colors[curC]);
							break;
						case'Y':
							g.setColor(colors[Integer.parseInt(points[0])]);
							g.fillOval(Integer.parseInt(points[1]),Integer.parseInt(points[2]),Integer.parseInt(points[3]),Integer.parseInt(points[4]));
							g.setColor(colors[curC]);
							break;
						case'O':
							g.setColor(colors[Integer.parseInt(points[0])]);
							g.fillOval(Integer.parseInt(points[1]),Integer.parseInt(points[2]),Integer.parseInt(points[3]),Integer.parseInt(points[4]));
							g.setColor(colors[curC]);
							break;
						case'R':
							g.setColor(colors[Integer.parseInt(points[0])]);
							g.fillRect(Integer.parseInt(points[1]),Integer.parseInt(points[2]),Integer.parseInt(points[3]),Integer.parseInt(points[4]));
							g.setColor(colors[curC]);
							break;
						case'T':
							g.setColor(colors[Integer.parseInt(points[0])]);
							g.drawString(points[3],Integer.parseInt(points[1]),Integer.parseInt(points[2]));
							g.setColor(colors[curC]);
							break;
						case'S':
							textArea.append(points[0]+":\n\r"+points[1] + "\n\r");
							break;
						case'N':
							listArea.setText("");
							for (int i = 1; i < points.length; i++) {
								listArea.append(points[i]+"\n\r");
							}
							break;
						case'P':
							paintBoard.paint(g);
							break;
						}
					} catch (IOException e) {
						textArea.append("Connet error!");
					}
			}
		}


	}
}

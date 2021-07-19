//Jialiang Cheng 1251403
package client;
import java.awt.EventQueue;


public class Client {
	
	// IP and port
	
	public static void main(String[] args) 
	{
		if(args.length!=2){
			System.err.println("<host> <server-port> are required");
			System.exit(1);
		}
		
		String ip = args[0];
		int port = Integer.parseInt(args[1]);
		
    	EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow(ip, port);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		    

	}

}

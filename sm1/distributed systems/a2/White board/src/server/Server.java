//Jialiang Cheng 1251403
package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
	public ArrayList<String>names=new ArrayList<>();
	public ArrayList<Integer>counts=new ArrayList<>();
	public ArrayList<Socket>sockets=new ArrayList<>();
	public ArrayList<DataInputStream>inputs=new ArrayList<>();
	public ArrayList<DataOutputStream>outputs=new ArrayList<>();
	
	// Declare the port number
	
	// Identifies the user number connected

	public static void main(String[] args) throws Exception
	{	
		Server ser = new Server();
		ser.create(args);
	}
	public void create(String[] args) throws Exception {
		
		int count = 0;
		
		if(args.length!=1){
			System.err.println("<port> are required");
			System.exit(1);
		}
		
		int port = Integer.parseInt(args[0]);
        
		try(ServerSocket server = new ServerSocket(port))
		{
			System.out.println("The server has started...");
			
			// Wait for connections.
			while(true)
			{
				Socket soc=server.accept();
				sockets.add(soc);
				
				DataInputStream input= new DataInputStream(soc.getInputStream());
				DataOutputStream output= new DataOutputStream(soc.getOutputStream());
				String name = input.readUTF();

				inputs.add(input);
				outputs.add(output);
				names.add(name);
				counts.add(count);
				
				String curmember = "N";
				for (int i = 0; i < names.size(); i++) {
					curmember = curmember + "|" + names.get(i);
					
				}
				for (int i = 0; i < outputs.size(); i++) {
					outputs.get(i).writeUTF(curmember);
					outputs.get(i).writeUTF("SServer|Client "+name+" has connected.");
				}
				System.out.println("client "+count+" has connected!");
				
				MessageChannel channel=new MessageChannel(input,count,name);
				channel.start();
				count++;
			}
			
		} 
		catch (Exception e)
		{
			System.out.println("EEROR: Got error with create the socket, please restart the server with another port");
			return;
		}
	}
	class MessageChannel extends Thread{
		private DataInputStream input;
		private int no;
		private String name;
		
		public MessageChannel(DataInputStream input, int count, String name){
			this.input=input;
			no = count;
			this.name = name;
		}
		
		
		public void run(){
				while(true){
					try {
						String inputS = input.readUTF();
						for (int i = 0; i < outputs.size(); i++) {
							outputs.get(i).writeUTF(inputS);
						}
					} catch (Exception e) {
						int pos = -1;
						for(int i = 0; i <counts.size();i++) {
							if (no == counts.get(i)) {
								pos = i;
							}
						}
						inputs.remove(pos);
						outputs.remove(pos);
						names.remove(pos);
						sockets.remove(pos);
						counts.remove(pos);
						String curmember = "N";
						for (int i = 0; i < names.size(); i++) {
							curmember = curmember + "|" + names.get(i);
							
						}
						for (int i = 0; i < outputs.size(); i++) {
							try {
								outputs.get(i).writeUTF(curmember);
								outputs.get(i).writeUTF("SServer|Client "+name+" has quit.");
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
						return;
					}
					
			}
		}
	}

}

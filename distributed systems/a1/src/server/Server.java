//Jialiang Cheng 1251403
package server;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.net.ServerSocketFactory;

public class Server {
	
	// Declare the port number
	
	// Identifies the user number connected

	public static void main(String[] args) throws Exception
	{	
		if(args.length!=2){
			System.err.println("<port> <dictionary-file> are required");
			System.exit(1);
		}
		
		int port = Integer.parseInt(args[0]);
		
		String dpath = args[1];
		
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(dpath));
		}
		catch (Exception e) {
			System.out.println("EEROR: Got error with the target file, please restart the server with correct file!");
			return;
		}
        String str;
        List<String> wlist = new ArrayList<String>();
        List<String> mlist = new ArrayList<String>();
        while ((str = in.readLine()) != null) {
        	String[] strArr = str.split("\\|");
        	wlist.add(strArr[0]);
        	mlist.add(strArr[1]);
        }
        in.close();
        
		ServerSocketFactory factory = ServerSocketFactory.getDefault();
		
		try(ServerSocket server = factory.createServerSocket(port))
		{
			System.out.println("The dictionary server has started...");
			
			// Wait for connections.
			while(true)
			{
				Socket client = server.accept();				
				// Start a new thread for a connection
				Thread t = new Thread(() -> serveClient(client,wlist,mlist,dpath));
				t.start();
				t.join();
			}
			
		} 
		catch (Exception e)
		{
			System.out.println("EEROR: Got error with create the socket, please restart the server with another port");
			return;
		}
		
	}
	
	private static List<List<String>> serveClient(Socket client,List<String> wlist,List<String> mlist,String dpath)
	{

		try(Socket clientSocket = client)
		{
			// Input stream
			DataInputStream input = new DataInputStream(clientSocket.getInputStream());
			// Output Stream
		    DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
		    
		    String clientInputStr = input.readUTF();
		    
	    	if (clientInputStr.charAt(0) == 'Q') {
	    		String target_w = clientInputStr.substring(1);
	    		int pos = wlist.indexOf(target_w);
	    		if (pos == -1) {
	    			output.writeUTF("Error:The query word does not exsit in dictionary!");
	    		} else {
	    			output.writeUTF("The meaning of the query word is: "+mlist.get(pos));
	    		}
	    	} else if (clientInputStr.charAt(0) == 'A') {
	    		String[] strArr = clientInputStr.substring(1).split("\\|");
	    		if (strArr.length != 2) {
	    			output.writeUTF("Error:The add command does not follow the format!");
	    		} else {
		    		String target_w = strArr[0];
		    		int pos = wlist.indexOf(target_w);
		    		if (pos != -1) {
		    			output.writeUTF("Error:The add word already exsit in dictionary!");
		    		} else {
		    			wlist.add(target_w);
		    			mlist.add(strArr[1]);
		    			output.writeUTF("The word " + target_w + " has been add to dictionary.");
		    		}
	    		}
	    	} else if (clientInputStr.charAt(0) == 'R') {
	    		String target_w = clientInputStr.substring(1);
	    		int pos = wlist.indexOf(target_w);
	    		if (pos == -1) {
	    			output.writeUTF("Error:The remove word does not exsit in dictionary!");
	    		} else {
	    			wlist.remove(pos);
	    			mlist.remove(pos);
	    			output.writeUTF("The word " + target_w + " has been removed");
	    		}
	    	} else if (clientInputStr.charAt(0) == 'U') {
	    		String[] strArr = clientInputStr.substring(1).split("\\|");
	    		String target_w = strArr[0];
	    		int pos = wlist.indexOf(target_w);
	    		if (pos == -1) {
	    			output.writeUTF("Error:The update word dose not exsit in dictionary!");
	    		} else {
	    			mlist.set(pos,strArr[1]);
	    			output.writeUTF("The word " + target_w + " has been update in dictionary.");
	    		}
	    	} else if (clientInputStr.charAt(0) == 'S') {
	    		try {
	                BufferedWriter out = new BufferedWriter(new FileWriter(dpath));
	                for (int i = 0; i < wlist.size(); i++) {
	                	out.write(wlist.get(i) + "|" + mlist.get(i) + "\n");
	                }
	                out.close();
	                output.writeUTF("Saving success!");
	            } catch (Exception e) {
	            	output.writeUTF("Saving failed, because: "+ e);
	            }
	    	} else {
	    		output.writeUTF("Wrong input!");
	    	}
			System.out.println("Now we have "+wlist.size()+" words in the dictionary");
		    List<List<String>> rlist = new ArrayList<List<String>>();
		    rlist.add(wlist);
		    rlist.add(mlist);
		    return rlist;
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return null;
	}

}

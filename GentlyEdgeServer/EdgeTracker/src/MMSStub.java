
import java.util.*;
import java.io.*;
import java.net.*;

class MMSStub {

    public static void main (String args[]) throws Exception {
		// throws Exception here because don't want to deal
		// with errors in the rest of the code for simplicity.
		// (Note: NOT a good practice).
		
         //Welcome socket  ---- SOCKET 1
		 ServerSocket serverSocket = new ServerSocket(9000);
		 // waits for a new connection. Accepts connetion from multiple clients
		 while (true) 
		 {
			 System.out.println("waiting for connection at 9000");
			 
             //Connection socket  --- SOCKET 2
			 Socket s = serverSocket.accept();
			 System.out.println("connection established from " + s.getInetAddress());
			 System.out.println("Connection Definition " + s.toString());
			 
			 ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
			 EdgeServerTransferObject edgeTransferObject = (EdgeServerTransferObject) ois.readObject();  

			 //print output then eyeball output for correctness
			 System.out.println("server name is " + edgeTransferObject.getServerName());
			 ArrayList<Streamer> tempArList = edgeTransferObject.getStreamers();
			 for(int j = 0; j < tempArList.size(); j++){
			 	Streamer tempStream = tempArList.get(j);
			 	System.out.println("number of viewers is " + tempStream.getNviewers());
			 	System.out.println("pageurl is " + tempStream.getPageurl());
			 	System.out.println("streamkey is " + tempStream.getStreamkey());
			 }
			 
			// close current connection
			s.close();
		 }
    }
}

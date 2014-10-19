//
//  TCPServerMultiThread.java
//
//  Bhojan Anand
//
package gently;

import java.util.*;
import java.io.*;
import java.net.*;

class TCPServerMultiThread {

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
			 
			 //Construct an instance of HttpRequestProcessor to process this request
			 tcpRequestProcessor reqProcessor = new tcpRequestProcessor(s);
			 
			 //Create a new thread to process the request
			 Thread thread = new Thread(reqProcessor);
			 
			 //Start the thread.
			 thread.start();	
			 
			}
    }
}


final class tcpRequestProcessor implements Runnable{
	
	Socket s;
	
	//Constructor
    public tcpRequestProcessor(Socket socket) throws Exception 
	{
		// throws Exception here because don't want to deal
		// with errors in the rest of the code for simplicity.
		
		this.s = socket;
		
	}
	
	public void run() {
		try{
			// create a BufferedReader object to read strings from
			// the socket. (read strings FROM CLIENT)
			BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			String input = br.readLine();
			
			//create output stream to write to/send TO CLINET
			DataOutputStream output = new DataOutputStream(s.getOutputStream());
			
			// keep repeating until an empty line is read.
			while (input.compareTo("") != 0) {
				// convert input to upper case and echo back to
				// client.
				output.writeBytes(input.toUpperCase() + "\n");
				input = br.readLine();
			}
			// close current connection
			s.close();
			
		}catch (Exception ex){
			System.out.println(ex);
		}
	}	
	
}
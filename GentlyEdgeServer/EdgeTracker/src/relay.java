import java.util.*;
import java.io.*;
import java.net.*;


public class relay {
	public static void main (String args[]) throws Exception {
		// throws Exception here because don't want to deal
		// with errors in the rest of the code for simplicity.
		// (Note: NOT a good practice).

		//Welcome socket  ---- SOCKET 1
		ServerSocket serverSocket = new ServerSocket(9001);
		// waits for a new connection. Accepts connection from multiple clients
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
			System.out.println(input);
			// keep repeating until an empty line is read.
				if (input.contains("$")) {
					System.out.println("in-1");
					String[] parts = input.split("\\$");
					System.out.println(parts.length);
					if (parts.length >= 3) {
						System.out.println("in-2");
						if (parts[0].equals("STARTRELAY")){
							System.out.println("in-3");
							relayStream (parts[1], parts[2]);			
						}
					}
				}
			// close current connection
			s.close();

		}catch (Exception ex){
			System.out.println(ex);
		}
	}	



	static void relayStream (String streamKey, String destination) throws IOException {
		String startStream = "rtmp://kiangkuang-i.comp.nus.edu.sg:1935/live1/" + streamKey;
		String endStream = "rtmp://" + destination + ":1935/live1/" + streamKey; 	
		System.out.println(endStream);
		String[] cmd = {"C:/Users/a0096677/Desktop/nginx_1.7.4_rtmp_1.1.4/ffmpeg/bin/ffmpeg", "-i", startStream, "-vcodec", "flv", "-acodec", "copy", "-s", "1920x1200", "-r", "20", "-f", "flv", endStream};
		Runtime.getRuntime().exec(cmd);	
		System.out.println("relayed");
	}

}

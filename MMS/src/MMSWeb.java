import java.util.*;
import java.io.*;
import java.net.*;

class MMSWeb implements Runnable {
    
    private static ServerSocket _serverSocket;
    
    //Constructor
    MMSWeb(int portNo) throws IOException {
	_serverSocket = new ServerSocket(portNo); 
    }
    
    public void run() {

	while (true) { 
	    Socket s = null;
	    
	    try {
		System.out.println("Waiting for connection at " + _serverSocket.getLocalPort());
		s = _serverSocket.accept();
		System.out.println("Connection established from " + s.getInetAddress());
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
			 
	    MMSWebRequestProcessor reqProcessor = null;
	    
	    try {
		reqProcessor = new MMSWebRequestProcessor(s);
	    } catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
			 
	    Thread thread = new Thread(reqProcessor);
			 
	    thread.start();	
			 
	}
    }
}



final class MMSWebRequestProcessor implements Runnable{
	
	Socket s;
	
	//Constructor
    public MMSWebRequestProcessor(Socket socket) throws Exception {
	this.s = socket;
    }
	
    public void run() {
	try{
		
	    //Create a reader to read from webComponent
	    BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
	    String input = br.readLine();
		System.out.println("Input	: " + input);
	    //Create outputstream to return to reader
	    DataOutputStream output = new DataOutputStream(s.getOutputStream());
		
	    String[] splitInput = input.split(" ");
	    String clientIP = splitInput[0];
	    String streamerID = splitInput[1];
	    String serverIP;
	    InternalMemory mem = InternalMemory.getInstance();
	    
//	    String location = mem.getLocationOfIP(clientIP);
//	    ArrayList<Server> preferenceList = null;
//	    ArrayList<Server> serversWithStream = mem.getExistingServersStreaming(streamerID);
	    switch (streamerID) {
		case "user1":
			serverIP = "rtmp://mediatech-i.comp.nus.edu.sg:1935/live1/flv:123";
			break;
		case "user2":
			serverIP = "rtmp://mediatech-i.comp.nus.edu.sg:1935/live1/flv:456";
			break;
		case "user3":
			serverIP = "rtmp://mediatech-i.comp.nus.edu.sg:1935/live1/flv:789";
			break;
		case "user4":
			serverIP = "rtmp://mediatech-i.comp.nus.edu.sg:1935/live1/flv:321";
			break;
		case "user5":
			serverIP = "rtmp://mediatech-i.comp.nus.edu.sg:1935/live1/flv:654";
			break;
		case "user6":
			serverIP = "rtmp://mediatech-i.comp.nus.edu.sg:1935/live1/flv:987";
			break;
		default:
			serverIP = "rtmp://mediatech-i.comp.nus.edu.sg:1935/live1/flv:123";
			break;
		}
	    serverIP += "\n";
//	    if (serversWithStream != null) {
//		 for (Server s : preferenceList) {
//			boolean isSuccessful = s.isSuccessfulInAddingUser();
//			
//			if (isSuccessful) {
//			    //contact server s to get stream
//			    
//			    serversWithStream.add(s);
//			    serverIP = s.getServerIP();
//			    break;
//			}
//		 }
//	    }
	    
	    if (serverIP == null) {
	    	//error in allocation
	    } else {
	    	output.writeBytes(serverIP);
	    	System.out.println("Output	: " + serverIP);
	    }
	    s.close();
	    
	} catch (Exception ex){
	    ex.printStackTrace();
	}
    }	
}
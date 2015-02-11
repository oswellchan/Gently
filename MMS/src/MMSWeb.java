import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.io.*;
import java.net.*;

import javafx.application.Platform;

class MMSWeb implements Runnable {
    
    private static ServerSocket _serverSocket;
    private static final String MSG_LISTENING = "Web port waiting for connection at port %1$s. \r";
    private static final String MSG_ESTABLISHED = "Received connection from %1$s. \r";
    GUIController controller;
    
    //Constructor
    MMSWeb(int portNo) throws IOException {
	_serverSocket = new ServerSocket(portNo); 
	controller = GUIController.getInstance();
    }
    
    public void run() {

	while (true) { 
	    Socket s = null;
	    
	    try {
		final String res = String.format(MSG_LISTENING, _serverSocket.getLocalPort());
		
		Platform.runLater(new Runnable() {
    		
    		@Override
    		public void run() {
    			controller.outputText(res);
    		}
    	});
		
		
		s = _serverSocket.accept();
		
		final String res2 = String.format(MSG_ESTABLISHED, _serverSocket.getInetAddress());
		
		Platform.runLater(new Runnable() {
    		
    		@Override
    		public void run() {
    			controller.outputText(res2);
    		}
    	});
		
	    } catch (Exception e) {
		
		if (e instanceof InterruptedException) {
		    System.out.println("exit");
		} else {
		    final String res3 = e.getMessage();
		    
		    Platform.runLater(new Runnable() {
	    		
	    		@Override
	    		public void run() {
	    			controller.outputText(res3);
	    		}
	    	});
		}
		
	    }
			 
	    MMSWebRequestProcessor reqProcessor = null;
	    
	    try {
		reqProcessor = new MMSWebRequestProcessor(s);
	    } catch (Exception e) {
		final String res4 = e.getMessage();
		Platform.runLater(new Runnable() {
    		
    		@Override
    		public void run() {
    			controller.outputText(res4);
    		}
    	});
	    }
			 
	    Thread thread = new Thread(reqProcessor);
	    
	    thread.start();	
	    
	}
    }
}



final class MMSWebRequestProcessor implements Runnable{
	
	Socket s;
	GUIController controller;
	
	//Constructor
    public MMSWebRequestProcessor(Socket socket) throws Exception {
	this.s = socket;
	controller = GUIController.getInstance();
    }
	
    public void run() {
	try{
		
	    //Create a reader to read from webComponent
	    BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
	    String input = br.readLine();
	    
	    //Create outputstream to return to reader
	    DataOutputStream output = new DataOutputStream(s.getOutputStream());
	    
	    //controller.outputText(input);
		
	    String[] splitInput = input.split(" ");
	    String clientIP = splitInput[0];
	    String streamerID = splitInput[1];
	    //String clientLocation = getLocationOfIPaddress(clientIP);
	    
	    InternalMemory mem = InternalMemory.getInstance();
	    
	    String sources = mem.getStreamSourcesByID(streamerID);
	    
	    // temp hard coded links
	    sources = "https://www.youtube.com/watch?v=ImXuRHAhgKA#t=10 https://www.youtube.com/watch?v=MiBT7PfAOqY https://www.youtube.com/watch?v=m-GaPdI2u28#t=25";
	    
	    output.writeBytes(sources + "\n");
	    
	    //controller.outputText(sources);
	    
	    s.close();
	    
	} catch (Exception ex){
	    ex.printStackTrace();
	}
    }	
    
    //Given a IP, return the geo-location of that IP address
    private static String getLocationOfIPaddress(String clientIP) {
   		Connection conn;
       	try {
       		conn = DriverManager.getConnection("jdbc:mysql://localhost/gently", "root", "");
       		
       		double ipNum = convertToIPNumber(clientIP);
       		
       		String query = "SELECT `country_name` FROM `ip2location_db1` WHERE "
       				+ "(`ip_from` <=" + ipNum + ") AND (`ip_to` >=" + ipNum + "16785407)";
       		Statement stmt = conn.createStatement();
       		ResultSet rs = stmt.executeQuery(query);
       		
       		while(rs.next()) {
       			return rs.getString("country_name");
       		} 
       		
       	} catch (Exception e) {
       		System.err.println(e);
       	}
       	
		return null;
   	}
    
    //for IPV4
    public static double convertToIPNumber(String IPAddress) {
    	
    	String[] arrDec;
    	double IPNum = 0;
    	
    	
    	if (IPAddress == "") {
    		return 0;
    	} else {
    		arrDec = IPAddress.split("\\.");
    		for (int i=0; i<arrDec.length; i++) {
    			IPNum += ((Integer.parseInt(arrDec[i])%256) * Math.pow(256 ,(3 - i )));
    		}
 
    		return IPNum;
    	}
    	
    }
}
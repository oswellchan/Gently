import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.application.Platform;

public class MMSEdge implements Runnable{
    
    private static ServerSocket _serverSocket;
    private static final String MSG_LISTENING = "Server port waiting for connection at port %1$s. \r";
    private static final String MSG_ESTABLISHED = "Received connection from %1$s. \r";
    GUIController controller;
    
    //Constructor
    MMSEdge(int portNo) throws IOException {
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
			 
	    MMSEdgeRequestProcessor reqProcessor = null;
	    
	    try {
		reqProcessor = new MMSEdgeRequestProcessor(s);
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

final class MMSEdgeRequestProcessor implements Runnable{
	
	Socket s;
	GUIController controller;
	
	//Constructor
	public MMSEdgeRequestProcessor(Socket socket) throws Exception {
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
	    
		controller.outputText(input);
	    
		s.close();
	    
	    } catch (Exception ex){
		ex.printStackTrace();
	    }
	}	
}
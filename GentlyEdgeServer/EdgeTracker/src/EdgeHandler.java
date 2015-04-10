import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
	

public class EdgeHandler {
	public static final String EXTRACT = "e";
	public static final String MMSURL = "mediatech-i.comp.nus.edu.sg";
	//localhost used for testing with MMSStub
	//public static final String MMSURL = "localhost";
	public static final int PORTNUMBER = 9000;
	public static final int EMPTYLINE = 0;

	public static final int SECONDS = 5;
	
	//for logging
	private final static Logger LOGGER = Logger.getLogger(EdgeHandler.class.getName());
	private static Handler fh = null;

	//Object to be sent to MMS
    private static EdgeServerTransferObject edgeTransferObject = new EdgeServerTransferObject();
    
    class ExtractTask extends TimerTask{
    	EdgeTracker edgeTracker = null;
    	//ObjectOutputStream serverWriter;
    	Socket s;
    	
    	public ExtractTask(EdgeTracker edgeTracker, Socket s){
    		this.edgeTracker = edgeTracker;
    		//this.serverWriter = serverWriter;
    		this.s = s;

    	}
    	
    	public ExtractTask(EdgeTracker edgeTracker){
    		this.edgeTracker = edgeTracker;
    	}
    	
    	
		public void run() {
			edgeTransferObject = edgeTracker.trackStatus();
			try {
				Socket s = new Socket(MMSURL, PORTNUMBER);
				OutputStream os = s.getOutputStream();
				ObjectOutputStream serverWriter = new ObjectOutputStream(os);
				
				// isrServer and serverReader to be used during later stage of implementation  
				//InputStreamReader isrServer = new InputStreamReader(s.getInputStream());
				//BufferedReader serverReader = new BufferedReader(isrServer);

				//inFromUser and sentence to be used during later stage of implementation
		        //BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		        //String sentence;
		        //sentence = inFromUser.readLine();
				
				serverWriter.writeObject(edgeTransferObject);
				
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
				LOGGER.log(Level.SEVERE, e.toString());
			}
			
			
		}
    }
    
	public static void main (String args[]) {
		try {
			fh = new FileHandler("edgehandler.log", true); 
			LOGGER.addHandler(fh);
			LOGGER.setLevel(Level.SEVERE);
			
			EdgeTracker edgeTracker = new EdgeTracker();
			
	        Timer timer = new Timer();
	        EdgeHandler handler = new EdgeHandler();
	        ExtractTask extract = handler.new ExtractTask(edgeTracker);
	        timer.schedule(extract, 0, SECONDS * 1000);
	       
		} catch (UnknownHostException e) {
			e.printStackTrace();
			LOGGER.log(Level.SEVERE, e.toString());
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.log(Level.SEVERE, e.toString());
		} finally {
			fh.close();
		}
	}
	
}


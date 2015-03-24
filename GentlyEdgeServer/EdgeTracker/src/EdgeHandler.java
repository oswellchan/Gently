import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
	

public class EdgeHandler {
	public static final String EXTRACT = "e";
	public static final String MMSURL = "mediatech-i.comp.nus.edu.sg";
	public static final int PORTNUMBER = 9000;
	public static final int EMPTYLINE = 0;

	//for logging
	private final static Logger LOGGER = Logger.getLogger(EdgeHandler.class.getName());
	private static Handler fh = null;

	//Object to be sent to MMS
    private static EdgeServerTransferObject edgeTransferObject = new EdgeServerTransferObject();
    
	public static void main (String args[]) {
		try {
			fh = new FileHandler("edgehandler.log", true); 
			LOGGER.addHandler(fh);
			LOGGER.setLevel(Level.SEVERE);
			
			EdgeTracker edgeTracker = new EdgeTracker();
			
			Socket s = new Socket(MMSURL, PORTNUMBER);

			OutputStream os= s.getOutputStream();
			ObjectOutputStream serverWriter = new ObjectOutputStream(os);
			
			// isrServer and serverReader to be used during later stage of implementation  
			InputStreamReader isrServer = new InputStreamReader(s.getInputStream());
			BufferedReader serverReader = new BufferedReader(isrServer);

	        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
	        String sentence;
	        sentence = inFromUser.readLine();
	       
	        // keep repeating until an empty line is read.
			while (sentence.compareTo("") != EMPTYLINE) {
	        
			   System.out.println(sentence);
			   if (sentence.equals(EXTRACT)) {
				   edgeTransferObject = edgeTracker.extractData();
				   serverWriter.writeObject(edgeTransferObject);
				   
			   }
	           sentence = inFromUser.readLine();
	           
	       }
			serverWriter.writeBytes("\n");

			s.close();
			fh.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			LOGGER.log(Level.SEVERE, e.toString());
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.log(Level.SEVERE, e.toString());
		}	
	}
	
}

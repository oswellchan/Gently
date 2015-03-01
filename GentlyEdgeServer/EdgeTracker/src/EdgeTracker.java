import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;
import java.net.*;

public class EdgeTracker {
	public static final String STATS_FILE_LOCATION = "http://localhost/stat";	
	public static final String BRACKETSLASH = "</";
	public static final String EXTRACT = "EXTRACT";
	public static final String CLIENT = "<client>";
	public static final String PUBLISHING = "publishing";
	public static final String ACTIVE = "<active>";
	public static final String ENDSTREAM = "</stream>";
	public static final String STREAM = "<stream>";
	public static final String NAME = "<name>";
	public static final String PAGEURL = "<pageurl>";
	public static final String EDGESERVERNAME = "PLACEHOLDER NAME";
	public static final String MMSURL = "mediatech-i.comp.nus.edu.sg";
	public static final int PORTNUMBER = 9000;
	public static final int EMPTYLINE = 0;
	
	//for logging
	private final static Logger LOGGER = Logger.getLogger("EdgeServer");
	private static Handler fh = null;
	
    //Object to be sent to MMS
    private static EdgeServerTransferObject edgeTransferObject = new EdgeServerTransferObject();
    
    private static int nviewers = 0; 
	private static Streamer tempStream = new Streamer();
	private static ArrayList<Streamer> tempArList = new ArrayList<Streamer>();
	
	// At current stage of implementation, user has to manually call the extract() 
    // method by entering "EXTRACT". This will be automated later.
	// At current stage of implementation, user also has enter an newLine 
	// character("\n") to terminate connection.
	
	//main function to be further refactored during implementation of other classes
	public static void main (String args[]) {
		Socket s;
		try {
			s = new Socket(MMSURL, PORTNUMBER);

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
				   edgeTransferObject = extractData();
			   		serverWriter.writeObject(edgeTransferObject);
			   }
	           sentence = inFromUser.readLine();
	       }
			serverWriter.writeBytes("\n");

			s.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			LOGGER.log(Level.SEVERE, e.toString(), e);
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.log(Level.SEVERE, e.toString(), e);
		}	
	}
	
	private static EdgeServerTransferObject extractData(){
		BufferedReader br = null;
		InputStream is = null;
	    String line = null;
	    String temp = null;
	    
	    br = statsDownloader(is, br);
		try {
			fh = new FileHandler("edgeserver.log"); 
			LOGGER.addHandler(fh);
			LOGGER.setLevel(Level.SEVERE);
			
			while ((line = br.readLine()) != null) {
				nviewers = isViewerIncrement(line);
				if (line.contains(STREAM)){
					line = br.readLine();
					if (line.contains(NAME)){
						temp = getString(line, NAME);							
						tempStream.setStreamkey(Long.parseLong(temp));
					}
				}
				else if (line.contains(PAGEURL)){
					temp = getString(line, PAGEURL);							
					tempStream.setPageurl(temp);
				}	
				else if(line.contains(ENDSTREAM)){
					setNumberOfViewers();
					addStreamerToArrayList();
				}
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
			LOGGER.log(Level.SEVERE, ioe.toString(), ioe);
		} finally {
			try {
				if (is != null) is.close();
	            if (br != null) br.close();
				if (fh != null) fh.close();
	    		edgeTransferObject.setServerName(EDGESERVERNAME);
	    		edgeTransferObject.setStreamer(tempArList);
	        } catch (IOException ioe) {
	        	ioe.printStackTrace();
	        	LOGGER.log(Level.SEVERE, ioe.toString(), ioe);
	        }
		}
		return edgeTransferObject;		
	}

	private static void addStreamerToArrayList() {
		tempArList.add(tempStream);
		tempStream = new Streamer();
	}

	private static void setNumberOfViewers() {
		tempStream.setNviewers(nviewers);
		nviewers = 0;
	}
    
	private static int isViewerIncrement(String line) {
		if( (line.contains(CLIENT)) && !(line.contains(PUBLISHING)) ){
			nviewers++;
			if(line.contains(PAGEURL)){
				//System.out.println("This viewer is pushing the stream elsewhere. Get the destination from log");
				//remember to implement said log later
			}
		}
		return nviewers;
	}	
	private static BufferedReader statsDownloader(InputStream is, BufferedReader br) {
	    try {
	        URL url = new URL(STATS_FILE_LOCATION);
	        is = url.openStream();  
	        br = new BufferedReader(new InputStreamReader(is));
	        
	    } catch (MalformedURLException mue) {
	         mue.printStackTrace();
	         LOGGER.log(Level.SEVERE, mue.toString(), mue);
	    } catch (IOException ioe) {
	 		ioe.printStackTrace();
	 		LOGGER.log(Level.SEVERE, ioe.toString(), ioe);
	    }
        return br;
	}
	
	private static String getString(String line, String input) {
		int startingIndex = line.indexOf(input);
		return line.substring(startingIndex+input.length(), line.indexOf(BRACKETSLASH, startingIndex));
	}
}
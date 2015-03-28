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
	public static final String EXTRACT = "e";
	public static final String CLIENT = "<client>";
	public static final String PUBLISHING = "publishing";
	public static final String ACTIVE = "<active>";
	public static final String ENDSTREAM = "</stream>";
	public static final String STREAM = "<stream>";
	public static final String NAME = "<name>";
	public static final String PAGEURL = "<pageurl>";
	public static final String EDGESERVERNAME = "PLACEHOLDER NAME";
	public static final int EMPTYLINE = 0;
	
	//for logging
	private final static Logger LOGGER = Logger.getLogger(EdgeHandler.class.getName());
	private static Handler fh = null;	

	public EdgeServerTransferObject extractData(){
		
		BufferedReader br = null;
		InputStream is = null;
	    String line = null;
	    String temp = null;
	    
	    //Object to be sent to MMS
	    EdgeServerTransferObject edgeTransferObject = new EdgeServerTransferObject();
	    
	    int nviewers = 0; 
		Streamer tempStream = new Streamer();
		ArrayList<Streamer> tempArList = new ArrayList<Streamer>();
	    
	    br = statsDownloader(is, br);
		try {
			fh = new FileHandler("edgetracker.log");
			LOGGER.addHandler(fh);
			LOGGER.setLevel(Level.SEVERE);
			
			while ((line = br.readLine()) != null) {
				nviewers = isViewerIncrement(line, nviewers);
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
					tempStream.setNviewers(nviewers);
					nviewers = 0;
					
					tempArList.add(tempStream);
					tempStream = new Streamer();
				}
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
			LOGGER.log(Level.SEVERE, ioe.toString());
		} finally {
			try {
				if (is != null) is.close();
	            if (br != null) br.close();
				if (fh != null) fh.close();
	    		edgeTransferObject.setServerName(EDGESERVERNAME);
	    		edgeTransferObject.setStreamer(tempArList);
	        } catch (IOException ioe) {
	        	ioe.printStackTrace();
	        	LOGGER.log(Level.SEVERE, ioe.toString());
	        }
		}
		return edgeTransferObject;		
	}
    
	private static int isViewerIncrement(String line, int nviewers) {
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
	         LOGGER.log(Level.SEVERE, mue.toString());
	    } catch (IOException ioe) {
	 		ioe.printStackTrace();
	 		LOGGER.log(Level.SEVERE, ioe.toString());
	    }
        return br;
	}
	
	private static String getString(String line, String input) {
		int startingIndex = line.indexOf(input);
		return line.substring(startingIndex+input.length(), line.indexOf(BRACKETSLASH, startingIndex));
	}
}
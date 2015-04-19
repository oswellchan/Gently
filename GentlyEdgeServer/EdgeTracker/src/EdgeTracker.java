import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;
import java.net.*;

public class EdgeTracker {
	// EDIT STAT FILE LOCATION
	public static final String STATS_FILE_LOCATION = "http://localhost:455/stat";	
	//EDIT LINK
	public static final String LINK = "rtmp://kiangkuang-i.comp.nus.edu.sg:1935/live1";
	
	public static final String BRACKETSLASH = "</";
	public static final String CLIENT = "<client>";
	public static final String PUBLISHING = "publishing";
	public static final String DROPPED = "<dropped>";
	public static final String ACTIVE = "<active>";
	public static final String ENDSTREAM = "</stream>";
	public static final String STREAM = "<stream>";
	public static final String NAME = "<name>";
	//public static final String SWFURL = "<swfurl>";
	//public static final String PAGEURL = "<pageurl>";
	public static final int EMPTYLINE = 0; 
	
	//for logging
	private final static Logger LOGGER = Logger.getLogger(EdgeHandler.class.getName());

	public EdgeServerTransferObject trackStatus(){
		
		BufferedReader br = null;
		InputStream is = null;
	    //Object to be sent to MMS
	    //EdgeServerTransferObject edgeTransferObject = new EdgeServerTransferObject();
	    
	    br = statsDownloader(is, br);
	    //Object to be sent to MMS
	    EdgeServerTransferObject edgeTransferObject = extractData(br);
			
		try {
			if (br != null) br.close();
    		//edgeTransferObject.setStreamer(tempArList);
        } catch (IOException ioe) {
        	ioe.printStackTrace();
        	LOGGER.log(Level.SEVERE, ioe.toString());
        }
		return edgeTransferObject;		
	}
    
	private EdgeServerTransferObject extractData(BufferedReader br){
		EdgeServerTransferObject tempEdgeTransferObject = new EdgeServerTransferObject();
		ArrayList<Streamer> tempArList = new ArrayList<Streamer>();

		String line = null;
		String temp = null;
		int nviewers = 0; 
		int ndroppedframes = 0;
		Streamer tempStream = new Streamer();
		
		try {
			while ((line = br.readLine()) != null) {
				nviewers = isViewerIncrement(line, nviewers);
				if (line.contains(DROPPED)){
					temp = getString(line, DROPPED);
					ndroppedframes += Integer.parseInt("11");
				}
				if (line.contains(STREAM)){
					line = br.readLine();
					if (line.contains(NAME)){
						temp = getString(line, NAME);							
						tempStream.setStreamkey(Long.parseLong(temp));
						System.out.println(LINK + "/" + temp);
						tempStream.setPageurl(LINK + "/" + temp);
					}
				}/*
				else if (line.contains(SWFURL)){
					temp = getString(line, SWFURL) + "/" + tempStream.getStreamkey();							
					tempStream.setPageurl(temp);
				}*/	
				else if(line.contains(ENDSTREAM)){
					tempStream.setNviewers(nviewers);
					nviewers = 0;
					
					tempArList.add(tempStream);
					tempStream = new Streamer();
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			LOGGER.log(Level.SEVERE, e.toString());
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.log(Level.SEVERE, e.toString());
		} finally {
			tempEdgeTransferObject.setDroppedFrames(ndroppedframes);
			tempEdgeTransferObject.setStreamer(tempArList);
		}
		return tempEdgeTransferObject;
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
	
	private static int isViewerIncrement(String line, int nviewers) {
		if( (line.contains(CLIENT)) && !(line.contains(PUBLISHING)) ){
			nviewers++;
			//if(line.contains(PAGEURL)){
				//System.out.println("This viewer is pushing the stream elsewhere. Get the destination from log");
				//remember to implement said log later
			//}
		}
		return nviewers;
	}	
	
	private static String getString(String line, String input) {
		String toBeReturned = null;
		if(line.equals("") || input.equals("")){
			toBeReturned = "";
		}
		else{
			int startingIndex = line.indexOf(input);
			if(startingIndex == -1){
				toBeReturned = "";
			}
			else{
				toBeReturned = line.substring(startingIndex+input.length(), line.indexOf(BRACKETSLASH, startingIndex));
			}
		}
		return toBeReturned;
	}
}
import java.util.*;
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
	
    //Object to be sent to MMS
    private static EdgeServerTransferObject edgeTransferObject = new EdgeServerTransferObject();
    
    private static int nviewers = 0; 
	private static Streamer tempStream = new Streamer();
	private static ArrayList<Streamer> tempArList = new ArrayList<Streamer>();
	
	public static void main (String args[]) {
		// Connect to the server process running at host
		// mediatech-i.comp.nus.edu.sg and port 9000.
		Socket s;
		try {
			s = new Socket("mediatech-i.comp.nus.edu.sg", 9000);

		    // The next 2 lines create a output stream we can 
			// write to.  (To write TO SERVER)
			OutputStream os= s.getOutputStream();
			//DataOutputStream serverWriter = new DataOutputStream(os); //to write string,otherwise, need to write byte array
			ObjectOutputStream serverWriter = new ObjectOutputStream(os);
			
			// The next 2 lines create a buffer reader that
			// reads from the standard input. (to read stream FROM SERVER)
			InputStreamReader isrServer = new InputStreamReader(s.getInputStream());
			BufferedReader serverReader = new BufferedReader(isrServer);

	        //create buffer reader to read input from user. Read the user input to string 'sentence'
	        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
	        String sentence;
	        sentence = inFromUser.readLine();
	        
	        // keep repeating until an empty line is read.
			while (sentence.compareTo("") != 0) {
	           // Send a user input to server
				//serverWriter.writeBytes(sentence +"\n");
				//Scanner sc = new Scanner(System.in);
	           //String response = serverReader.readLine();
				//String
			   System.out.println(sentence);
			   if (sentence.equals(EXTRACT)) {
				   edgeTransferObject = extractData();
			   		serverWriter.writeObject(edgeTransferObject);
			   }
	           //read user input again
	           sentence = inFromUser.readLine();
	       }

			// Send an empty line to server to end communication.
			serverWriter.writeBytes("\n");

			s.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	private static EdgeServerTransferObject extractData(){
		BufferedReader br = null;
		InputStream is = null;
	    String line = null;
	    String temp = null;
	    br = statsDownloader(is, br);
		
		try {
			while ((line = br.readLine()) != null) {
				nviewers = isViewerIncrement(line, nviewers);
				if (line.indexOf(STREAM) > -1){
					line = br.readLine();
					if (line.indexOf(NAME) > -1){
						temp = getString(line, NAME);							
						tempStream.setStreamkey(Long.parseLong(temp));
					}
				}
				else if (line.indexOf(PAGEURL) > -1){
					temp = getString(line, PAGEURL);							
					tempStream.setPageurl(temp);
				}	
				else if(line.indexOf(ENDSTREAM)>-1){
					setNumberOfViewers();
					addStreamerToArrayList();
				}
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (is != null) is.close();
	            if (br != null) br.close();
	        } catch (IOException ioe) {
	            // nothing to see here
	        }
		}
		edgeTransferObject.setServerName(EDGESERVERNAME);
		edgeTransferObject.setStreamer(tempArList);

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
    
	private static int isViewerIncrement(String line, int nviewers) {
		if( (line.indexOf(CLIENT)>-1) && (line.indexOf(PUBLISHING)==-1) ){
			nviewers++;
			if(line.indexOf(PAGEURL)==-1){
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
	    } catch (IOException ioe) {
	 			ioe.printStackTrace();
	    } finally {
	    }
        return br;
	}
	
	private static String getString(String line, String input) {
		int startingIndex = line.indexOf(input);
		return line.substring(startingIndex+input.length(), line.indexOf(BRACKETSLASH, startingIndex));
	}
}
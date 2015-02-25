import java.util.*;
import java.io.*;
import java.net.*;

public class EdgeTracker {
	public static final String STATS_FILE_LOCATION = "http://localhost/stat";	
	public static final String BRACKETSLASH = "</";
	public static final String EXTRACT = "EXTRACT";
	//publishing and active should return boolean
	//private static String[] flagArray = {"name", "bytes_in", "bytes_out", "address", "dropped", "publishing", "active", "width", "height", "frame_rate", "codec", "audio><codec", "nclients"};
	private static String[] flagArray = {"name", "bytes_in", "bytes_out", "address", "dropped", "width", "height", "frame_rate", "codec", "audio><codec", "nclients"};
	private static ArrayList<String> flagArList = new ArrayList<String>();
	
	public static void main (String args[]) {
		// throws Exception here because don't want to deal
				// with errors in the rest of the code for simplicity.
				// (note: NOT a good practice!)

				// Connect to the server process running at host
				// mediatech-i.comp.nus.edu.sg and port 9000.
				Socket s = new Socket("mediatech-i.comp.nus.edu.sg", 9000);

			    // The next 2 lines create a output stream we can
				// write to.  (To write TO SERVER)
				OutputStream os= s.getOutputStream();
				DataOutputStream serverWriter = new DataOutputStream(os); //to write string,otherwise, need to write byte array

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
		           serverWriter.writeBytes(sentence +"\n");

				   // Server should convert to upper case and reply.
				   // Read server's reply below and output to screen.
		           String response = serverReader.readLine();
				   System.out.println(response);
				   if (response.equals(EXTRACT))
					   edgetracker.extractData();
		           //read user input again
		           sentence = inFromUser.readLine();
		           }

				// Send an empty line to server to end communication.
				serverWriter.writeBytes("\n");

				s.close();
	}
	
	private void extractData(){	
		BufferedReader br = null;
		InputStream is = null;
	    String line = null;
	    Iterator<String> flagIter = null;
	    flagArList.addAll(Arrays.asList(flagArray));
	    
	    //check if this is legit
		EdgeTracker edgetracker = new EdgeTracker();
		br = edgetracker.downloader(is, br);
		
		try {
			while ((line = br.readLine()) != null) {
				//cannot like that lah
				if(line.indexOf("client")>-1 ){
					if(line.indexOf("publishing")>-1)
						System.out.println("This is a streamer");
					else if(line.indexOf("active")>-1)
						System.out.println("This is a viewer");
				}
				else{
					//do nothing
				}
				flagIter = flagArList.iterator();
				while(flagIter.hasNext()){
					String flag = flagIter.next();
					//System.out.println(flag);
					if (line.indexOf(flag) > -1){
						String temp = edgetracker.getString(line, flag);
						System.out.println(flag+ " = " + temp);
					}
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
	}	
	private BufferedReader downloader(InputStream is, BufferedReader br) {
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
	
	private String getString(String line, String input) {
		int startingIndex = line.indexOf(input);
		return line.substring(startingIndex+input.length()+1, line.indexOf(BRACKETSLASH, startingIndex));
	}
}
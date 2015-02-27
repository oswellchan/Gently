import java.util.*;
import java.io.*;
import java.net.*;

public class EdgeTracker {
	public static final String STATS_FILE_LOCATION = "http://localhost/stat";	
	public static final String BRACKETSLASH = "</";
	public static final String EXTRACT = "EXTRACT";
	public static final String CLIENT = "client";
	public static final String PUBLISHING = "publishing";
	public static final String ACTIVE = "active";
	public static final String STREAM = "</stream>";
	public static final String APPLICATION = "</application>";
	//private static String[] flagArray = {"name", "bytes_in", "bytes_out", "address", "dropped", "width", "height", "frame_rate", "codec", "audio><codec", "nclients"};
	private static String[] flagArray = {"<application>", "<name>", "<stream>", "<name>", "<pageurl>"};
	private static ArrayList<String> flagArList = new ArrayList<String>();
	
	public static void main (String args[]) {
		// throws Exception here because don't want to deal
				// with errors in the rest of the code for simplicity.
				// (note: NOT a good practice!)

				// Connect to the server process running at host
				// mediatech-i.comp.nus.edu.sg and port 9000.
		Socket s;
		try {
			s = new Socket("mediatech-i.comp.nus.edu.sg", 9000);

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
				   extractData();
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
	
	private static void extractData(){
		BufferedReader br = null;
		InputStream is = null;
	    String line = null;
	    String flag = null;
	    String temp = null;
	    Iterator<String> flagIter = null;
	    flagArList.addAll(Arrays.asList(flagArray));
	    int nstreamers = 0;
	    int nviewers = 0; 
	    
	    //Object to be sent to MMS
	    ArrayList<Application> edgeTransferObject = new ArrayList<Application> ();
		Application tempApp = new Application();
		Streamer tempStream = new Streamer();
		ArrayList<Streamer> tempArList = new ArrayList<Streamer>();
		br = downloader(is, br);
		
		try {
			while ((line = br.readLine()) != null) {
				if(line.indexOf(CLIENT)>-1 ){
					if(line.indexOf(PUBLISHING)>-1){
						//System.out.println("This is a streamer");
						nstreamers++;
					}
					else if(line.indexOf(ACTIVE)>-1){
						//System.out.println("This is a viewer");
						nviewers++;
							if(line.indexOf(flagArList.get(1))==-1){
								//System.out.println("This viewer is pushing the stream elsewhere. Get the destination from log");
								//remember to implement said log later
							}
					}
				}
				else if(line.indexOf(STREAM)>-1){
					//System.out.println("nstreamers = "+nstreamers);
					//System.out.println("nviewers = "+nviewers);
					tempStream.setNviewers(nviewers);
					tempArList.add(tempStream);
					tempStream = new Streamer();
					nstreamers = 0;
					nviewers = 0;
				}
				else if(line.indexOf(APPLICATION)>-1){
					tempApp.setStreamer(tempArList);
					edgeTransferObject.add(tempApp);
					tempArList = new ArrayList<Streamer>();
					tempApp = new Application();
				}
				flagIter = flagArList.iterator();
				while(flagIter.hasNext()){
					flag = flagIter.next();
					if (line.indexOf(flag) > -1){
						//System.out.println(flag+ " = " + temp);						
						if(flag.equals(flagArList.get(0))){		//application
							line = br.readLine();
							flag = flagIter.next();
							if (line.indexOf(flag) > -1){
								temp = getString(line, flag);							
								tempApp.setAppName(temp);
							}
						}
						else if(flag.equals(flagArList.get(2))){		//stream
							line = br.readLine();
							flag = flagIter.next();
							if (line.indexOf(flag) > -1){
								temp = getString(line, flag);							
								tempStream.setStreamkey(Long.parseLong(temp));
							}
						}
						else if(flag.equals(flagArList.get(4))){		//pageurl
							temp = getString(line, flag);							
							tempStream.setPageurl(temp);
						}
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
	private static BufferedReader downloader(InputStream is, BufferedReader br) {
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
import java.util.*;
import java.io.*;
import java.net.*;

public class EdgeTracker {
	public static final String STATS_FILE_LOCATION = "http://localhost/stat";	
	public static final String BRACKETSLASH = "</";
	//publishing and active should return boolean
	//private static String[] flagArray = {"name", "bytes_in", "bytes_out", "address", "dropped", "publishing", "active", "width", "height", "frame_rate", "codec", "audio><codec", "nclients"};
	private static String[] flagArray = {"name", "bytes_in", "bytes_out", "address", "dropped", "width", "height", "frame_rate", "codec", "audio><codec", "nclients"};
	private static ArrayList<String> flagArList = new ArrayList<String>();
	
	public static void main (String args[]) {
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
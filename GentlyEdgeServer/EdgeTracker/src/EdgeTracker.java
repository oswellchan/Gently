import java.util.*;
import java.io.*;
import java.net.*;

public class EdgeTracker {
	public static final String STATS_FILE_LOCATION = "http://localhost/stat";	
	public static final String NAME = "name";
	
	public static void main (String args[]) {
		BufferedReader br = null;
		InputStream is = null;
	    String line = null;
	    
		EdgeTracker edgetracker = new EdgeTracker();
		br = edgetracker.downloader(is, br);
		
		try {
			while ((line = br.readLine()) != null) {
				//find name of application
				if (line.indexOf(NAME) > -1){
					String temp = edgetracker.getString(line, NAME);
					System.out.println(temp);
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
		return line.substring(line.indexOf(input)+input.length()+1, line.indexOf("</"));
	}
}
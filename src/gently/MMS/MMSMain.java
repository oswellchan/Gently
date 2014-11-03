package gently.MMS;

import java.util.*;
import java.io.*;
import java.net.*;

import org.json.JSONException;

class MMSMain {
    
    public static void main (String args[]) {
	try {
	    MMSWeb webComponent = new MMSWeb(9001);
	    
	    Thread webThread = new Thread(webComponent);
	    
	    initializeStorage();
	    
	    webThread.run();
	    
	} catch (Exception e) {
	    System.out.println(e.getMessage());
	}
    }

	private static void initializeStorage() throws JSONException {
		Storage.getInstance();
		
	}
}
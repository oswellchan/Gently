package gently.MMS;

import java.util.*;
import java.io.*;
import java.net.*;

class MMSMain {
    
    public static void main (String args[]) {
	try {
	    MMSWeb webComponent = new MMSWeb(9001);
	    
	    Thread webThread = new Thread(webComponent);
	    
	    webThread.run();
	    
	} catch (Exception e) {
	    System.out.println(e.getMessage());
	}
    }
}
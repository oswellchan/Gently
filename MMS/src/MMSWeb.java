import java.util.logging.Level;
import java.io.*;
import java.net.*;

class MMSWeb extends PortListener {

	private static final String PORTTYPE = "Web port";

	// Constructor
	MMSWeb(int portNo) throws IOException {
		super(portNo);
	}
	
	public void run() {
		while (true) {
			Socket s = null;

			try {
				s = waitForConnection(PORTTYPE);
				
			} catch (Exception e) {
				displayInTextField("Socket failed to accept.");
				_logger.log(Level.SEVERE, e.getMessage(), e);
				return;
			}

			MMSWebRequestProcessor reqProcessor = null;

			try {
				reqProcessor = new MMSWebRequestProcessor(s);
				
			} catch (Exception e) {
				displayInTextField("Request failed to execute.");
				_logger.log(Level.SEVERE, e.getMessage(), e);
				return;
			}

			Thread thread = new Thread(reqProcessor);

			thread.start();

		}
		
	}
}
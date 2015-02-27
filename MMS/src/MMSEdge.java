import java.util.logging.Level;
import java.io.*;
import java.net.*;

class MMSEdge extends PortListener {

	private static final String _portType = "Edge port";
	GUIController controller;

	// Constructor
	MMSEdge(int portNo) throws IOException {
		super(portNo);
	}
	
	public void run() {
		while (true) {
			Socket s = null;

			try {
				s = waitForConnection(_portType);
				
			} catch (Exception e) {
				displayInTextField("Socket failed to accept.");
				_logger.log(Level.SEVERE, e.getMessage(), e);
				return;
			}

			MMSEdgeRequestProcessor reqProcessor = null;

			try {
				reqProcessor = new MMSEdgeRequestProcessor(s);
				
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
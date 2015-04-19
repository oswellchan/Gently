import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;
import java.net.*;

class MMSEdge extends PortListener {
	
	private static final String PORTTYPE = "Edge port";
	private static final String MSG_FAILEDTOACCEPT = "Socket failed to accept.";
	private static final String MSG_FAILEDTOEXECUTEREQUEST = "Failed to execute request.";
	private static final String INFO_MMSEDGECREATED = "MMSEdge created";
	private static final String INFO_MMSEDGELISTENING = "MMSEdge listening";
	private static final String INFO_CONNECTIONACCEPTED = "MMSEdge accepted connection";
	
	GUIController controller;
	private final static Logger _logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	MMSEdge(int portNo) throws IOException {
		super(portNo);
		_logger.log(Level.INFO, INFO_MMSEDGECREATED);
	}
	
	public void run() {
		MMSEdgeQueueProcessor queueProcessor = new MMSEdgeQueueProcessor();
		Thread queueThread = new Thread(queueProcessor);
		queueThread.start();
		
		MMSEdgeActivityChecker activityChecker = new MMSEdgeActivityChecker();
		Thread acThread = new Thread(activityChecker);
		acThread.start();
		
		while (true) {
			Socket s = null;

			try {
				_logger.log(Level.INFO, INFO_MMSEDGELISTENING);
				s = waitForConnection(PORTTYPE);
				
			} catch (Exception e) {
				displayInTextField(MSG_FAILEDTOACCEPT);
				_logger.log(Level.SEVERE, e.getMessage(), e);
				return;
			}
			
			_logger.log(Level.INFO, INFO_CONNECTIONACCEPTED);

			MMSEdgeRequestProcessor reqProcessor = null;

			try {
				reqProcessor = new MMSEdgeRequestProcessor(s);
				
			} catch (Exception e) {
				displayInTextField(MSG_FAILEDTOEXECUTEREQUEST);
				_logger.log(Level.SEVERE, e.getMessage(), e);
				return;
			}
			
			Thread thread = new Thread(reqProcessor);

			thread.start();
		}
		
	}
}
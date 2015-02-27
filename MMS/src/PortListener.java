import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;
import java.net.*;

import javafx.application.Platform;

class PortListener implements Runnable {

	private ServerSocket _serverSocket;
	protected static final String _portType = "";
	protected static final String MSG_LISTENING = "%1$s waiting for connection at port %2$s. \r";
	protected static final String MSG_ESTABLISHED = "Received connection from %1$s. \r";
	protected final static Logger _logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	protected GUIController controller;

	// Constructor
	PortListener(int portNo) throws IOException {
		_serverSocket = new ServerSocket(portNo);
		controller = GUIController.getInstance();
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

			RequestProcessor reqProcessor = null;

			try {
				reqProcessor = new RequestProcessor(s);
				
			} catch (Exception e) {
				displayInTextField("Request failed to execute.");
				_logger.log(Level.SEVERE, e.getMessage(), e);
				return;
			}

			Thread thread = new Thread(reqProcessor);

			thread.start();

		}
		
	}

	protected Socket waitForConnection(String portType) throws IOException {
		String response = String.format(MSG_LISTENING, portType, _serverSocket.getLocalPort());
		displayInTextField(response);

		Socket s = _serverSocket.accept();

		String res2 = String.format(MSG_ESTABLISHED, _serverSocket.getInetAddress());
		displayInTextField(res2);
		
		return s;
	}

	protected void displayInTextField(final String res) {
		
		final String response = res;
		
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				controller.outputText(response);
			}
		});
	}
}
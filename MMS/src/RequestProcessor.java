import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.application.Platform;

class RequestProcessor implements Runnable {

	Socket s;
	GUIController controller;

	// Constructor
	public RequestProcessor(Socket socket) throws Exception {
		this.s = socket;
		controller = GUIController.getInstance();
	}

	public void run() {

	}

	private void serviceRequest(BufferedReader br, DataOutputStream outputStream) throws IOException {

	}

	private void displayInTextField(final String res) {

		final String response = res;

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				controller.outputText(response);
			}
		});
	}
}
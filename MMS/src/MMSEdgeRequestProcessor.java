import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.ObservableList;

public class MMSEdgeRequestProcessor extends RequestProcessor{

	InternalMemory IM = InternalMemory.getInstance();
	private final static Logger _logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	public MMSEdgeRequestProcessor(Socket socket) throws Exception {
		super(socket);
	}

	public void run() {
		try {
			ObjectInputStream ois = new ObjectInputStream(s.getInputStream());

			collectRequest(ois);

			s.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void collectRequest(ObjectInputStream ois) throws IOException {
		
		EdgeServerTransferObject request = null;
		
		try {
			InetAddress ip = s.getInetAddress();
			_logger.log(Level.INFO, "Receiving EdgeData from " + ip);
			request = (EdgeServerTransferObject) ois.readObject();
		} catch (ClassNotFoundException e) {
			_logger.log(Level.SEVERE, e.getMessage(), e);
			return;
		}
		
		IM.getRequestQueue().add(request);
		
	}
}
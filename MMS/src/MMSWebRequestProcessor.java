import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;


public class MMSWebRequestProcessor extends RequestProcessor{

	private static final String MSG_STREAMISNULL = "Stream is null";
	private static final String MSG_SOCKETISNULL = "Socket is null";
	private static final String MSG_INVALIDINPUT = "Input is invalid.";
	private static final int EDGERELAYPORT = 9001;
	private final static Logger _logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final static int MAXDROPPEDFRAMES = 10;

	public MMSWebRequestProcessor(Socket socket) throws Exception {
		super(socket);
	}

	public void run() {
		try {
			if (s == null) {
				throw new Exception(MSG_STREAMISNULL);
			}

			// Create a reader to read from webComponent
			BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));

			// Create outputstream to return to reader
			DataOutputStream outputStream = new DataOutputStream(s.getOutputStream());

			serviceRequest(br, outputStream);

			s.close();

		} catch (Exception e) {
			_logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	private void serviceRequest(BufferedReader br, DataOutputStream outputStream) throws Exception {

		if (br == null || outputStream == null) {
			_logger.log(Level.SEVERE, MSG_STREAMISNULL);
			throw new Exception(MSG_STREAMISNULL);
		}

		String input = br.readLine();

		String[] splitInput = input.split(" ");

		String clientIP = "";
		String streamerID = "";
		String sources = MSG_INVALIDINPUT;

		if (splitInput.length == 2) {
			clientIP = splitInput[1];
			streamerID = splitInput[0];

			InternalMemory IM = InternalMemory.getInstance();
			checkStatusOfEdgeServers(streamerID);
			sources = IM.getStreamSourcesByID(streamerID);
			sources = sources.trim();
			System.out.println(sources);
		}

		outputStream.writeBytes(sources + "\n");
	}

	// Given a IP, return the geo-location of that IP address (currently unused)
	private static String getLocationOfIPaddress(String clientIP) {
		Connection conn;
		try {
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/gently",
					"root", "");

			double ipNum = convertToIPNumber(clientIP);

			String query = "SELECT `country_name` FROM `ip2location_db1` WHERE "
					+ "(`ip_from` <="
					+ ipNum
					+ ") AND (`ip_to` >="
					+ ipNum
					+ "16785407)";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				return rs.getString("country_name");
			}

		} catch (Exception e) {
			System.err.println(e);
		}

		return null;
	}

	// for IPV4 (currently unused)
	public static double convertToIPNumber(String IPAddress) {

		String[] arrDec;
		double IPNum = 0;

		if (IPAddress == "") {
			return 0;
		} else {
			arrDec = IPAddress.split("\\.");
			for (int i = 0; i < arrDec.length; i++) {
				IPNum += ((Integer.parseInt(arrDec[i]) % 256) * Math
						.pow(256, (3 - i)));
			}

			return IPNum;
		}

	}

	public void checkStatusOfEdgeServers(String identity) {
		InternalMemory IM = InternalMemory.getInstance();
		ConcurrentHashMap<String, List<String>> map = IM.getStreamerToStreamMap();
		HashMap<String, Integer> serverToIndexMap = IM.getServerToIndexMap();
		List<String> list = map.get(identity);
		ConcurrentObservableList serverList = IM.getServerList();
		int leastDroppedFrames = Integer.MAX_VALUE;
		String leastBurdenedServer = null;
		int size = serverList.getSize();
		
		if (list != null) {
			for (String s : list) {
				System.out.println(s);
				String domain = extractDomain(s);
				Integer index = serverToIndexMap.get(domain);
				
				if (index != null) {
					Server server = serverList.getItem(index);
					if (server.getServerName().equals(domain)) {
						int droppedFrames = server.getOb().getDroppedFrames();
						if (droppedFrames < leastDroppedFrames) {
							leastBurdenedServer = server.getServerName();
							leastDroppedFrames = droppedFrames;
						}	
					}
				}
			}
			
			String bestServer = null;

			if (leastDroppedFrames > MAXDROPPEDFRAMES) {
				leastDroppedFrames = MAXDROPPEDFRAMES;
				int leastConnections = Integer.MAX_VALUE;
				
				for (int j = 0; j < size; j++) {
					Server server = serverList.getItem(j);

					if (server.getOb().getDroppedFrames() < leastDroppedFrames) {
						bestServer = server.getServerName();
						leastDroppedFrames = server.getOb().getDroppedFrames();
						leastConnections = server.getStreamerCount() + server.getViewerCount();
					} else if (server.getOb().getDroppedFrames() == leastDroppedFrames) {
						if (server.getStreamerCount() + server.getViewerCount() < leastConnections) {
							bestServer = server.getServerName();
							leastConnections = server.getStreamerCount() + server.getViewerCount();
						}
					}
				}
			}
			
			if (bestServer != null) {
				try {
					sendRelayRequest(leastBurdenedServer, EDGERELAYPORT, bestServer, identity);
				} catch (Exception e) {
					_logger.log(Level.SEVERE, e.getMessage(), e);
				}
			}
		}
	}
	
	public void sendRelayRequest(String url, int port, String relay, String identity) throws Exception {

		Socket s = new Socket(url, port);
		
		String command = "STARTRELAY$" + identity + "$" + relay;
		
		OutputStream os= s.getOutputStream();
		DataOutputStream serverWriter = new DataOutputStream(os); //to write string,otherwise, need towrite byte array
		
		serverWriter.writeBytes(command +"\n");

		s.close();
	}

	public String extractDomain(String url) {
		String domain = "";
		// find & remove protocol (http, ftp, etc.) and get domain
		if (url.indexOf("://") > -1) {
			domain = url.split("/")[2];
		} else {
			domain = url.split("/")[0];
		}

		// find & remove port number
		domain = domain.split(":")[0];

		return domain;
	}
}

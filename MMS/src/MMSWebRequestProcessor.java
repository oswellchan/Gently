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


public class MMSWebRequestProcessor extends RequestProcessor{

	public MMSWebRequestProcessor(Socket socket) throws Exception {
		super(socket);
	}
	
	public void run() {
		try {
			// Create a reader to read from webComponent
			BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));

			// Create outputstream to return to reader
			DataOutputStream outputStream = new DataOutputStream(s.getOutputStream());
			
			serviceRequest(br, outputStream);

			s.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void serviceRequest(BufferedReader br, DataOutputStream outputStream) throws IOException {
		String input = br.readLine();
		// controller.outputText(input);

		String[] splitInput = input.split(" ");
		String clientIP = splitInput[0];
		String streamerID = splitInput[1];
		// String clientLocation =
		// getLocationOfIPaddress(clientIP);

		InternalMemory mem = InternalMemory.getInstance();

		String sources = mem.getStreamSourcesByID(streamerID);

		outputStream.writeBytes(sources + "\n");

		// controller.outputText(sources);
	}

	// Given a IP, return the geo-location of that IP address
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

	// for IPV4
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


}

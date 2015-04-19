import java.util.*;
import java.io.*;
import java.net.*;

public class WebStub {

	WebStub() {

	}

	public static String sendRequest(String sentence, String url, int port) throws Exception {

		Socket s = new Socket(url, port);

		OutputStream os= s.getOutputStream();
		DataOutputStream serverWriter = new DataOutputStream(os); //to write string,otherwise, need towrite byte array

		InputStreamReader isrServer = new InputStreamReader(s.getInputStream());
		BufferedReader serverReader = new BufferedReader(isrServer);

		serverWriter.writeBytes(sentence +"\n");

		String response = serverReader.readLine();

		s.close();

		return response;
	}
	
	public static void main(String[] args) {
		try {
			String r = sendRequest("Ip hello", "localhost", 9001);
			System.out.println(r);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

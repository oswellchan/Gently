import java.util.*;
import java.io.*;
import java.net.*;

public class WebStub {

	WebStub() {

	}

	public String sendRequest(String sentence, String url, int port) throws Exception {

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
}

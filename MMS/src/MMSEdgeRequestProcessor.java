import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MMSEdgeRequestProcessor extends RequestProcessor{
	
	InternalMemory IM = InternalMemory.getInstance();

	public MMSEdgeRequestProcessor(Socket socket) throws Exception {
		super(socket);
	}
	
	public void run() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));

			DataOutputStream outputStream = new DataOutputStream(s.getOutputStream());
			
			serviceRequest(br, outputStream);

			s.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void serviceRequest(BufferedReader br, DataOutputStream outputStream) throws IOException {
		
	}
}

import java.util.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONML;
import org.json.JSONObject;

public class Storage {
	
	public static Storage _storage;
	public JSONArray IPtoLocationFile;
	public JSONArray ServerFile;
	
	public ArrayList<Server> serverList;
    private ArrayList<Server> preferenceList;
	
	
	private Storage() throws JSONException {
		IPtoLocationFile = new JSONArray();
		ServerFile = new JSONArray();
		
		initializeServerFile();
		
		initializeInternalMemory();
		
	}
	
	private void initializeInternalMemory() throws JSONException {
		InternalMemory IM = InternalMemory.getInstance();	
		
		fillIPtoLocationMap(IM);
		
		createServerList();

		fillServerPreferenceList(IM);
	}

	private void fillIPtoLocationMap(InternalMemory IM) throws JSONException {
		
		
	}
	
	private void fillServerPreferenceList(InternalMemory IM) {
		
	}

	//create a list of available servers from JSON file
	private void createServerList() throws JSONException {
		
		for(int i=0; i<ServerFile.length(); i++) {
			JSONObject obj = ServerFile.getJSONObject(i);
			Server serverObj = createServerObject(100, 10, obj.getString("ServerIP"), obj.getString("ServerName"));
			
			serverList.add(serverObj);
		}
		
	}
	
	public Server createServerObject(float bandwidth, float usage, String serverIP, String serverName) {
    	return new Server(bandwidth, usage, serverIP, serverName);
    }



	public static Storage getInstance() throws JSONException {
		if (_storage == null) {
		    _storage = new Storage();
		}
		
		return _storage;
	}
	
	
	private void initializeServerFile() throws JSONException {
		JSONObject obj = new JSONObject();
		
		obj.put("ServerName", "Cananda");
		obj.put("ServerIP", "202.222.1.2");
		
		ServerFile.put(obj);
		
	}


	

	
}
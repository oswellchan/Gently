import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONML;
import org.json.JSONObject;

public class Storage {
	
	public static Storage _storage;
	public JSONArray IPtoLocationFile;
	InternalMemory IM = InternalMemory.getInstance();
	
	private Storage() throws JSONException {
//		IPtoLocationFile = new JSONArray();
//		
//		initializeServerFile();
//		
		initializeInternalMemory();
	    
	    
		
	}
	
	private void initializeInternalMemory() throws JSONException {	
	    
	    BufferedReader reader = null;
	    
	    try {
		reader = new BufferedReader(new FileReader("save.txt"));
		
	    } catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    
	    String JSONsource = null;
	    
	    try {
		JSONsource = reader.readLine();
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    
	    JSONObject savedState = new JSONObject(JSONsource);
	    JSONObject portNumbers = savedState.getJSONObject("portNumbers");
	    int webPort = portNumbers.getInt("webPort");
	    int serverPort = portNumbers.getInt("serverPort");
	    IM.setPortNumbers(webPort, serverPort);
	    
	    JSONObject streamerMap = savedState.getJSONObject("streamerMap");
	    String[] arrayOfStreamers = JSONObject.getNames(streamerMap);
	    
	    if (arrayOfStreamers != null) {
	    
	    ConcurrentHashMap<String, ArrayList<String>> streamerToStreamSourcesMap = IM.getStreamerToStreamMap();
	    
	    for (String streamer : arrayOfStreamers) {
		JSONArray JSONArrayOfSources = streamerMap.getJSONArray(streamer);
		ArrayList<String> arrayOfSources = new ArrayList<String>();
		
		for (int i = 0; i < JSONArrayOfSources.length(); i++) {
		    String source = JSONArrayOfSources.getString(i);
		    arrayOfSources.add(source);
		}
		
		streamerToStreamSourcesMap.put(streamer, arrayOfSources);
	    }
	    }
	}
	
	private void fillServerPreferenceList(InternalMemory IM) {
		
	}

	public static Storage getInstance() throws JSONException {
		if (_storage == null) {
		    _storage = new Storage();
		}
		
		return _storage;
	}
	
	
	public void saveStateToFile() throws JSONException {
	    
	    JSONObject file = new JSONObject();
	    
	    JSONObject portNumbers = portNumbersToJSONObj();
	    JSONObject streamerMap = streamerMapToJSONObj();
	    
	    file.put("portNumbers", portNumbers);
	    file.put("streamerMap", streamerMap);
	    
	    writeToFile(file);
	}

	private void writeToFile(JSONObject file) {
	    try {
		PrintWriter writer = new PrintWriter("save.txt");
		writer.print("");
		writer.write(file.toString());
		
		writer.flush();
		writer.close();
	    } catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	    }
	}

	private JSONObject portNumbersToJSONObj() throws JSONException {
	    int webPort = IM.getWebPort();
	    int serverPort = IM.getServerPort();
	    
	    JSONObject entry = new JSONObject();
	    entry.put("webPort", webPort);
	    entry.put("serverPort", serverPort);
	    
	    return entry;
	}

	private JSONObject streamerMapToJSONObj() throws JSONException {
	    JSONObject entry = new JSONObject();
	    
	    ConcurrentHashMap<String, ArrayList<String>> streamerToStreamSourcesMap = IM.getStreamerToStreamMap();
	    Set<Map.Entry<String, ArrayList<String>>> setOfEntries = streamerToStreamSourcesMap.entrySet();
	    
	    for (Map.Entry<String, ArrayList<String>> e : setOfEntries) {
		JSONArray streamSources = new JSONArray();
		
		for (String source : e.getValue()) {
		    streamSources.put(source);
		}
		
		entry.put(e.getKey(), streamSources);
	    }
	    return entry;
	}
}
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Storage {

	public static Storage _storage;
	private final static Logger _logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	InternalMemory IM = InternalMemory.getInstance();

	private Storage() throws JSONException {
		initializeInternalMemory();
	}

	public static Storage getInstance() throws JSONException {
		if (_storage == null) {
			_storage = new Storage();
		}
		return _storage;
	}

	private void initializeInternalMemory() throws JSONException {

		String JSONsource = getJSONSourceFromSaveFile();
		
		if (JSONsourceIsNotEmpty(JSONsource)) {
			JSONObject savedState = new JSONObject(JSONsource);
		
			setupPortNumbers(savedState);
			setupStreamerToStreamSourcesMap(savedState);
		}
	}

	private boolean JSONsourceIsNotEmpty(String JSONsource) {
		return JSONsource != null;
	}

	private void setupStreamerToStreamSourcesMap(JSONObject savedState) throws JSONException {
		
		JSONObject streamerMapInJSON = savedState.getJSONObject("streamerMap");
		String[] arrayOfStreamers = JSONObject.getNames(streamerMapInJSON);

		if (streamersArrayNotEmpty(arrayOfStreamers)) {
			convertJSONtoHashMap(streamerMapInJSON, arrayOfStreamers);
		}
	}

	private void convertJSONtoHashMap(JSONObject streamerMap,
			String[] arrayOfStreamers) throws JSONException {
		
		ConcurrentHashMap<String, ArrayList<String>> streamerToStreamSourcesMap = IM
				.getStreamerToStreamMap();

		for (String streamer : arrayOfStreamers) {
			ArrayList<String> arrayOfSources = getArrayOfSources(streamerMap, streamer);
			streamerToStreamSourcesMap.put(streamer, arrayOfSources);
		}
	}

	private boolean streamersArrayNotEmpty(String[] arrayOfStreamers) {
		return arrayOfStreamers != null;
	}

	private ArrayList<String> getArrayOfSources(JSONObject streamerMap,
			String streamer) throws JSONException {
		
		JSONArray JSONArrayOfSources = streamerMap.getJSONArray(streamer);
		ArrayList<String> arrayOfSources = new ArrayList<String>();

		for (int i = 0; i < JSONArrayOfSources.length(); i++) {
			String source = JSONArrayOfSources.getString(i);
			arrayOfSources.add(source);
		}
		
		return arrayOfSources;
	}

	private void setupPortNumbers(JSONObject savedState) throws JSONException {
		
		JSONObject portNumbers = savedState.getJSONObject("portNumbers");
		int webPort = portNumbers.getInt("webPort");
		int serverPort = portNumbers.getInt("serverPort");
		IM.setPortNumbers(webPort, serverPort);
	}

	private String getJSONSourceFromSaveFile() {
		BufferedReader reader = null;
		String JSONsource = null;
		
		if (fileDoesNotExists()) {
			createNewFile();
		} else {
        		reader = initializeBufferedReader();
        		try {
        			JSONsource = reader.readLine();
        		} catch (IOException e) {
        			_logger.log(Level.SEVERE, "Unable to read from JSONFile");
        			System.exit(0);
        		}
		}
		
		return JSONsource;
	}

	private BufferedReader initializeBufferedReader() {
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new FileReader("save.txt"));
		} catch (Exception e) {
			_logger.log(Level.SEVERE, "Saved file cannot be read");
			System.exit(0);
		}
		
		return reader;
	}

	private boolean fileDoesNotExists() {
		
		boolean fileDoesNotExists = true;
		
		try {
			FileReader reader = new FileReader("save.txt");
			reader.close();
			
			fileDoesNotExists = false;
		} catch (Exception e) {
			_logger.log(Level.INFO, "New save file created");
		}
		
		return fileDoesNotExists;
	}

	private void createNewFile() {
		try {
			PrintWriter writer = new PrintWriter("save.txt");

			writer.flush();
			writer.close();
		} catch (IOException e) {
			_logger.log(Level.SEVERE, "Unable to create new file");
		}
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
			_logger.log(Level.SEVERE, "Unable to save to file");
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
		
		JSONObject JSONstreamerMap = new JSONObject();

		ConcurrentHashMap<String, ArrayList<String>> streamerToStreamSourcesMap = IM
				.getStreamerToStreamMap();
		Set<Map.Entry<String, ArrayList<String>>> setOfEntries = streamerToStreamSourcesMap
				.entrySet();

		for (Map.Entry<String, ArrayList<String>> entry : setOfEntries) {
			JSONArray streamSources = new JSONArray();
			
			ArrayList<String> arrayOfSources = entry.getValue();
			String streamer = entry.getKey();
			
			for (String source : arrayOfSources) {
				streamSources.put(source);
			}

			JSONstreamerMap.put(streamer, streamSources);
		}
		
		return JSONstreamerMap;
	}
}
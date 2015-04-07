import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//Follows Singleton pattern
public class InternalMemory {

	private static final String MSG_NOSOURCEFOUND = "NULL";
	private static final String INFO_INTERNALMEMINITIALISED = "InternalMemory initialised";
	private static final String INFO_RETRIEVINGSOURCES = "Retrieving stream links";
	private static final String INFO_SOURCESFOUND = "%1$s found";
	
	private final static Logger _logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	private static InternalMemory _internalMem = null;
	private int _webPortNo = -1;
	private int _serverPortNo = -1;
	private static ConcurrentHashMap<String, ArrayList<String>> _streamerToStreamSourcesMap;
	private ObservableList<Server> _serverList = FXCollections.observableArrayList();
	

	public InternalMemory() {
		_streamerToStreamSourcesMap = new ConcurrentHashMap<String, ArrayList<String>>();
		
//		//For testing GUI purposes. Delete when done debugging
//		_serverList.add(new Server("Link", "mediatech-i", 1, 2));
//		_serverList.add(new Server("Link", "mediatech-i1", 1, 2));
//		_serverList.add(new Server("Link", "mediatech-i2", 1, 2));
//		_serverList.add(new Server("Link", "mediatech-i3", 1, 2));
//		_serverList.add(new Server("Link", "mediatech-i4", 1, 2));
//		_serverList.add(new Server("Link", "mediatech-i5", 1, 2));
		
		_logger.log(Level.INFO, INFO_INTERNALMEMINITIALISED);
	}

	public static InternalMemory getInstance() {
		if (_internalMem == null) {
			_internalMem = new InternalMemory();
		}

		return _internalMem;
	}

	public String getStreamSourcesByID(String userID) {
		
		_logger.log(Level.INFO, INFO_RETRIEVINGSOURCES);

		String sources = "";
		
		if (userID != null) {

			ArrayList<String> arrayOfStreamSources = _streamerToStreamSourcesMap.get(userID);

			if (arrayOfStreamSources == null) {
				sources = MSG_NOSOURCEFOUND;
			} else {
				for (String source : arrayOfStreamSources) {
					sources += (source + " ");
				}
			}
		
			_logger.log(Level.INFO, String.format(INFO_SOURCESFOUND, sources));
		} else {
			sources = MSG_NOSOURCEFOUND;
		}
		
		return sources;
	}

	public void updatePortNos(int webPort, int serverPort) {
		_webPortNo = webPort;
		_serverPortNo = serverPort;
	}

	public ConcurrentHashMap<String, ArrayList<String>> getStreamerToStreamMap() {
		return _streamerToStreamSourcesMap;
	}

	public int getWebPort() {
		return _webPortNo;
	}

	public int getServerPort() {
		return _serverPortNo;
	}

	public void setPortNumbers(int webPort, int serverPort) {
		_webPortNo = webPort;
		_serverPortNo = serverPort;
	}

	public ObservableList<Server> getServerList() {
		return _serverList;
	}

	public void setServerList(ObservableList<Server> serverList) {
		this._serverList = serverList;
	}
}

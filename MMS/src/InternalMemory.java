import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//Follows Singleton pattern
public class InternalMemory {

	private static InternalMemory _internalMem;
	private static final String MSG_NOSOURCEFOUND = "NULL";
	private int _webPortNo;
	private int _serverPortNo;
	private static ConcurrentHashMap<String, ArrayList<String>> _streamerToStreamSourcesMap;
	private ObservableList<Server> _serverList = FXCollections.observableArrayList();

	private InternalMemory() {
		_streamerToStreamSourcesMap = new ConcurrentHashMap<String, ArrayList<String>>();
		_serverList.add(new Server("Link", "mediatech-i", 1, 2));
		_serverList.add(new Server("Link", "mediatech-i1", 1, 2));
		_serverList.add(new Server("Link", "mediatech-i2", 1, 2));
		_serverList.add(new Server("Link", "mediatech-i3", 1, 2));
		_serverList.add(new Server("Link", "mediatech-i4", 1, 2));
		_serverList.add(new Server("Link", "mediatech-i5", 1, 2));
	}

	public static InternalMemory getInstance() {
		if (_internalMem == null) {
			_internalMem = new InternalMemory();
		}

		return _internalMem;
	}

	public static String getStreamSourcesByID(String userID) {

		String sources = "";

		ArrayList<String> arrayOfStreamSources = _streamerToStreamSourcesMap
				.get(userID);

		if (arrayOfStreamSources == null) {
			sources = MSG_NOSOURCEFOUND;
		} else {
			for (String source : arrayOfStreamSources) {
				sources += (source + " ");
			}
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

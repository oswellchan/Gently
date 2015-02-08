import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

//Follows Singleton pattern
public class InternalMemory {
    
    private static InternalMemory _internalMem;
    private static final String MSG_NOSOURCEFOUND = "NULL";
    private int _webPortNo;
    private int _serverPortNo;
    private static ConcurrentHashMap<String, ArrayList<String>> _streamerToStreamSourcesMap;
	
    private InternalMemory() {
	_streamerToStreamSourcesMap = new ConcurrentHashMap<String, ArrayList<String>>();
	
    }
    
    public static InternalMemory getInstance() {
	if (_internalMem == null) {
	    _internalMem = new InternalMemory();
	}
		
	return _internalMem;
    }
    
    public static String getStreamSourcesByID(String userID) {
	
	String sources = "";
	
	ArrayList<String> arrayOfStreamSources = _streamerToStreamSourcesMap.get(userID);
	
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
}



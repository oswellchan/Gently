import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

//Follows Singleton pattern
public class InternalMemory {
    
    private static InternalMemory _internalMem;
    private static final String MSG_NOSOURCEFOUND = "NULL";
    private static ConcurrentHashMap<String, ArrayList<String>> _streamerToStreamSourcesMap;
    private static ArrayList<Thread> _threadArrayList; 
	
    private InternalMemory() {
	_streamerToStreamSourcesMap = new ConcurrentHashMap<String, ArrayList<String>>();
	_threadArrayList = new ArrayList<Thread>();
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
    
    public static void addToListOfThreads(Thread t) {
	_threadArrayList.add(t);
    }
    
    public static void main(String[] args) {
    	
    }
    
}



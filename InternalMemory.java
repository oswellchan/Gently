package gently;

import java.util.*;

//Follows Singleton pattern
public class InternalMemory {
    
    private static InternalMemory _theOne;
    private HashMap<String, String> _IPtoLocationMap;
    private HashMap<String, ArrayList<Server>> _streamerToServerMap; 
    private HashMap<String, ArrayList<Server>> _serverPreferenceList;
    
    private InternalMemory() {
	_IPtoLocationMap = new HashMap<String, String>();
	_IPtoLocationMap.put("201.222.1.2", "Canada");
	_IPtoLocationMap.put("201.222.1.3", "Chile");
	_IPtoLocationMap.put("201.222.1.4", "Russia");
	_IPtoLocationMap.put("201.222.1.5", "Australia");
	_IPtoLocationMap.put("201.222.1.6", "Germany");
	_IPtoLocationMap.put("201.222.1.7", "Japan");
	_IPtoLocationMap.put("201.222.1.8", "India");
	
    	Server canada = new Server(100, 10, "canada server");
    	Server chile = new Server(100, 10, "chile server");
    	Server russia = new Server(100, 10, "russia server");
    	Server australia = new Server(100, 10, "australia server");
    	Server germany = new Server(100, 10, "germany server");
    	Server japan = new Server(0, 10, "japan server");
    	Server india = new Server(100, 10, "india server");
    	
	_streamerToServerMap = new HashMap<String, ArrayList<Server>>();
	ArrayList<Server> s1 = new ArrayList<Server>();
	s1.add(canada);
	s1.add(india);
	_streamerToServerMap.put("oswell", s1);
	
	_serverPreferenceList = new HashMap<String, ArrayList<Server>>();
	ArrayList<Server> s = new ArrayList<Server>();
	
	s.add(japan);
	s.add(canada);
	s.add(india);
	s.add(germany);
	s.add(chile);
	s.add(russia);
	s.add(australia);
	_serverPreferenceList.put("Japan", s);
	
    }
    
    
    public static InternalMemory getInstance() {
	if (_theOne == null) {
	    _theOne = new InternalMemory();
	}
	
	return _theOne;
    }
    
    
    
    //Given a IP, return the geo-location of that IP
    public String getLocationOfIP(String IP) {
	return _IPtoLocationMap.get(IP);
    }
    
    //Given a StreamerID, get all servers that currently have that stream
    public ArrayList<Server> getExistingServersStreaming(String streamerID) {
	return _streamerToServerMap.get(streamerID);
    }
    
    //Given a location, return the preferenceList of servers of that location
    public ArrayList<Server> getPreferenceList(String location) {
	return _serverPreferenceList.get(location);
    }
    
    
}

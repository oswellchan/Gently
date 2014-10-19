package gently;

import java.util.*;

//Follows Singleton pattern
public class InternalMemory {
    
    private static InternalMemory _theOne;
    private HashMap<String, String> _IPtoLocationMap;
    private HashMap<String, Server> _serverIPToServerMap;
    private HashMap<String, ArrayList<Server>> _streamerToServerMap; 
    private HashMap<String, ArrayList<Server>> _serverPreferenceList;
    
    private InternalMemory() {
	
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
    
    //Given an IP of an edge server, return the Object representation of that server
    public Server getServer(String IP) {
	return _serverIPToServerMap.get(IP);
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

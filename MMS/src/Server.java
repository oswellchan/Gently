import java.util.*;

public class Server {
    String _serverIP;
    String _serverName;
    float _maxLoad;
    float _currentLoad;
    boolean _inUse;
    
    Server(float bandwith, float usage, String serverIP, String serverName) {
	_serverIP = serverIP;
	_serverName = serverName;
	_maxLoad = (float) Math.floor(bandwith/usage);
	_currentLoad = 0;
	_inUse = false;
    }
    
    //Get IP
    public String getServerIP() {
	return _serverIP;
    }
    
    //check if server can still take in any more users
    private boolean canService() {
	if (_currentLoad < _maxLoad) {
	    return true;
	}
	return false;
    }
    
    /* Try to add a user to the server. If successful, return true.
     * Else return false.
     */
    public boolean isSuccessfulInAddingUser() {
	boolean isSuccessful = false;
	
	while (_inUse) {
	    //wait to prevent concurrent modification
	}
	
	//set flag so that other threads cannot modify
	_inUse = true;
	
	if (canService()) {
	    _currentLoad++;
	    isSuccessful = true;
	}
	
	//reset flag so that others can access server
	_inUse = false;
	
	return isSuccessful;
    }
    
    public void removeUser() {
	while (_inUse) {
	    //wait to prevent concurrent modification
	}
	
	_currentLoad--;
	
	_inUse = false;
    }
}

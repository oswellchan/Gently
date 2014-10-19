package gently;

import java.math.BigInteger;
import java.util.*;

public class Server {
    String _serverIP;
    BigInteger _maxLoad;
    BigInteger _currentLoad;
    boolean _inUse;
    
    Server(float bandwith, float usage, String serverIP) {
	_serverIP = serverIP;
	_maxLoad = new BigInteger(Math.floor(bandwith/usage) + "");
	_currentLoad = new BigInteger("0");
	_inUse = false;
    }
    
    //Get IP
    public String getServerIP() {
	return _serverIP;
    }
    
    //check if server can still take in any more users
    private boolean canService() {
	if (_currentLoad.compareTo(_maxLoad) == 1) {
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
	    _currentLoad.add(BigInteger.ONE);
	    isSuccessful = true;
	}
	
	//reset flag so that others can access server
	_inUse = false;
	
	return isSuccessful;
    }
}

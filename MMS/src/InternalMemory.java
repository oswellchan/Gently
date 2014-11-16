import java.util.*;

//Follows Singleton pattern
public class InternalMemory {
    
    private static InternalMemory _theOne;
    private HashMap<String, ArrayList<Server>> _streamerToServerMap; 
    private int counter;
    Server es1 = new Server(3, 1, "rtmp://mediatech-i.comp.nus.edu.sg:1935/live1/flv:123", "es1");
    Server es2 = new Server(3, 1, "rtmp://3283server2-i.comp.nus.edu.sg:1970/live1/flv:123", "es2");
    Server es0 = new Server(3, 1, "rtmp://mediatech-i.comp.nus.edu.sg:1935/live1/flv:456", "es1");
	
    private InternalMemory() {
		_streamerToServerMap = new HashMap<String, ArrayList<Server>>();
		
		
		ArrayList<Server> user1 = new ArrayList<Server>();
		ArrayList<Server> user2 = new ArrayList<Server>();
		user1.add(es1);
		user1.add(es2);
		user2.add(es0);
		
		_streamerToServerMap.put("user1", user1);
		_streamerToServerMap.put("user2", user2);
		
    }
    
    public static InternalMemory getInstance() {
		if (_theOne == null) {
		    _theOne = new InternalMemory();
		}
		
		return _theOne;
    }
    
    public String chooseBest(String streamerID) {
	ArrayList<Server> listOfServer = _streamerToServerMap.get(streamerID);
	
	if (counter >= 5) {
	    es1.removeUser();
	    es1.removeUser();
	    es2.removeUser();
	}
	
	if (listOfServer == null) {
		return null;
	}
	
	for (Server s : listOfServer) {
	    if (s.isSuccessfulInAddingUser()) {
		return s._serverIP;
	    }
	}
	
	return null;
    }
    
    //Given a StreamerID, get all servers that currently have that stream
    public ArrayList<Server> getExistingServersStreaming(String streamerID) {
    	return _streamerToServerMap.get(streamerID);
    }
    
    public void addCounter() {
	counter++;
    }
    
    public static void main(String[] args) {
    	
    }

   
    
    
}



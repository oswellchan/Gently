import java.util.*;

//Follows Singleton pattern
public class InternalMemory {
    
    private static InternalMemory _theOne;
    private HashMap<String, ArrayList<Server>> _streamerToServerMap; 
    
    private InternalMemory() {
	    	
		_streamerToServerMap = new HashMap<String, ArrayList<Server>>();
		
    }
    
    public static InternalMemory getInstance() {
		if (_theOne == null) {
		    _theOne = new InternalMemory();
		}
		
		return _theOne;
    }
    
    
    
    
   	

    

    
    //Given a StreamerID, get all servers that currently have that stream
    public ArrayList<Server> getExistingServersStreaming(String streamerID) {
    	return _streamerToServerMap.get(streamerID);
    }

    
    public static void main(String[] args) {
    	
    }

   
    
    
}



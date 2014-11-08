package gently.MMS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

//Follows Singleton pattern
public class InternalMemory {
    
    private static InternalMemory _theOne;
    private HashMap<String, String> _IPtoLocationMap;
    private HashMap<String, ArrayList<Server>> _streamerToServerMap; 
    
    private InternalMemory() {
		_IPtoLocationMap = new HashMap<String, String>();
	    	
		_streamerToServerMap = new HashMap<String, ArrayList<Server>>();
		ArrayList<Server> s1 = new ArrayList<Server>();
		
	
    }
    
    public static InternalMemory getInstance() {
		if (_theOne == null) {
		    _theOne = new InternalMemory();
		}
		
		return _theOne;
    }
    
    //for IPV4
    public static double convertToIPNumber(String IPAddress) {
    	
    	String[] arrDec;
    	double IPNum = 0;
    	
    	
    	if (IPAddress == "") {
    		return 0;
    	} else {
    		arrDec = IPAddress.split("\\.");
    		for (int i=0; i<arrDec.length; i++) {
    			IPNum += ((Integer.parseInt(arrDec[i])%256) * Math.pow(256 ,(3 - i )));
    		}
 
    		return IPNum;
    	}
    	
    }
    
    
    private ArrayList<Server> findPreferenceList() {
		
		return null;
	}

    //Given a IP, return the geo-location of that IP
    public String getLocationOfIP(String IP) {
    	return _IPtoLocationMap.get(IP);
    }
    
    //Given a StreamerID, get all servers that currently have that stream
    public ArrayList<Server> getExistingServersStreaming(String streamerID) {
    	return _streamerToServerMap.get(streamerID);
    }

    
    public static void main(String[] args) {
    	Connection conn;
    	try {
    		conn = DriverManager.getConnection("jdbc:mysql://localhost/gently", "root", "");
    		
    		String query = "SELECT `country_name` FROM `ip2location_db1` WHERE "
    				+ "(`ip_from` <= 16785407) AND (`ip_to` >= 16785407)";
    		Statement stmt = conn.createStatement();
    		ResultSet rs = stmt.executeQuery(query);
    		
    		while(rs.next()) {
    			System.out.println(rs.getString("country_name"));
    		} 
    		
    		
    		
    		System.out.println(convertToIPNumber("202.186.13.4"));
    		
    	} catch (Exception e) {
    		System.err.println(e);
    	}
    }
    
    
}



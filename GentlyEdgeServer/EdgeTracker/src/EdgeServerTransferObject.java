import java.util.ArrayList;

public class EdgeServerTransferObject implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2793889619822471927L;
	private String serverName = null;
	private ArrayList<Streamer> streamers = new ArrayList<Streamer> ();
	
	public String getServerName(){
		return serverName;
	}
	
	public ArrayList<Streamer> getStreamers(){
		return streamers;
	}
	
	public void setServerName(String input){
		serverName = input;
	}
	
	public void setStreamer(ArrayList<Streamer> input){
		streamers = input;
	}
}

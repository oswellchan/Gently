import java.util.ArrayList;

public class EdgeServerTransferObject implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2793889619822471927L;
	private String serverName = null;
	private ArrayList<Streamer> streamers = new ArrayList<Streamer> ();
	private String command = null;
	private int ndroppedframes = 0;
	
	public String getServerName(){
		return serverName;
	}
	
	public ArrayList<Streamer> getStreamers(){
		return streamers;
	}
	
	public String getCommand(){
		return command;
	}
	
	public int getDroppedFrames(){
		return ndroppedframes;
	}
	
	public void setServerName(String input){
		serverName = input;
	}
	
	public void setStreamer(ArrayList<Streamer> input){
		streamers = input;
	}
	
	public void setCommand(String input){
		command = input;
	}
	
	public void setDroppedFrames(int input){
		ndroppedframes = input;
	}
	
}

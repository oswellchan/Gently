import java.util.ArrayList;

public class Application {
	private String appName = null;
	private ArrayList<Streamer> streamers = new ArrayList<Streamer> ();
	
	public String getAppName(){
		return appName;
	}
	
	public ArrayList<Streamer> getStreamers(){
		return streamers;
	}
	
	public void setAppName(String input){
		appName = input;
	}
	
	public void setStreamer(ArrayList<Streamer> input){
		streamers = input;
	}
}

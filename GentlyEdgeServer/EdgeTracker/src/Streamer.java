
public class Streamer {
	private long streamkey = 0;
	private int nviewers = 0;
	private String pageurl = null;
	
	public long getStreamkey(){
		return streamkey;
	}
	
	public int getNviewers() {
		return nviewers;
	}
	
	public String getPageurl() {
		return pageurl;
	}
	
	public void setStreamkey(long input){
		streamkey = input;
	}
	
	public void setNviewers(int input) {
		nviewers = input;
	}
	
	public void setPageurl(String input) {
		pageurl = input;
	}
	
	
}

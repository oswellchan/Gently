import java.util.*;

public class Server {
	private String _serverIP;
	private String _serverName;
	private int _viewerCount;
	private int _streamerCount;
	
	Server(String serverIP, String serverName, int viewerCount, int streamerCount) {
		this._serverIP = serverIP;
		this._serverName = serverName;
		this._viewerCount = viewerCount;
		this._streamerCount = streamerCount;
	}

	public String getServerIP() {
		return _serverIP;
	}

	public void set_serverIP(String serverIP) {
		this._serverIP = serverIP;
	}

	public String getServerName() {
		return _serverName;
	}

	public void setServerName(String serverName) {
		this._serverName = serverName;
	}

	public int getViewerCount() {
		return _viewerCount;
	}

	public void setViewerCount(int viewerCount) {
		this._viewerCount = viewerCount;
	}

	public int getStreamerCount() {
		return _streamerCount;
	}

	public void setStreamerCount(int streamerCount) {
		this._streamerCount = streamerCount;
	}

}

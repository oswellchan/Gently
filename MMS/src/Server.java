import java.util.*;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;

public class Server implements java.io.Serializable {
	private String _serverName;
	private int _viewerCount;
	private int _streamerCount;
	private EdgeServerTransferObject _ob;
	private Calendar _timeAtLastUpdate;
	private boolean _isActive;

	Server(String serverName, int viewerCount, int streamerCount, EdgeServerTransferObject _ob, Calendar time, boolean isActive) {
		this._serverName = serverName;
		this._viewerCount = viewerCount;
		this._streamerCount = streamerCount;
		this._ob = _ob;
		this._timeAtLastUpdate = time;
		this._isActive = isActive;
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
	
	public EdgeServerTransferObject getOb() {
		return _ob;
	}

	public void setOb(EdgeServerTransferObject _ob) {
		this._ob = _ob;
	}

	public Calendar getTimeAtLastUpdate() {
		return _timeAtLastUpdate;
	}

	public void setTimeAtLastUpdate(Calendar _timeAtLastUpdate) {
		this._timeAtLastUpdate = _timeAtLastUpdate;
	}
	
	public boolean isActive() {
		return _isActive;
	}

	public void setIsActive(boolean _isActive) {
		this._isActive = _isActive;
	}

}

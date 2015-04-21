import java.util.ArrayList;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class ConcurrentObservableList {
	private ObservableList<Server> _serverList = FXCollections.observableArrayList();
	private ArrayList<Server> dataArray = new ArrayList<Server>();
	
	ConcurrentObservableList(ObservableList<Server> list) {
		_serverList = list;
	}
	
	public synchronized Server getItem(int i) {
		return dataArray.get(i);
	}
	
	public synchronized int getSize() {
		return dataArray.size();
	}
	
	public synchronized int addItem(Server s) {
		dataArray.add(s);
		return dataArray.size() - 1;
	}
	
	public ObservableList<Server> getList() {
		return _serverList;
	}
	
	public synchronized void refresh() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				_serverList.clear();
				for(Server s : dataArray) {
					_serverList.add(s);
				}
			}
		});
	}
}

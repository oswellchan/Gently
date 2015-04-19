import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ObservableList;


public class MMSEdgeQueueProcessor implements Runnable {
	private GUIController controller = GUIController.getInstance();
	private InternalMemory IM = InternalMemory.getInstance();
	private final static Logger _logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private List<EdgeServerTransferObject> requestQueue = IM.getRequestQueue();

	@Override
	public void run() {
		while (true) {
			if (!requestQueue.isEmpty()) {
				EdgeServerTransferObject request = requestQueue.remove(0);

				boolean isInit = request.getCommand().equals("INIT");
				boolean isUpdate = request.getCommand().equals("UPDATE");

				if (isInit) {
					processEdgeServerInit(request);
				} else if (isUpdate) {
					String serverName = request.getServerName();
					ConcurrentObservableList serverList = IM.getServerList();
					HashMap<String, Integer> serverToIndexMap = IM.getServerToIndexMap();
					Integer index = serverToIndexMap.get(serverName);

					if (index == null) {

					} else {
						Server server = serverList.getItem(index);
						updateEdgeStateInIM(request, server);
						server.setIsActive(true);
					}

					serverList.refresh();
				}
					//printIMState();

			}
			
		}
	}


	private void processEdgeServerInit(EdgeServerTransferObject edgeTransferObject) {
		String serverName = edgeTransferObject.getServerName();
		ConcurrentObservableList serverList = IM.getServerList();
		HashMap<String, Integer> serverToIndexMap = IM.getServerToIndexMap();
		Integer index = serverToIndexMap.get(serverName);

		if (index == null) {
			addEdgeServerToIM(edgeTransferObject, serverName, serverList, serverToIndexMap);
		} else {
			Server server = serverList.getItem(index);

			if (server.isActive()) {
				_logger.log(Level.INFO, "Server already active. Ignoring update");
			} else {
				updateEdgeStateInIM(edgeTransferObject, server);
				server.setIsActive(true);
			}	
		}

		serverList.refresh();
	}

	private void updateEdgeStateInIM(EdgeServerTransferObject edgeTransferObject, Server server) {
		EdgeServerTransferObject oldState = server.getOb();
		ArrayList<Streamer> newListOfStreamers = edgeTransferObject.getStreamers();
		ArrayList<Streamer> noLongerStreamingList = oldState.getStreamers();

		ArrayList<Streamer> listOfNewStreamers = new ArrayList<Streamer>();

		int totalViewers = separateActiveFromInactiveStreamers(newListOfStreamers, noLongerStreamingList, listOfNewStreamers);
		server.setViewerCount(totalViewers);
		
		removeStreamersNoLongerStreaming(noLongerStreamingList);
		addNewStreamers(edgeTransferObject, server, newListOfStreamers, listOfNewStreamers);
	}

	private void addNewStreamers(EdgeServerTransferObject edgeTransferObject, Server server, ArrayList<Streamer> newListOfStreamers, ArrayList<Streamer> listOfNewStreamers) {
		ConcurrentHashMap<String, List<String>> streamerToStreamSourcesMap = IM.getStreamerToStreamMap();

		int totalNumStreamers = newListOfStreamers.size();
		for (Streamer s : listOfNewStreamers) {
			List<String> URLs = streamerToStreamSourcesMap.get(s.getStreamkey() + "");
			if (URLs == null) {
				URLs = Collections.synchronizedList(new ArrayList<String>());
				URLs.add(s.getPageurl());
				streamerToStreamSourcesMap.put(s.getStreamkey() + "", URLs);
			} else {
				URLs.add(s.getPageurl());
			}
		}

		Calendar now = Calendar.getInstance();

		server.setOb(edgeTransferObject);
		server.setStreamerCount(totalNumStreamers);
		server.setTimeAtLastUpdate(now);
	}

	private int separateActiveFromInactiveStreamers(ArrayList<Streamer> newListOfStreamers, ArrayList<Streamer> noLongerStreamingList, ArrayList<Streamer> listOfNewStreamers) {
		int totalNumViewers = 0;
		for (Streamer s1 : newListOfStreamers) {
			totalNumViewers += s1.getNviewers();
			Streamer match = null;
			for (Iterator<Streamer> iterator = noLongerStreamingList.iterator(); iterator.hasNext();) {
				Streamer s2 = iterator.next();
				if (s1.getStreamkey() == s2.getStreamkey() && s1.getPageurl().equals(s2.getPageurl())) {
					match = s2;
					iterator.remove();
					break;
				}
			}

			if (match == null) {
				listOfNewStreamers.add(s1);
			}
		}

		return totalNumViewers;
	}

	private void removeStreamersNoLongerStreaming(ArrayList<Streamer> noLongerStreamingList) {
		ConcurrentHashMap<String, List<String>> streamerToStreamSourcesMap = IM.getStreamerToStreamMap();
		for (Streamer noLonger : noLongerStreamingList) {
			List<String> URLs = streamerToStreamSourcesMap.get(noLonger.getStreamkey() + "");
			URLs.remove(noLonger.getPageurl());

			if (URLs.isEmpty()) {
				streamerToStreamSourcesMap.remove(noLonger.getStreamkey() + "");
			}
		}
	}

	private void addEdgeServerToIM(
			EdgeServerTransferObject edgeTransferObject,
			String serverName, ConcurrentObservableList serverList,
			HashMap<String, Integer> serverToIndexMap) {

		Server newServer = createServerObject(edgeTransferObject, serverName);
		int newServerIndex = serverList.addItem(newServer);
		serverToIndexMap.put(serverName, newServerIndex);
	}

	private Server createServerObject(EdgeServerTransferObject edgeTransferObject, String serverName) {
		ArrayList<Streamer> listOfStreamers = edgeTransferObject.getStreamers();

		int numOfStreamers = listOfStreamers.size();
		int totalNumViewers = 0;

		for (int i = 0; i < numOfStreamers; i++){
			Streamer tempStream = listOfStreamers.get(i);
			totalNumViewers += tempStream.getNviewers();

			String pgURL = tempStream.getPageurl();
			String streamKey = tempStream.getStreamkey() + "";

			ConcurrentHashMap<String, List<String>> streamerToStreamSourcesMap = IM.getStreamerToStreamMap();
			List<String> availableLinks = streamerToStreamSourcesMap.get(streamKey);

			if (availableLinks == null) {
				List<String> source = Collections.synchronizedList(new ArrayList<String>());
				source.add(pgURL);
				streamerToStreamSourcesMap.put(streamKey, source);
			} else {
				availableLinks.add(pgURL);
			}
		}

		Calendar now = Calendar.getInstance();
		Server newServer = new Server(serverName, totalNumViewers, numOfStreamers, edgeTransferObject, now, true);
		return newServer;
	}


	private void displayInTextField(final String res) {

		final String response = res;

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				controller.outputText(response);
			}
		});
	}
}

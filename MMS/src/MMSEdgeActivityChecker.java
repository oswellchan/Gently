import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;


public class MMSEdgeActivityChecker implements Runnable {
	private GUIController controller = GUIController.getInstance();
	private InternalMemory IM = InternalMemory.getInstance();
	private final static Logger _logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private ConcurrentObservableList serverList = IM.getServerList();
	private final long MAXIMUMTIMEDIFFERNCE = 15000;
	
	MMSEdgeActivityChecker() {
		
	}
	
	@Override
	public void run() {
		while (true) {
			int size = serverList.getSize();
			Calendar now = Calendar.getInstance();
			for (int i = 0; i < size; i++) {
				Server s = serverList.getItem(i);
				if (s.isActive()) {
					Calendar lastUpdate = s.getTimeAtLastUpdate();
					long timeDifference = now.getTimeInMillis() - lastUpdate.getTimeInMillis();
					if (timeDifference > MAXIMUMTIMEDIFFERNCE) {
						s.setIsActive(false);
						ArrayList<Streamer> streamers = s.getOb().getStreamers();
						ConcurrentHashMap<String, List<String>> streamToLinks = IM.getStreamerToStreamMap();
						for (Streamer stream : streamers) {
							List<String> links = streamToLinks.get(stream.getStreamkey() + "");
							if (links != null) {
								links.remove(stream.getPageurl());
								if (links.isEmpty()) {
									streamToLinks.remove(stream.getStreamkey() + "");
								}
							}
						}
						
						serverList.refresh();
					}
				}
			}
		}
	}
	
	
}

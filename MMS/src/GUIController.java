import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONException;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class GUIController {

	private static final String MSG_WRONGWEBPORT = "Invalid Web Port. Please choose a port between 1 to 65535.";
	private static final String MSG_WRONGSERVERPORT = "Invalid Server Port. Please choose a port between 1 to 65535.";
	private static final String MSG_SAMEPORTNO = "Web Port and Server Port cannot be the same. Please choose another.";
	private static final String LOG_EXITMMS = "Exiting MMS";
	private static final String LOGO = "file:logo.png";
	private static final String CSS_CLOSEBUTTON = "closeButton";
	private static final String CSS_SERVERLISTCELL = "serverListCell";
	
	private static final int MINSERVERPORT = 1;
	private static final int MAXSERVERPORT = 65535;
	
	private final static Logger _logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	static GUIController controller= null;
	InternalMemory IM = null;

	@FXML
	private ResourceBundle resources = null;

	@FXML
	private URL location = null;

	@FXML
	private Button addButton = null;

	@FXML
	private ImageView logo = null;

	@FXML
	protected TextArea outputWindow = null;

	@FXML
	private Label closeButton = null;

	@FXML
	private Button removeButton = null;
	
	@FXML
	private ListView<Server> serverList = null;

	@FXML
	private TextField serverPortTextField = null;

	@FXML
	private Button startButton= null;

	@FXML
	private TextField webPortTextField = null;

	@FXML
	void closeApplication(MouseEvent event) {
		_logger.log(Level.INFO, LOG_EXITMMS);
		System.exit(0);
	}

	@FXML
	void openAddWindow(MouseEvent event) {
		//Implement adding of edgeServers to serverList manually
		printIMState();
	}

	@FXML
	void removeSelection(MouseEvent event) {
		//Implement deleting of edgeServers from serverList manually
	}

	@FXML
	void startListeners(MouseEvent event) {
		
		String webPortInString = webPortTextField.getText();
		String serverPortInString = serverPortTextField.getText();

		int webPort = parsePort(webPortInString);
		int serverPort = parsePort(serverPortInString);

		if (portsAreValid(webPort, serverPort)) {
			try {
				MMSWeb webComponent = new MMSWeb(webPort);
				Thread webThread = new Thread(webComponent);

				MMSEdge edgeComponent = new MMSEdge(serverPort);
				Thread edgeThread = new Thread(edgeComponent);
				
				savePortNumbersToFile(webPort, serverPort);

				webThread.start();
				edgeThread.start();
				
			} catch (Exception e) {
				_logger.log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}


	@FXML
	void initialize() {
		assert addButton != null : "fx:id=\"addButton\" was not injected: check your FXML file 'MMSGUI.fxml'.";
		assert logo != null : "fx:id=\"logo\" was not injected: check your FXML file 'MMSGUI.fxml'.";
		assert outputWindow != null : "fx:id=\"outputWindow\" was not injected: check your FXML file 'MMSGUI.fxml'.";
		assert removeButton != null : "fx:id=\"removeButton\" was not injected: check your FXML file 'MMSGUI.fxml'.";
		assert serverList != null : "fx:id=\"serverList\" was not injected: check your FXML file 'MMSGUI.fxml'.";
		assert serverPortTextField != null : "fx:id=\"serverPortTextField\" was not injected: check your FXML file 'MMSGUI.fxml'.";
		assert startButton != null : "fx:id=\"startButton\" was not injected: check your FXML file 'MMSGUI.fxml'.";
		assert webPortTextField != null : "fx:id=\"webPortTextField\" was not injected: check your FXML file 'MMSGUI.fxml'.";

		styleCloseButtonWithCSS();

		setupGentlyLogo();

		IM = InternalMemory.getInstance();
		
		populateGUIwithPortNos();

		controller = this;
		
		initServerList();
	}
	
	public static GUIController getInstance() {
		return controller;
	}
	
	private void savePortNumbersToFile(int webPort, int serverPort) throws JSONException {
		IM.updatePortNos(webPort, serverPort);
		Storage.getInstance().saveStateToFile();
	}

	private boolean portsAreValid(int webPort, int serverPort) {
		
		boolean portsAreValid = true;
		
		if (webPort == -1) {
			portsAreValid = false;
			outputText(MSG_WRONGWEBPORT);
		}

		if (serverPort == -1) {
			portsAreValid = false;
			outputText(MSG_WRONGSERVERPORT);
		}

		if (serverPort == webPort) {
			portsAreValid = false;
			outputText(MSG_SAMEPORTNO);
		}
		
		return portsAreValid;
	}

	private void populateGUIwithPortNos() {
		webPortTextField.setText("" + IM.getWebPort());
		serverPortTextField.setText("" + IM.getServerPort());
	}

	private void setupGentlyLogo() {
		Image gentlyLogo = new Image(LOGO);
		logo.setImage(gentlyLogo);
	}

	private void styleCloseButtonWithCSS() {
		closeButton.getStyleClass().add(CSS_CLOSEBUTTON);
	}
	
	private void initServerList() {
		//Set serverList to observe the list of servers in Internal Memory 
		serverList.setItems(IM.getServerList().getList());
	        setServerCellFactory();
	}
	
	private void setServerCellFactory() {
		serverList.setCellFactory(new Callback<ListView<Server>, ListCell<Server>>() {
			@Override
			public ListCell<Server> call(ListView<Server> list) {
				ServerListCell listCell = new ServerListCell();
				listCell.getStyleClass().add(CSS_SERVERLISTCELL);
				return listCell;
			}
		});
	}

	private int parsePort(String s) {

		int i = -1;

		try {
			i = Integer.parseInt(s);
		} catch (Exception e) {
			return -1;
		}
		
		if (i < MINSERVERPORT || i > MAXSERVERPORT) {
			return -1;
		}

		return i;
	}

	public void outputText(String s) {
		String timeStamp = createTimeStamp();
		outputWindow.appendText(timeStamp + s + "\n");
	}

	private static String createTimeStamp() {
		Calendar cal = Calendar.getInstance();
		String YEAR;
		String DAY;
		String MONTH;
		String HOUR;
		String MIN;

		YEAR = cal.get(Calendar.YEAR) + "";
		DAY = convertToDoubleDigits(cal.get(Calendar.DAY_OF_MONTH));
		MONTH = convertToDoubleDigits(cal.get(Calendar.MONTH));
		HOUR = convertToDoubleDigits(cal.get(Calendar.HOUR_OF_DAY));
		MIN = convertToDoubleDigits(cal.get(Calendar.MINUTE));

		String timeStamp = formatTimeToString(DAY, MONTH, HOUR, MIN,
				YEAR);

		return timeStamp;
	}

	private static String formatTimeToString(String DAY, String MONTH,
			String HOUR, String MIN, String YEAR) {
		String timeStamp = "[" + DAY + "/" + MONTH + "/" + YEAR + " "
				+ HOUR + ":" + MIN + "] ";

		return timeStamp;
	}
	
	private static String convertToDoubleDigits(int number) {
		String numberInString;
		
		if (number < 10 && number >= 0) {
			numberInString = "0" + number;
		} else {
			numberInString = "" + number;
		}
		
		return numberInString;
	}
	
	public static void printIMState() {
		InternalMemory IM = InternalMemory.getInstance();
		ObservableList<Server> obList = IM.getServerList().getList();
		HashMap<String, Integer> map = IM.getServerToIndexMap();
		ConcurrentHashMap<String, List<String>> map2 = IM.getStreamerToStreamMap();
		
		int i = 0;
		for (Server s : obList) {
			i++;
			System.out.println(i + ". Server: " + s.getServerName());
		}
		
		Set<Entry<String, Integer>> setOfEntries = map.entrySet();
		
		i = 0;
		for (Map.Entry<String, Integer> e : setOfEntries) {
			i++;
			System.out.println(i + ". Name: " + e.getKey() + " index: " + e.getValue());
		}
		
		Set<Entry<String, List<String>>> setOfEntries2 = map2.entrySet();
		
		i = 0;
		for (Entry<String, List<String>> e2 : setOfEntries2) {
			i++;
			String r = i + ". Name: " + e2.getKey()+ " List: ";
			for (String l : e2.getValue()) {
				r += l + ", ";
			}
			
			System.out.println(r);
		}
	}

}
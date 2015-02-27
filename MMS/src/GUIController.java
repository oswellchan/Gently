import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	private final static Logger _logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	static GUIController controller;
	InternalMemory IM;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button addButton;

	@FXML
	private ImageView logo;

	@FXML
	protected TextArea outputWindow;

	@FXML
	private Label closeButton;

	@FXML
	private Button removeButton;
	
	@FXML
	private ListView<Server> serverList;

	@FXML
	private TextField serverPortTextField;

	@FXML
	private Button startButton;

	@FXML
	private TextField webPortTextField;

	@FXML
	void closeApplication(MouseEvent event) {
		System.exit(0);
	}

	@FXML
	void openAddWindow(MouseEvent event) {
	}

	@FXML
	void removeSelection(MouseEvent event) {
	}

	@FXML
	void startListeners(MouseEvent event) {

		boolean portsAreValid = true;
		String webPortInString = webPortTextField.getText();
		String serverPortInString = serverPortTextField.getText();

		int webPort = parsePort(webPortInString);
		int serverPort = parsePort(serverPortInString);

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

		if (portsAreValid) {
			try {
				MMSWeb webComponent = new MMSWeb(webPort);
				Thread webThread = new Thread(webComponent);

				MMSEdge edgeComponent = new MMSEdge(serverPort);
				Thread edgeThread = new Thread(edgeComponent);

				InternalMemory mem = InternalMemory.getInstance();
				mem.updatePortNos(webPort, serverPort);

				Storage.getInstance().saveStateToFile();

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

		closeButton.getStyleClass().add("closeButton");

		Image gentlyLogo = new Image("file:logo.png");
		logo.setImage(gentlyLogo);

		IM = InternalMemory.getInstance();
		webPortTextField.setText("" + IM.getWebPort());
		serverPortTextField.setText("" + IM.getServerPort());

		controller = this;
		initServerList();
	}
	
	private void initServerList() {
		//Set list to observe eventItems in Internal Memory
		serverList.setItems(IM.getServerList());
	        
	        setEventCellFactory();
	}
	
	private void setEventCellFactory() {
		serverList.setCellFactory(new Callback<ListView<Server>, ListCell<Server>>() {
			@Override
			public ListCell<Server> call(ListView<Server> list) {
				ServerListCell listCell = new ServerListCell();
				listCell.getStyleClass().add("serverListCell");
				return listCell;
			}
		});
	}

	public static GUIController getInstance() {

		return controller;

	}

	private int parsePort(String s) {

		int i = -1;

		try {
			i = Integer.parseInt(s);
		} catch (Exception e) {
			return -1;
		}

		if (i < 1 && i > 65535) {
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
		String DAY;
		String MONTH;
		String HOUR;
		String MIN;

		DAY = setDay(cal);
		MONTH = setMonth(cal);
		int year = cal.get(Calendar.YEAR);
		HOUR = setHour(cal);
		MIN = setMinutes(cal);

		String timeStamp = formatTimeToString(DAY, MONTH, HOUR, MIN,
				year);

		return timeStamp;
	}

	private static String formatTimeToString(String DAY, String MONTH,
			String HOUR, String MIN, int year) {
		String timeStamp = "[" + DAY + "/" + MONTH + "/" + year + " "
				+ HOUR + ":" + MIN + "] ";

		return timeStamp;
	}

	private static String setMinutes(Calendar cal) {
		String MIN;
		int minutes = cal.get(Calendar.MINUTE);
		if (minutes < 10) {
			MIN = "0" + minutes;
		} else {
			MIN = "" + minutes;
		}
		return MIN;
	}

	private static String setHour(Calendar cal) {
		String HOUR;
		int hours = cal.get(Calendar.HOUR_OF_DAY);
		if (hours < 10) {
			HOUR = "0" + hours;
		} else {
			HOUR = "" + hours;
		}
		return HOUR;
	}

	private static String setMonth(Calendar cal) {
		String MONTH;
		int month = cal.get(Calendar.MONTH) + 1;
		if (month < 10) {
			MONTH = "0" + month;
		} else {
			MONTH = "" + month;
		}
		return MONTH;
	}

	private static String setDay(Calendar cal) {
		String DAY;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		if (day < 10) {
			DAY = "0" + day;
		} else {
			DAY = "" + day;
		}
		return DAY;
	}
}

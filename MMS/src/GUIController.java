

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;


public class GUIController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private static TextArea outputWindow;

    @FXML
    private Button startButton;
    
    static MMSWeb webComponent;

    @FXML
    void handleStartApplication(MouseEvent event) {
	System.out.println("activated");
	activate();
    }

    @FXML
    void initialize() {
        assert outputWindow != null : "fx:id=\"outputWindow\" was not injected: check your FXML file 'MMSGUI.fxml'.";
        assert startButton != null : "fx:id=\"startButton\" was not injected: check your FXML file 'MMSGUI.fxml'.";


    }
    
    public static void activate() {
	try {
	    webComponent = new MMSWeb(9001);
	    System.out.println("starting");
	    Thread webThread = new Thread(webComponent);
	    
	    webThread.start();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
    
    @FXML
    public static void onEnter(KeyEvent event) {
	if (event.getCode().equals(KeyCode.ENTER)) {
	   
	    String instruction = outputWindow.getText();
	    String[] thing = instruction.split(" ");
	    int v1 = Integer.parseInt(thing[0].trim());
	    int v2 = Integer.parseInt(thing[1].trim());
	    
	    InternalMemory mem = InternalMemory.getInstance();
	    mem.setLoad(v1, v2);
	    outputWindow.clear();
	}
    }
}




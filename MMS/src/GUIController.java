import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


public class GUIController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addButton;

    @FXML
    private ImageView logo;

    @FXML
    private TextArea outputWindow;

    @FXML
    private Button removeButton;

    @FXML
    private Button startButton;

    @FXML
    private Button stopButton;


    @FXML
    void openAddWindow(MouseEvent event) {
    }

    @FXML
    void removeSelection(MouseEvent event) {
    }

    @FXML
    void startListeners(MouseEvent event) {
	
    }

    @FXML
    void stopListeners(MouseEvent event) {
    }

    @FXML
    void initialize() {
        assert addButton != null : "fx:id=\"addButton\" was not injected: check your FXML file 'MMSGUI.fxml'.";
        assert logo != null : "fx:id=\"logo\" was not injected: check your FXML file 'MMSGUI.fxml'.";
        assert outputWindow != null : "fx:id=\"outputWindow\" was not injected: check your FXML file 'MMSGUI.fxml'.";
        assert removeButton != null : "fx:id=\"removeButton\" was not injected: check your FXML file 'MMSGUI.fxml'.";
        assert startButton != null : "fx:id=\"startButton\" was not injected: check your FXML file 'MMSGUI.fxml'.";
        assert stopButton != null : "fx:id=\"stopButton\" was not injected: check your FXML file 'MMSGUI.fxml'.";

        Image gentlyLogo  = new Image("file:logo.png");
        logo.setImage(gentlyLogo);
    }

}

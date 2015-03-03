import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;
import java.net.URL;

import org.json.JSONException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MMSMain extends Application {
	
	private final String FXMLFILE = "MMSGUI.fxml";
	private final String FONTAWESOMEFONTFILE = "fontawesome-webfont.ttf";
	private final String CSSFILE = "gently.css";
	private final String LOGOFILE = "file:logo.png";
	private final String CSS_ANCHOR = "anchor";
	
	private double _xOffset = 0;
	private double _yOffset = 0;
	private static FXMLLoader _loader = null;
	private final static Logger _logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			ApplicationLogger.setup();
			initialiseStorage();
			initialiseGUI(primaryStage);

		} catch (Exception e) {
			_logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	private void initialiseStorage() {
		try {
			_logger.log(Level.INFO, "Storage initialising");
			Storage.getInstance();

		} catch (JSONException e) {
			_logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	private void initialiseGUI(Stage primaryStage) throws IOException {
		
		loadFXML(FXMLFILE);
		loadFont(FONTAWESOMEFONTFILE);
		String CSS = getClass().getResource(CSSFILE).toExternalForm();
		
		//Create scene
		AnchorPane root = customiseRoot();
		Scene scene = createScene(CSS, root);
		
		createStage(primaryStage, scene);

		setGUIMovable(primaryStage, root);

		primaryStage.show();
	}

	private void createStage(Stage primaryStage, Scene scene) {
		customiseStage(primaryStage);
		primaryStage.setScene(scene);
	}

	private void customiseStage(Stage primaryStage) {
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.getIcons().add(new Image(LOGOFILE));
	}

	private Scene createScene(String CSS, AnchorPane root) {
		Scene scene = new Scene(root);
		scene.getStylesheets().add(CSS);
		return scene;
	}

	private AnchorPane customiseRoot() throws IOException {
		AnchorPane root = (AnchorPane) _loader.load();
		root.getStyleClass().add(CSS_ANCHOR);
		return root;
	}

	private void loadFXML(String FXMLFilePath) {
		URL FXML = getClass().getResource(FXMLFilePath);
		_loader = new FXMLLoader(FXML);
	}

	private void loadFont(String fontFilePath) {
		String fontAwesomeFont = getClass().getResource(fontFilePath).toExternalForm();
		Font.loadFont(fontAwesomeFont, 20);
	}

	private void setGUIMovable(final Stage primaryStage, AnchorPane root) {

		root.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				_xOffset = event.getSceneX();
				_yOffset = event.getSceneY();
			}
		});

		root.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				primaryStage.setX(event.getScreenX() - _xOffset);
				primaryStage.setY(event.getScreenY() - _yOffset);
			}
		});
	}
}

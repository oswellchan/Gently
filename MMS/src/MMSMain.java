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

	private double xOffset = 0;
	private double yOffset = 0;
	private static FXMLLoader loader;
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
			Storage.getInstance();

		} catch (JSONException e) {
			_logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	private void initialiseGUI(Stage primaryStage) throws IOException {
		
		// Load resources needed, i.e. FXML file
		loader = new FXMLLoader(getClass().getResource("MMSGUI.fxml"));
		AnchorPane root = (AnchorPane) loader.load();
		Font.loadFont(MMSMain.class.getResource("fontawesome-webfont.ttf").toExternalForm(), 20);
		root.getStyleClass().add("anchor");

		// Load CSS file used to style GUI
		Scene scene = new Scene(root);
		scene.getStylesheets().add(
				getClass().getResource("gently.css")
						.toExternalForm());
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.getIcons().add(new Image("file:logo.png"));
		
		primaryStage.setScene(scene);

		setGuiMovable(primaryStage, root);

		primaryStage.show();
	}

	private void setGuiMovable(final Stage primaryStage, AnchorPane root) {

		root.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xOffset = event.getSceneX();
				yOffset = event.getSceneY();
			}
		});

		root.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				primaryStage.setX(event.getScreenX() - xOffset);
				primaryStage.setY(event.getScreenY() - yOffset);
			}
		});
	}
}

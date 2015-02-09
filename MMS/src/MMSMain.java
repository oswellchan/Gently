import java.util.*;
import java.io.*;
import java.net.*;

import org.json.JSONException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
	 try {
	     
	     initialiseStorage();
	     initialiseGUI(primaryStage);
		
	    } catch(Exception e) {
		e.printStackTrace();
	    }
    }

    private void initialiseStorage() {
	try {
	    Storage.getInstance();
	    
	} catch (JSONException e) {
	    e.printStackTrace();
	}
    }

    private void initialiseGUI(Stage primaryStage) throws IOException {
	//Load resources needed, i.e. FXML file and font
	loader = new FXMLLoader(getClass().getResource("MMSGUI.fxml"));
	AnchorPane root = (AnchorPane) loader.load();
	root.getStyleClass().add("anchor");
	
	//Load CSS file used to style GUI
	Scene scene = new Scene(root);
	scene.getStylesheets().add(getClass().getResource("gently.css").toExternalForm());
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


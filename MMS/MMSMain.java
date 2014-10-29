package gently.MMS;

import java.util.*;
import java.io.*;
import java.net.*;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MMSMain extends Application {
    
    public static void main(String[] args) {
        launch(args); 
    }
    
    @Override
    public void start(Stage primaryStage) {
	 try {
		//Load resources needed, i.e. FXML file and font
		AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("MMSGUI.fxml"));
		
		Button exitButton = initExitButton();
		
		//Add items to GUI
		root.getChildren().add(exitButton);
		
		//Load CSS file used to style GUI
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	    } catch(Exception e) {
		e.printStackTrace();
	    }
    }
    
    private Button initExitButton() {
	    Button startButton = new Button("Exit");
	    startButton.setLayoutX(400);
	    startButton.setLayoutY(20);
	    
	    startButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
	        public void handle(MouseEvent me) {
	            System.exit(0);
	        }
	    });
	    return startButton;
    }
}


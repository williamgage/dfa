package dfa;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import com.jfoenix.controls.JFXSpinner;
import javafx.animation.PauseTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.util.Duration;

/**
 * Class for controlling the DFA interface.
 * @author williamgage
 *
 */
public class MainWindowController {
	
	@FXML
	private Label labelSuccess, acceptStatus, close, dragLabel;
	
	@FXML
	private JFXSpinner spinner;
	
	@FXML
	private Button selectDFA;
	
	@FXML
	private TextField input;
	
	private DFA generatedDFA;
    private double xOffset = 0;
    private double yOffset = 0;
	
    /**
     *  Initializes window
     */
	@FXML
	public void initialize() {
		spinner.setVisible(false);
		input.setVisible(false);
		labelSuccess.setVisible(false);
		acceptStatus.setVisible(false);
	    acceptStatus.setAlignment(Pos.CENTER);
	    acceptStatus.setWrapText(true);
		
	}
	
	/**
	 * Allows a user to select a file from the filesystem.
	 */
	public void handleSelectDFA() {
		
		try {		
			
			FileChooser fileChooser = new FileChooser();
			File selectedFile = fileChooser.showOpenDialog(selectDFA.getScene().getWindow());
			
			// List to store lines written in file.
			ArrayList<String> rules = new ArrayList<String>();
			
			if (input.isVisible()) {
				input.setVisible(false);
				input.setText("");
				labelSuccess.setVisible(false);
				acceptStatus.setText("");
			}
			spinner.setVisible(true);
			
			// Simple transitions for interface.
			PauseTransition delay = new PauseTransition(Duration.seconds(2));
			PauseTransition delay2 = new PauseTransition(Duration.seconds(3));

			delay2.setOnFinished(event -> 
			{
				labelSuccess.setVisible(false);
			});
			
			delay.setOnFinished(event -> 
			{
				spinner.setVisible(false);
				input.setVisible(true);
				labelSuccess.setVisible(true);
				delay2.play();
			});
			
			// Read the file.
			BufferedReader reader;
			reader = new BufferedReader(new FileReader(selectedFile));
			String line = reader.readLine();
			while (line != null) {
				rules.add(line);
				line = reader.readLine();
			}
			reader.close();
						
			// Generate DFA based on given rules.
			generatedDFA = new DFA(rules);
			
			delay.playFromStart();
		}
		catch (Exception ex) {
			
			Window owner = selectDFA.getScene().getWindow();
			input.setVisible(false);
			labelSuccess.setVisible(false);
			spinner.setVisible(false);
			AlertHelper.showAlert(AlertType.ERROR, owner, "Error", "Unable to create DFA from file provided.");
			return;
		}
	}
		
	/**
	 * Handles the string input by the user. 
	 */
	public void submitString() {
		
		String tInput = input.getText();
		boolean result = generatedDFA.testStringGUI(tInput);
		
		if (result) {
			acceptStatus.setText("String " + tInput + " accepted!");
			acceptStatus.setVisible(true);
		}
		else {
			acceptStatus.setText("String " + tInput + " not accepted.");
			acceptStatus.setVisible(true);
		}
		
	}
	
	
	/**
	 * Class to show alerts.
	 */
	private static class AlertHelper {

	    private static void showAlert (Alert.AlertType alertType, Window owner, String title, String message) {
	        Alert alert = new Alert(alertType);
	        alert.setTitle(title);
	        alert.setHeaderText(null);
	        alert.setContentText(message);
	        alert.initOwner(owner);
	        alert.show();
	    }
	}
	
	/**
	 * Handle close button.
	 */
	public void closeWindow() {
		System.exit(0);
	}
	
	/**
	 *  Allows the user to move the window since the scene has no title bar. 
	 * @param event
	 */
	public void moveWindow(MouseEvent event) {
		acceptStatus.getScene().getRoot().setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
		});
		
		acceptStatus.getScene().getRoot().setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                acceptStatus.getScene().getWindow().setX(event.getScreenX() - xOffset);
                acceptStatus.getScene().getWindow().setY(event.getScreenY() - yOffset);
            }
        });
	}
}



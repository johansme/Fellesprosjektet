package messages;

import calendarGUI.ControllerInterface;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ConfirmController implements MessageController {
	
	@FXML private Label confirmationInfo;
	@FXML private Button okButton;
	@FXML private Button cancelButton;
	@FXML private AnchorPane screen;
	
	private ControllerInterface referenceController;
	
	public void setMessage(String msg) {
		confirmationInfo.setText(msg);
	}
	
	@FXML
	private void okButtonPressed(Event e) {
		if (e.getSource().equals(screen)) {
			if (!(((KeyEvent) e).getCode()==KeyCode.ENTER)) {
				return;
			}
		}
		//TODO Fix functionality for confirming action 
		// get a handle to the stage
	    Stage stage = (Stage) okButton.getScene().getWindow();
	    stage.close();
	    referenceController.setFeedback();
	}
	
	@FXML
	private void cancelButtonPressed() {
		// get a handle to the stage
	    Stage stage = (Stage) cancelButton.getScene().getWindow();
	    stage.close();
	}

	@Override
	public void setReferenceController(ControllerInterface controller) {
		referenceController = controller;
		
	}

}

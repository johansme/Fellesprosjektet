package messages;

import calendarGUI.ControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ConfirmController implements MessageController {
	
	@FXML private Label confirmationInfo;
	@FXML private Button okButton;
	@FXML private Button cancelButton;
	
	private ControllerInterface referenceController;
	
	public void setMessage(String msg) {
		confirmationInfo.setText(msg);
	}
	
	@FXML
	private void okButtonPressed() {
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

package messages;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ConfirmController {
	
	@FXML private Label confirmationInfo;
	@FXML private Button okButton;
	@FXML private Button cancelButton;
	
	public void setMessage(String msg) {
		confirmationInfo.setText(msg);
	}
	
	@FXML
	private void okButtonPressed() {
		//TODO Fix functionality for confirming action 
		// get a handle to the stage
	    Stage stage = (Stage) okButton.getScene().getWindow();
	    stage.close();
	}
	
	@FXML
	private void cancelButtonPressed() {
		// get a handle to the stage
	    Stage stage = (Stage) cancelButton.getScene().getWindow();
	    stage.close();
	}

}

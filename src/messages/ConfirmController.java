package messages;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ConfirmController {
	
	@FXML private Label confirmationInfo;
	@FXML private Button okButton;
	@FXML private Button cancelButton;
	
	public void setMessage(String msg) {
		confirmationInfo.setText(msg);
	}
	
	@FXML
	private void okButtonPressed() {
		
	}
	
	@FXML
	private void cancelButtonPressed() {
		
	}

}

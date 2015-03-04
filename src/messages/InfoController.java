package messages;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class InfoController implements MessageController {
	
	@FXML private Label infoMessage;
	@FXML private Button okButton;
	
	public void setMessage(String msg) {
		infoMessage.setText(msg);
	}
	
	@FXML
	private void okButtonPressed() {
		// get a handle to the stage
	    Stage stage = (Stage) okButton.getScene().getWindow();
	    stage.close();
	}

}

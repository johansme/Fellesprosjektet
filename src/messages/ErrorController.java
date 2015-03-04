package messages;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ErrorController implements MessageController {
	
	@FXML private Label errorMessage;
	@FXML private Button okButton;

	@Override
	public void setMessage(String msg) {
		errorMessage.setText(msg);
	}
	
	@FXML
	private void okButtonPressed() {
		// get a handle to the stage
	    Stage stage = (Stage) okButton.getScene().getWindow();
	    stage.close();
	}

}

package messages;

import calendarGUI.ControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class InfoController implements MessageController {
	
	@FXML private Label infoMessage;
	@FXML private Button okButton;
	
	private ControllerInterface referenceController;
	
	public void setMessage(String msg) {
		infoMessage.setText(msg);
	}
	
	@FXML
	private void okButtonPressed() {
		// get a handle to the stage
	    Stage stage = (Stage) okButton.getScene().getWindow();
	    stage.close();
	}

	@Override
	public void setReferenceController(ControllerInterface controller) {
		referenceController=controller;
		
	}

}

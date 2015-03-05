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

public class ErrorController implements MessageController {
	
	@FXML private Label errorMessage;
	@FXML private Button okButton;
	@FXML private AnchorPane screen;

	@Override
	public void setMessage(String msg) {
		errorMessage.setText(msg);
	}
	
	@FXML
	private void okButtonPressed(Event e) {
		
		if (e.getSource().equals(screen)) {
			if (!(((KeyEvent) e).getCode()==KeyCode.ENTER)) {
				return;
			}
		}

		// get a handle to the stage
	    Stage stage = (Stage) okButton.getScene().getWindow();
	    stage.close();
	}

	@Override
	public void setReferenceController(ControllerInterface controller) {
		// TODO Auto-generated method stub
		
	}

}

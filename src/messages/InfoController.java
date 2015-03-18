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

public class InfoController implements MessageController {
	
	@FXML private Label infoMessage;
	@FXML private Button okButton;
	@FXML private AnchorPane screen;
	
	public void setMessage(String msg) {
		infoMessage.setText(msg);
	}
	
	@FXML
	private void okButtonPressed(Event e) {

		// get a handle to the stage
	    Stage stage = (Stage) okButton.getScene().getWindow();
	    stage.close();
	}
	
	@FXML
	private void keyPressed(KeyEvent e) {
		if (e.getCode()==KeyCode.ENTER) {
			okButtonPressed((Event) e);
		}
		else if (e.getCode()==KeyCode.ESCAPE) {
			okButtonPressed((Event) e);
		}
		else {
			return;
		}
	}


	@Override
	public void setReferenceController(ControllerInterface controller) {
		
	}

}

package calendarGUI;

import calendar.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditUserPasswordController {
	
	@FXML private Label userLabel;
	@FXML private PasswordField newPasswordField;
	@FXML private Button cancelButton;
	@FXML private Button saveButton;
	
	private User user;
	
	
	@FXML
	private void cancelAction(){
		// get a handle to the stage
				Stage stage = (Stage)cancelButton.getScene().getWindow();
				stage.close();
	}
	
	@FXML
	private void saveAction(){
		// save the changes to user:
		
		// get a handle to the stage
				Stage stage = (Stage)cancelButton.getScene().getWindow();
				stage.close();
	}
	public void setUser(User usr){
		user = usr;
		userLabel.setText(user.getUsername());
	}
	
	
}

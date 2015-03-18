package calendarGUI;

import java.io.IOException;

import org.json.JSONObject;

import api.API;
import calendar.Calendar;
import calendar.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class EditUserPasswordController {

	@FXML private Label invalid;
	@FXML private Label userLabel;
	@FXML private PasswordField newPasswordField;
	@FXML private Button cancelButton;
	@FXML private Button saveButton;

	private User user;
	private Calendar calendar;


	@FXML
	private void cancelAction(){
		// get a handle to the stage
		Stage stage = (Stage)cancelButton.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void saveAction(){
		// save the changes to user:
		if (newPasswordField.getText().length() < 6 || ! newPasswordField.getText().matches("[\\wæøåÆØÅ]+")) {
			invalid.setVisible(true);
			return;
		}
		JSONObject obj = new JSONObject();
		obj.put("command", "change_password");
		obj.put("uid", user.getId());
		obj.put("password", newPasswordField.getText());
		try {
			API.call("/user", obj, calendar.getSession());
		} catch (IOException e) {
		}
		// get a handle to the stage
		Stage stage = (Stage)cancelButton.getScene().getWindow();
		stage.close();
	}
	public void setUser(User usr){
		user = usr;
		userLabel.setText(user.getUsername());
	}
	
	public void setData(Calendar calendar) {
		if (calendar != null) {
			this.calendar = calendar;
		}
	}


}

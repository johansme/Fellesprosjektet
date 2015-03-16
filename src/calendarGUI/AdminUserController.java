package calendarGUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import api.API;
import calendar.Appointment;
import calendar.Calendar;
import calendar.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AdminUserController implements ControllerInterface {

	@FXML private Tab rooms;

	@FXML private TabPane tabs;
	@FXML private Tab users;
	@FXML private TextField usernameField;
	@FXML private PasswordField passwordField;
	@FXML private CheckBox newAdmin;
	@FXML private Button addUserButton;
	@FXML private Button closeButton;

	@FXML private ListView<User> userListView;
	private List<User> userList = new ArrayList<User>();

	private Calendar calendar;

	@FXML
	private void addUser(){
		//:F
	}

	@FXML
	private void closeButtonPressed() {
		// get a handle to the stage
		Stage stage = (Stage)closeButton.getScene().getWindow();
		stage.close();
	}

	private void setUserList() {
		JSONObject obj = new JSONObject();
		obj.put("command", "get_all");
		try {
			JSONObject res = API.call("/user", obj, calendar.getSession());
			JSONArray resArray = res.getJSONArray("users");
			for (int i = 0; i < resArray.length(); i++) {
				User user = new User();
				JSONObject userObj = (JSONObject) resArray.get(i);
				user.fromJSON(userObj);
				userList.add(user);
				//TODO add to listView, incl. delete and change password
			}
		} catch (IOException e) {
		}
	}

	@Override
	public void setData(Calendar calendar) {
		if (calendar != null) {
			this.calendar = calendar;
			setUserList();
		}
	}

	@Override
	public Calendar getData() {
		return calendar;
	}

	@Override
	public void setData(Calendar c, Appointment a) {
		if (c != null) {
			this.calendar = c;
			setUserList();
		}
	}

	@Override
	public void setFeedback() {

	}

}

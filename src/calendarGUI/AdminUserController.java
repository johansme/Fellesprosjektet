package calendarGUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import api.API;
import calendar.Appointment;
import calendar.Calendar;
import calendar.Group;
import calendar.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import login.SceneHandler;

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
	
	@FXML
	private ListView<HBox> userElementsList;
	
	private List<User> userList = new ArrayList<User>();
	
	private HashMap<CheckBox, User> userElements = new HashMap<CheckBox, User>();

	private Calendar calendar;
	private SceneHandler sceneHandler = new SceneHandler();

	@FXML
	private void addUser(){
		if (isValidUser()) {
			User user = new User();
			user.setUsername(usernameField.getText());
			user.setAdmin(newAdmin.isSelected());
			//TODO
		} else {
			sceneHandler.popUpMessage("/messages/Error.fxml", 300, 150, "Insufficient user info", this);
		}
	}

	@FXML
	private void closeButtonPressed() {
		// get a handle to the stage
		Stage stage = (Stage)closeButton.getScene().getWindow();
		stage.close();
	}
	
	private void createUserList(){
		if (userList!=null && !userList.isEmpty()) {
			for (User usr : userList) {
				
				HBox line = new HBox();
				line.setPrefWidth((int)(userElementsList.getPrefWidth()*0.95));
				
				
				//label for the line:
				Label userLabel = new Label();
				userLabel.wrapTextProperty().set(true);
				userLabel.setFocusTraversable(false);
				userLabel.setText(usr.getName() +" " + usr.getSurname());

				
				//button to change password:
				Button changePassButton = new Button();
				changePassButton.setPrefWidth((int)(userElementsList.getPrefWidth()*0.4));
				changePassButton.setText("Change Password");
				
				
				changePassButton.setOnAction(new EventHandler<ActionEvent>() {
					@Override public void handle(ActionEvent e) {
						// launch change password window for targeted user:
						sceneHandler.popUpChangePassword("/calendarGUI/EditUserPasswordView.fxml", 250, 200, usr);
					}
				});
				
				//button to delete user:
				Button deleteUser = new Button();
				deleteUser.setPrefWidth((int)(userElementsList.getPrefWidth()*0.1));
				deleteUser.setText("X");
				
				
				deleteUser.setOnAction(new EventHandler<ActionEvent>() {
					@Override public void handle(ActionEvent e) {
						// remove user from database :O
						userElementsList.getItems().remove(line);
						
					}
				});
				
				userLabel.setPrefWidth(line.getPrefWidth()- changePassButton.getPrefWidth()-deleteUser.getPrefWidth());
				line.getChildren().addAll(userLabel, changePassButton,deleteUser);
				line.setFocusTraversable(false);
				
				
				
				userElementsList.getItems().add(line);
			}
	}
	}
	
	private boolean isValidUser() {
		//TODO
		return false;
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
		createUserList();
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

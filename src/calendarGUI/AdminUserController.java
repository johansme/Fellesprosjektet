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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import login.SceneHandler;

public class AdminUserController implements ControllerInterface {

	@FXML private Tab rooms;
	@FXML private AdminRoomController roomsPaneController;

	@FXML private TabPane tabs;
	@FXML private Tab users;
	@FXML private TextField surnameField;
	@FXML private TextField nameField;
	@FXML private TextField emailField;
	@FXML private TextField usernameField;
	@FXML private PasswordField passwordField;
	@FXML private CheckBox newAdmin;
	@FXML private Button addUserButton;
	@FXML private Button closeButton;
	
	private AdminUserController thisController = this;

	@FXML
	private ListView<HBox> userElementsList;

	private List<User> userList = new ArrayList<User>();


	private Calendar calendar;
	private SceneHandler sceneHandler = new SceneHandler();

	@FXML
	private void addUser(){
		if (isValidUser()) {
			User user = new User();
			user.setSurname(surnameField.getText());
			user.setName(nameField.getText());
			user.setEmail(emailField.getText());
			user.setUsername(usernameField.getText());
			user.setAdmin(newAdmin.isSelected());
			JSONObject obj = new JSONObject();
			obj.put("command", "create");
			obj.put("user", user.toJSON());
			obj.put("password", passwordField.getText());
			try {
				API.call("/user", obj, calendar.getSession());
				userList.add(user);
				createUserElement(user);
				surnameField.clear();
				nameField.clear();
				emailField.clear();
				usernameField.clear();
				passwordField.clear();
				newAdmin.selectedProperty().set(false);
			} catch (IOException e) {
				sceneHandler.popUpMessage("/messages/Error.fxml", 290, 140, "Something went wrong. Please try again.", this);
			}
		} else {
			sceneHandler.popUpMessage("/messages/Error.fxml", 290, 140, "Insufficient user info", this);
		}
	}

	@FXML
	private void roomsPressed() {

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
				createUserElement(usr);
			}
		}
	}
	
	private void createUserElement(User user) {
		HBox line = new HBox();
		line.setPrefWidth((int)(userElementsList.getPrefWidth()*0.95));
		line.alignmentProperty().set(Pos.CENTER);
		
		
		//label for the line:
		Label userLabel = new Label();
		userLabel.wrapTextProperty().set(true);
		userLabel.setFocusTraversable(false);
		userLabel.setText(user.getName() +" " + user.getSurname() + ", " + user.getEmail());
		
		userLabel.setFont(new Font(14));
		
		
		
		//button to change password:
		Button changePassButton = new Button();
		changePassButton.setPrefWidth((int)(userElementsList.getPrefWidth()*0.4));
		changePassButton.setText("Change Password");
		
		
		changePassButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				// launch change password window for targeted user:
				sceneHandler.popUpChangePassword("/calendarGUI/EditUserPasswordView.fxml", 240, 190, user, calendar);
			}
		});
		
		//button to delete user:
		Button deleteUser = new Button();
		deleteUser.setPrefWidth((int)(userElementsList.getPrefWidth()*0.1));
		deleteUser.setText("X");
		deleteUser.setTextFill(new Color(1, 0, 0, 1));
		
		
		deleteUser.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				delLine = line;
				sceneHandler.popUpMessage("/messages/Confirm.fxml", 290, 140, "Are you sure you want to delete?", thisController);
			}
		});
		
		userLabel.setPrefWidth(line.getPrefWidth()- changePassButton.getPrefWidth()-deleteUser.getPrefWidth());
		line.getChildren().addAll(userLabel, changePassButton,deleteUser);
		line.setFocusTraversable(false);
		
		
		
		userElementsList.getItems().add(line);
		
	}

	private boolean isValidUser() {
		if (! surnameField.getText().matches("([A-Z���][a-z���]+)(\\-[A-Z���][a-z���])?([a-z���]*)")) {
			return false;
		}
		if (! nameField.getText().matches("([A-Z���][a-z���]+)(\\-[A-Z���][a-z���])?([a-z���]*)")) {
			return false;
		}
		if (! emailField.getText().matches("[\\w_\\.������]+@[\\w_\\.������]+\\.[a-z]{2,3}")) {
			return false;
		}
		String usrnm = usernameField.getText();
		if (usrnm.length() < 5 || ! usrnm.matches("[a-z���]+")) {
			return false;
		}
		String srnm = surnameField.getText();
		String nm = nameField.getText();
		String eml = emailField.getText();
		for (User user : userList) {
			if (usrnm.equals(user.getUsername())) {
				return false;
			}
			if (srnm.equals(user.getSurname()) && nm.equals(user.getName()) && eml.equals(user.getEmail())) {
				return false;
			}
		}
		if (passwordField.getText().length() < 6 || ! passwordField.getText().matches("[\\w������]+")) {
			return false;
		}
		return true;
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
			roomsPaneController.setData(calendar);
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
			roomsPaneController.setData(c);
		}
	}

	@Override
	public void setFeedback() {
		delUser();
	}
	
	private HBox delLine;
	
	private void delUser() {
		int index =userElementsList.getItems().indexOf(delLine);
		User delUser = userList.get(index);
		JSONObject obj = new JSONObject();
		obj.put("command", "delete");
		obj.put("uid", delUser.getId());
		try {
			API.call("/user", obj, calendar.getSession());
			userList.remove(index);
			userElementsList.getItems().remove(delLine);
		} catch (IOException e1) {
			sceneHandler.popUpMessage("/messages/Error.fxml", 290, 140, "Something went wrong. Please try again.", thisController);
		}
	}

	@FXML
	private void keyPressed(KeyEvent e) {
		if (e.getCode()==KeyCode.ESCAPE) {
			closeButtonPressed();
		} else {
			return;
		}
	}

}

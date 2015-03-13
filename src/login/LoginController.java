package login;


import java.io.IOException;

import org.json.JSONObject;

import api.API;
import calendar.Appointment;
import calendar.Calendar;
import calendar.User;
import calendarGUI.ControllerInterface;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;


public class LoginController implements ControllerInterface {

	private LoginManager lman = new LoginManager();
	private SceneHandler sceneHandler = new SceneHandler();
	private Calendar calendar;
	private User loggingIn = new User();
	private String session;

	@FXML private TextField username;
	@FXML private TextField password;
	@FXML private Label errorMessage;
	@FXML private Button loginButton;
	@FXML private Pane screen;
		
	@FXML
	private void login(Event e){
		// get username and password

		if (e.getSource().equals(screen)) {
			if (!(((KeyEvent) e).getCode()==KeyCode.ENTER)) {
				return;
			}
		}

		String uname = username.textProperty().getValue();
		String pass = password.textProperty().getValue();

		//check if username and password corresponds

		if(lman.checkLogin(uname, pass)){
			session = lman.getSession();
			int uid = lman.getID();
			JSONObject obj = new JSONObject();
			obj.put("command", "get_by_id");
			obj.put("uid", uid);
			JSONObject res;
			try {
				res = API.call("/user", obj, session);
				loggingIn.fromJSON(res.getJSONObject("user"));
				calendar = new Calendar(loggingIn, session);
				sceneHandler.changeMonthRelatedScene(e, "/calendarGUI/MonthView.fxml",950,600, calendar);

			} catch (IOException e1) {
				errorMessage.textProperty().setValue("Invalid authentication credentials");				
			}

		}else{

			// no match, alert user.
			errorMessage.textProperty().setValue("Invalid authentication credentials");

		}

	}

	@Override
	public void setData(Calendar calendar) {
		this.calendar = calendar;
	}

	@Override
	public Calendar getData() {
		return calendar;
	}

	@Override
	public void setData(Calendar c, Appointment a) {
		calendar = c;
	}

	@Override
	public void setFeedback() {
		// TODO Auto-generated method stub
		
	}

}

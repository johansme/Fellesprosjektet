package login;


import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;


public class LoginController {

	LoginManager lman = new LoginManager();
	SceneHandler sceneHandler = new SceneHandler();
	
	
	@FXML TextField username;
	@FXML TextField password;
	@FXML Label errorMessage;
	@FXML Button loginButton;
	@FXML Pane screen;

	
	@FXML
	private void login(Event e){
		// get username and password
		
		if (e.getSource().equals(screen)) {
			if (!(((KeyEvent) e).getCode()==KeyCode.ENTER)) {
				return;
			}
		}
		
		String uname;
		String pass;
		uname = username.textProperty().getValue();
		pass = password.textProperty().getValue();
		
		//check if username and password corresponds
		
		if(lman.checkLogin(uname, pass)){
			
		 // its a match, proceeding to calendar
		// changing stage fxml file to calendar
			
			
			sceneHandler.changeMonthRelatedScene(e, "/calendarGUI/MonthView.fxml",950,600, null);
			
			
		
		}else{
			
			// no match, alert user.
			errorMessage.textProperty().setValue("Invalid authentication credentials");
			
		}
			
		}
	
	
	
	
	
	
	
	
	
}

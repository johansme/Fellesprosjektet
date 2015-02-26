package login;


import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class LoginController {

	LoginManager lman = new LoginManager();
	SceneHandler sceneHandler = new SceneHandler();
	
	
	@FXML TextField username;
	@FXML TextField password;
	@FXML Label errorMessage;
	@FXML Button loginButton;

	
	@FXML
	private void login(Event e){
		// get username and password
		
		String uname;
		String pass;
		uname = username.textProperty().getValue();
		pass = password.textProperty().getValue();
		
		//check if username and password corresponds
		
		if(lman.checkLogin(uname, pass)){
			
		 // its a match, proceeding to calendar
		// changing stage fxml file to calendar
			
			
			sceneHandler.changeScene("/calendarGUI/MonthView.fxml", e);
			sceneHandler.popUpScene("/newAppointment/NewAppointment.fxml",600,480);
			
		
		}else{
			
			// no match, alert user.
			errorMessage.textProperty().setValue("Invalid authentication credentials");
			
		}
			
		}
	
	
	
	
	
	
	
	
	
}

package login;


import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class LoginController {

	LoginManager lman = new LoginManager();
	
	
	
	@FXML TextField username;
	@FXML TextField password;
	@FXML TextField errorMessage;
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
		// harcore test!!!!
			try{
			Node node=(Node) e.getSource();
			  Stage stage=(Stage) node.getScene().getWindow();
			  Parent root = FXMLLoader.load(getClass().getResource("/calendarGUI/MonthView.fxml"));
			  Scene scene = new Scene(root);
			  stage.setScene(scene);
			  stage.show();
			} catch(Exception er) {
				er.printStackTrace();
			}
			
			//theMain.changeScene("/calendarGUI/MonthView.fxml");
		}else{
			
			// no match, alert user.
			errorMessage.textProperty().setValue("Wrong username or password, or maybe botch, i dont know:D");
			
		}
			
		}
	
	
	
	
	
	
}

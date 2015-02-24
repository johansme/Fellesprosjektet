package login;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class loginController {

	loginManager lman = new loginManager();

	
	
	@FXML TextField username;
	@FXML TextField password;
	@FXML TextField errorMessage;
	@FXML Button loginButton;
	
	
	@FXML
	private void login(){
		// get username and password
		String uname;
		String pass;
		uname = username.textProperty().getValue();
		pass = password.textProperty().getValue();
		
		//check if username and password corresponds
		if(lman.checkLogin(uname, pass)){
			
		 // its a match, proceeding to calendar
			
			System.out.println("goodie");
			
		}else{
			
			// no match, alert user.
			errorMessage.textProperty().setValue("Wrong username or password, or maybe botch, i dont know:D");
			
		}
			
		}
	
	
	
	
	
	
}

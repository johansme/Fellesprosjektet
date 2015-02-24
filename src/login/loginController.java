package login;


import javafx.fxml.FXML;
import javafx.scene.control.TextField;


public class loginController {

	loginManager lman = new loginManager();

	
	
	@FXML TextField username;
	@FXML TextField password;
	@FXML TextField errorMessage;
	
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
	@FXML
	private void hidePassword(){
		//for privacy reasons we have to hide the password from potential evil masterminds
		String pass;
		pass = password.textProperty().getValue();
		System.out.println(pass);
		String hider ="";
		for (int i = 0; i< pass.length();i++){
			hider += "*";
		}
		password.textProperty().set("hider");
	
	
	}
	
	
	
	
	
}

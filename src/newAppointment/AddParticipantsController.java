package newAppointment;

import calendar.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddParticipantsController {
	
	@FXML private TextField searchText;
	@FXML private ListView<User> searchList;
	@FXML private ListView<User> addList;
	@FXML private Button addButton;
	@FXML private Button cancelButton;
	
	@FXML
	private void initialize() {
		//TODO get users from server, add to searchList
	}
	
	@FXML
	private void addUserButtonPressed() {
		
	}
	
	@FXML
	private void removeUserButtonPressed() {
		
	}
	
	@FXML
	private void addButtonPressed() {
		
	}
	
	@FXML
	private void cancelButtonPressed() {
		// get a handle to the stage
	    Stage stage = (Stage) cancelButton.getScene().getWindow();
	    stage.close();
	}
	
	@FXML
	private void textChanged() {
		
	}

}

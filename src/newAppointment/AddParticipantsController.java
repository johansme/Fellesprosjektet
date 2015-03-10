package newAppointment;

import java.util.ArrayList;
import java.util.List;
import calendar.Appointment;
import calendar.Calendar;
import calendar.User;
import calendarGUI.ControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import login.SceneHandler;

public class AddParticipantsController implements ControllerInterface {
	
	@FXML private TextField searchText;
	@FXML private ListView<User> searchList;
	@FXML private ListView<User> addList;
	@FXML private Button addButton;
	@FXML private Button cancelButton;
	
	private Calendar calendar;
	
	@FXML
	private void initialize() {
		List<User> users = new ArrayList<User>();
		//TODO get users from server, add to searchList
		for (User user : users) {
			//TODO
			HBox line = new HBox();
			Label groupLabel = new Label();
			
			groupLabel.wrapTextProperty().set(true);
			
			groupLabel.setText(user.toString());
			line.getChildren().add(groupLabel);
			
			searchList.getItems().add(user);
		}
	}
	
	// when the right arrow button is pressed
	@FXML
	private void addUserButtonPressed() {
		
	}
	
	@FXML
	private void removeUserButtonPressed() {
		
	}
	
	@FXML
	private void addButtonPressed() {
		for (User user : addList.getItems()) {
			//TODO Add users to participants-list in NewAppointmentController
		}
		SceneHandler sh = new SceneHandler();
		sh.popUpMessage("/messages/Info.fxml", 300, 150, "The participants have been added", this);
		// get a handle to the stage
	    Stage stage = (Stage) cancelButton.getScene().getWindow();
	    stage.close();
	}
	
	@FXML
	private void cancelButtonPressed() {
		// get a handle to the stage
	    Stage stage = (Stage) cancelButton.getScene().getWindow();
	    stage.close();
	}
	
	@FXML
	private void searchTextChanged() {
		
	}

	@Override
	public void setData(Calendar calendar) {
		if (calendar != null) {
			this.calendar = calendar;
		}
	}

	@Override
	public Calendar getData() {
		return calendar;
	}

	@Override
	public void setData(Calendar c, Appointment a) {
		this.calendar = c;
	}

	@Override
	public void setFeedback() {
		// TODO Auto-generated method stub
		
	}

}

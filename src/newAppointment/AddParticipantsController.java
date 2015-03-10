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
	@FXML private ListView<HBox> searchList;
	@FXML private ListView<HBox> addList;
	@FXML private Button addButton;
	@FXML private Button cancelButton;
	
	private List<User> searchListItems = new ArrayList<User>();
	private List<User> addListItems = new ArrayList<User>();
	
	private Calendar calendar;
	private NewAppointmentController newAppointmentController;
	
	@FXML
	private void initialize() {
		List<User> users = new ArrayList<User>();
		//TODO get users from server, add to searchList
		for (User user : users) {
			HBox line = new HBox();
			Label userLabel = new Label();
			
			userLabel.wrapTextProperty().set(true);
			
			userLabel.setText(user.toString());
			line.getChildren().add(userLabel);
			
			searchList.getItems().add(line);
			searchListItems.add(user);
		}
	}
	
	// when the right arrow button is pressed
	@FXML
	private void addUserButtonPressed() {
		int i = searchList.getFocusModel().getFocusedIndex();
		User addedUser = searchListItems.remove(i);
		HBox userLabel = searchList.getItems().remove(i);
		addListItems.add(addedUser);
		addList.getItems().add(userLabel);
	}
	
	// When the left arrow button is pressed
	@FXML
	private void removeUserButtonPressed() {
		int i = addList.getFocusModel().getFocusedIndex();
		User removedUser = addListItems.remove(i);
		HBox userLabel = addList.getItems().remove(i);
		searchListItems.add(removedUser);
		searchList.getItems().add(userLabel);
	}
	
	@FXML
	private void addButtonPressed() {
		for (User user : addListItems) {
			newAppointmentController.addParticipant(user);
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
		String search = searchText.getText();
		int i = searchListItems.size()/2;
		//TODO decide order for name sorting
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

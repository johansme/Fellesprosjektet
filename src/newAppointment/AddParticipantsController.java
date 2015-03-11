package newAppointment;

import java.util.ArrayList;
import java.util.List;

import calendar.Appointment;
import calendar.Calendar;
import calendar.Participant;
import calendarGUI.ControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AddParticipantsController implements ControllerInterface {
	
	@FXML private TextField searchText;
	@FXML private ListView<HBox> searchList;
	@FXML private ListView<HBox> addList;
	@FXML private Button addButton;
	@FXML private Button cancelButton;
	
	private List<Participant> searchListItems = new ArrayList<Participant>();
	private List<Participant> addListItems = new ArrayList<Participant>();
	
	private Calendar calendar;
	private NewAppointmentController newAppointmentController;
	
	@FXML
	private void initialize() {
		List<Participant> participants = new ArrayList<Participant>();
		//TODO get participants from server, add to searchList
		for (Participant participant : participants) {
			HBox line = new HBox();
			Label userLabel = new Label();
			
			userLabel.wrapTextProperty().set(true);
			
			userLabel.setText(participant.toString());
			line.getChildren().add(userLabel);
			
			searchList.getItems().add(line);
			searchListItems.add(participant);
		}
	}
	
	// when the right arrow button is pressed
	@FXML
	private void addUserButtonPressed() {
		int i = searchList.getFocusModel().getFocusedIndex();
		Participant addedParticipant = searchListItems.remove(i);
		HBox userLabel = searchList.getItems().remove(i);
		addListItems.add(addedParticipant);
		addList.getItems().add(userLabel);
	}
	
	// When the left arrow button is pressed
	@FXML
	private void removeUserButtonPressed() {
		int i = addList.getFocusModel().getFocusedIndex();
		Participant removedParticipant = addListItems.remove(i);
		HBox userLabel = addList.getItems().remove(i);
		searchListItems.add(removedParticipant);
		searchList.getItems().add(userLabel);
	}
	
	@FXML
	private void addButtonPressed() {
		for (Participant participant : addListItems) {
			newAppointmentController.addParticipant(participant);
		}
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
		int index = 0;
		for (int i = 0; i < searchListItems.size(); i++) {
			String s = searchListItems.get(i).toString();
			s = s.replaceAll(",", "");
			s = s.replaceAll(";", "");
			String[] sa = s.split(" ");
			search = search.replaceAll(",", "");
			String[] searchArray = search.split(" ");
			if (sa[0].startsWith(searchArray[0])) {
				if (sa[0].equalsIgnoreCase(searchArray[0]) && searchArray.length > 1) {
					if (sa[1].startsWith(searchArray[1])) {
						index = i;
						if (sa[1].equalsIgnoreCase(searchArray[1])) {
							break;
						}
					}
				} else {
					index = i;
				}
			}
		}
		searchList.getFocusModel().focus(index);
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
		
	}
	
	public void setNewAppointmentController(NewAppointmentController controller) {
		if (controller != null) {
			this.newAppointmentController = controller;
		}
	}
	
	@FXML
	private void keyPressed(KeyEvent e) {
		if (e.getCode()==KeyCode.LEFT) {
			removeUserButtonPressed();
		}
		else if (e.getCode()==KeyCode.RIGHT) {
			addUserButtonPressed();
		}
		else if (e.getCode()==KeyCode.ENTER) {
			addButtonPressed();
		}
		else {
			return;
		}
	}

}

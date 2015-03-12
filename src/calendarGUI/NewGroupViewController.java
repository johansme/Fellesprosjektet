package calendarGUI;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import login.SceneHandler;
import calendar.Participant;

public class NewGroupViewController implements ParticipantController {

	@FXML private TextField nameField;
	@FXML private Label creatorLabel;
	@FXML private ListView<String> memberListView;
	@FXML private Button addMembersButton;
	@FXML private Button removeMemberButton;
	@FXML private Button saveButton;
	@FXML private Button cancelButton;

	private List<Participant> memberList = new ArrayList<Participant>();
	private SceneHandler sceneHandler = new SceneHandler();
	private GroupViewController groupViewController;

	@FXML
	private void initialize() {

	}

	private void setView() {
		creatorLabel.setText(groupViewController.getData().getLoggedInUser().toString());
	}

	@FXML
	private void addMembers() {
		sceneHandler.popUpParticipants("/newAppointment/AddParticipants.fxml", 500, 300, getGroupViewController().getData(), this);
	}

	@FXML
	private void removeMember() {
		int index = memberListView.getSelectionModel().getSelectedIndex();
		if(index >= 0 && index < memberListView.getItems().size()){
			memberListView.getItems().remove(index);
			memberList.remove(index);
		} else {
			sceneHandler.popUpMessage("/messages/Error.fxml", 300, 150, "No participant selected.", groupViewController);
		}
	}

	@FXML
	private void saveButtonPressed() {
		if (isValidName()) {
			
		} else {
			sceneHandler.popUpMessage("/messages/Error.fxml", 300, 150, "Invalid group name", getGroupViewController());
		}
	}

	private boolean isValidName() {
		String s = nameField.getText();
		if (s.length() > 50) {
			return false;
		}
		return s.matches("[ a-zA-Z0-9_]+") && ! s.startsWith(" ");
	}

	@FXML
	private void cancelButtonPressed() {
		// get a handle to the stage
		Stage stage = (Stage)cancelButton.getScene().getWindow();
		stage.close();
	}

	@Override
	public void addParticipant(Participant participant) {
		if (participant != null && ! memberList.contains(participant)) {
			memberList.add(participant);
			memberListView.getItems().add(participant.toString());
		}
	}

	public GroupViewController getGroupViewController() {
		return groupViewController;
	}

	public void setGroupViewController(GroupViewController controller) {
		if (controller != null) {
			this.groupViewController = controller;
			setView();
		}
	}

}

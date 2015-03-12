package calendarGUI;

import java.util.ArrayList;
import java.util.List;

import calendar.Appointment;
import calendar.Calendar;
import calendar.Participant;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import login.SceneHandler;

public class GroupViewController implements ControllerInterface, ParticipantController {
	
	@FXML private TabPane tabs;
	
	@FXML private Tab newGroup;
	@FXML private Pane newGroupPane;
	@FXML private NewGroupViewController newGroupPaneController;
	
	@FXML private Tab groups;
	@FXML private MenuButton groupChoice;
	@FXML private Label nameLabel;
	@FXML private TextField nameField;
	@FXML private Label creatorLabel;
	@FXML private ListView<String> memberListView;
	@FXML private Button addMembersButton;
	@FXML private Button removeMemberButton;
	@FXML private Button saveButton;
	@FXML private Button cancelButton;
	
	private Calendar calendar;
	private List<Participant> memberList = new ArrayList<Participant>();
	
	private SceneHandler sceneHandler = new SceneHandler();
	
	@FXML
	private void initialize() {
		
	}
	
	@FXML
	private void addMembers() {
		sceneHandler.popUpParticipants("/newAppointment/AddParticipants.fxml", 500, 300, calendar, this);
	}

	@FXML
	private void removeMember() {
		int index = memberListView.getSelectionModel().getSelectedIndex();
		if(index >= 0 && index < memberListView.getItems().size()){
			memberListView.getItems().remove(index);
			memberList.remove(index);
		} else {
			sceneHandler.popUpMessage("/messages/Error.fxml", 300, 150, "No participant selected.", this);
		}
	}

	@FXML
	private void saveButtonPressed() {

	}

	@FXML
	private void cancelButtonPressed() {
		// get a handle to the stage
		Stage stage = (Stage)cancelButton.getScene().getWindow();
		stage.close();
	}

	@Override
	public void setData(Calendar calendar) {
		if (calendar != null) {
			this.calendar = calendar;
			setController();
		}
	}

	@Override
	public Calendar getData() {
		return calendar;
	}

	@Override
	public void setData(Calendar c, Appointment a) {
		if (c != null) {
			calendar = c;
			setController();
		}
	}

	@Override
	public void setFeedback() {
		
	}

	@Override
	public void addParticipant(Participant participant) {
		if (participant != null && ! memberList.contains(participant)) {
			memberList.add(participant);
			memberListView.getItems().add(participant.toString());
		}
	}
	
	@FXML
	private void setController() {
		newGroupPaneController.setGroupViewController(this);
	}

}

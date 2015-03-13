package calendarGUI;

import java.util.ArrayList;
import java.util.List;

import calendar.Appointment;
import calendar.Calendar;
import calendar.Group;
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

	private List<Group> groupList = new ArrayList<Group>();
	private Group group;
	private Calendar calendar;
	private List<Participant> memberList = new ArrayList<Participant>();
	private List<Participant> addedMembers = new ArrayList<Participant>();
	private List<Participant> removedMembers = new ArrayList<Participant>();

	private SceneHandler sceneHandler = new SceneHandler();

	@FXML
	private void initialize() {

	}

	@FXML
	private void addMembers() {
		sceneHandler.popUpParticipants("/newAppointment/AddParticipants.fxml", 500, 300, calendar, this);
		saveButton.setDisable(false);
	}

	@FXML
	private void removeMember() {
		int index = memberListView.getSelectionModel().getSelectedIndex();
		if(index >= 0 && index < memberListView.getItems().size()){
			memberListView.getItems().remove(index);
			Participant removed = memberList.remove(index);
			if (addedMembers.contains(removed)) {
				addedMembers.remove(removed);
			} else {
				removedMembers.add(removed);
			}
		} else {
			sceneHandler.popUpMessage("/messages/Error.fxml", 300, 150, "No participant selected.", this);
		}
		saveButton.setDisable(false);
	}
	
	@FXML
	private void nameChanged() {
		saveButton.setDisable(false);
	}

	@FXML
	private void saveButtonPressed() {
		if (isValidName()) {
			group.setName(nameField.getText());
			for (Participant member : addedMembers) {
				group.addMember(member);
				//TODO send to server
			}
			for (Participant member : removedMembers) {
				group.removeMember(member);
				//TODO send to server
			}
			saveButton.setDisable(true);
		} else {
			sceneHandler.popUpMessage("/messages/Error.fxml", 300, 150, "Invalid group name", this);
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

	private void setView() {
		nameLabel.setText(group.getName());
		nameField.setText(group.getName());
		creatorLabel.setText(group.getAdmin().toString());
		setMemberList();
		if (calendar.getLoggedInUser().isAdmin() || group.getAdmin().equals(calendar.getLoggedInUser())) {
			nameLabel.setVisible(false);
			nameField.setVisible(true);
			nameField.setDisable(false);
			addMembersButton.setDisable(false);
			removeMemberButton.setDisable(false);
		} else {
			nameLabel.setVisible(true);
			nameField.setVisible(false);
			nameField.setDisable(true);
			addMembersButton.setDisable(true);
			removeMemberButton.setDisable(true);
		}
	}

	private void setMemberList() {
		memberList.clear();
		memberListView.getItems().clear();
		if (group == null || group.getMembers() == null) { return; }
		for (Participant member : group.getMembers()) {
			memberList.add(member);
			memberListView.getItems().add(member.toString());
		}
	}

	@Override
	public void setData(Calendar calendar) {
		if (calendar != null) {
			this.calendar = calendar;
			setGroups(calendar.getLoggedInUser().getGroups());
			setController();
			if (groupList.size() > 0) {
				setGroup(groupList.get(0));
				setView();
			} else {
				tabs.getSelectionModel().select(1);
			}
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
			setGroups(calendar.getLoggedInUser().getGroups());
			setController();
			if (groupList.size() > 0) {
				setGroup(groupList.get(0));
				setView();
			} else {
				tabs.getSelectionModel().select(1);
			}
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
			if (! removedMembers.contains(participant)) {
				addedMembers.add(participant);
			} else {
				removedMembers.remove(participant);
			}
		}
	}

	@FXML
	private void setController() {
		newGroupPaneController.setGroupViewController(this);
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		if (group != null) {
			this.group = group;
			groupChoice.setText(this.group.getName());
			setView();
		}
	}

	public void setGroups(List<Group> groups) {
		if (groups != null) {
			this.groupList = groups;
			setView();
		}
	}

	public TabPane getTabs() {
		return tabs;
	}

}

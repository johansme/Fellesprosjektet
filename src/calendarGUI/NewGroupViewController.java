package calendarGUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import api.API;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import login.SceneHandler;
import calendar.Group;
import calendar.Participant;
import calendar.User;

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
	
	private Group group;

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
			if (memberList.size() < 1) {
				sceneHandler.popUpMessage("/messages/Error.fxml", 300, 150, "Please add group members", getGroupViewController());
				return;
			}
			group = new Group(0, memberList, getGroupViewController().getData().getLoggedInUser(), nameField.getText());
			group.setCreatedBy(group.getAdmin().getId());
			groupViewController.setGroup(group);
			JSONObject obj = new JSONObject();
			obj.put("command", "create");
			obj.put("group", group);
			JSONObject res;
			try {
				res = API.call("/group", obj, getGroupViewController().getData().getSession());
				group.setId(res.getInt("gid"));	
			} catch (IOException e) {
			}
			for (Participant member : memberList) {
				if (member instanceof Group) {
					((Group) member).setParent(group.getId());
					//TODO update server
				} else if (member instanceof User) {
					((User) member).addGroup(group);
					//TODO update server
				}
			}
			//TODO send group to server
			
			nameField.clear();
			memberList.clear();
			memberListView.getItems().clear();
			groupViewController.getTabs().getSelectionModel().select(0);
		} else {
			sceneHandler.popUpMessage("/messages/Error.fxml", 300, 150, "Invalid group name", getGroupViewController());
		}
	}

	private boolean isValidName() {
		String s = nameField.getText();
		if (s.length() > 50) {
			return false;
		}
		return s.matches("[ a-zA-Z0-9]+") && ! s.startsWith(" ");
	}

	@FXML
	private void cancelButtonPressed() {
		// get a handle to the stage
		Stage stage = (Stage)cancelButton.getScene().getWindow();
		stage.close();
	}

	@Override
	public void addParticipant(Participant participant) {
		boolean eql = false;
		for (Participant p : memberList) {
			if (p.toString().equals(participant.toString())) {
				eql = true;
			}
		}
		if (participant != null && ! eql) {
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
	
	@FXML
	private void keyPressed(KeyEvent e) {
		if (e.getCode()==KeyCode.ENTER) {
			saveButtonPressed();
		} else if (e.getCode()==KeyCode.ESCAPE) {
			cancelButtonPressed();
		} else {
			return;
		}
	}

	@Override
	public List<Participant> getParticipants() {
		return memberList;
	}

}

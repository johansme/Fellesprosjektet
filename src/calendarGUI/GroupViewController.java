package calendarGUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import api.API;
import calendar.Appointment;
import calendar.Calendar;
import calendar.Group;
import calendar.Participant;
import calendar.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
	@FXML private Button deleteButton;
	@FXML private Button cancelButton;

	private List<Group> groupList = new ArrayList<Group>();
	private MenuItem menuItem;
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
		sceneHandler.popUpParticipants("/newAppointment/AddParticipants.fxml", 490, 290, calendar, this);
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
			sceneHandler.popUpMessage("/messages/Error.fxml", 290, 140, "No member is selected.", this);
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
			if (groupList.size() + addedMembers.size() - removedMembers.size() <= 0) {
				sceneHandler.popUpMessage("/messages/Error.fxml", 290, 140, "The group has to have members.", this);
				return;
			}
			JSONObject obj;
			group.setName(nameField.getText());
			for (Participant member : addedMembers) {
				obj = new JSONObject();
				group.addMember(member);
				if (member instanceof User) {
					((User) member).addGroup(group);
					obj.put("command", "add_user");
					obj.put("gid", group.getId());
					obj.put("uid", ((User) member).getId());
				} else if (member instanceof Group) {
					((Group) member).setParent(group.getId());
					obj.put("command", "modify");
					obj.put("group", ((Group) member).toJSON());
				}
				try {
					API.call("/group", obj, calendar.getSession());
				} catch (IOException e) {
				}
			}
			for (Participant member : removedMembers) {
				obj = new JSONObject();
				group.removeMember(member);
				if (member instanceof User) {
					((User) member).removeGroup(group);
					obj.put("command", "remove_user");
					obj.put("gid", group.getId());
					obj.put("uid", ((User) member).getId());
				} else if (member instanceof Group) {
					((Group) member).setParent(0);
					obj.put("command", "modify");
					obj.put("group", ((Group) member).toJSON());
				}
				try {
					API.call("/group", obj, calendar.getSession());
				} catch (IOException e) {
				}
			}
			obj = new JSONObject();
			obj.put("command", "modify");
			obj.put("group", group.toJSON());
			try {
				API.call("/group", obj, calendar.getSession());
			} catch (IOException e) {
			}
			saveButton.setDisable(true);
			calendar.refresh();
		} else {
			sceneHandler.popUpMessage("/messages/Error.fxml", 290, 140, "Invalid group name", this);
		}
	}

	private boolean isValidName() {
		String s = nameField.getText();
		if (s.length() > 50) {
			return false;
		}
		return (s.matches("[ a-zA-Z0-9]+") && ! s.startsWith(" "));
	}

	@FXML
	private void deleteGroup() {
		sceneHandler.popUpMessage("/messages/Confirm.fxml", 290, 140, "Are you sure you want to delete?", this);
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
		saveButton.setDisable(true);
		if (calendar.getLoggedInUser().isAdmin() || group.getAdmin().toString().equals(calendar.getLoggedInUser().toString())) {
			nameLabel.setVisible(false);
			nameField.setVisible(true);
			nameField.setDisable(false);
			addMembersButton.setDisable(false);
			removeMemberButton.setDisable(false);
			deleteButton.setDisable(false);
		} else {
			nameLabel.setVisible(true);
			nameField.setVisible(false);
			nameField.setDisable(true);
			addMembersButton.setDisable(true);
			removeMemberButton.setDisable(true);
			deleteButton.setDisable(true);
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
				setGroupMenu();
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
				setGroupMenu();
				setView();
			} else {
				tabs.getSelectionModel().select(1);
			}
		}
	}

	@Override
	public void setFeedback() {
		delete();
	}

	private void delete() {
		int gid = group.getId();
		JSONObject obj = new JSONObject();
		obj.put("command", "delete");
		obj.put("gid", gid);
		try {
			API.call("/group", obj, calendar.getSession());
			calendar.getLoggedInUser().removeGroup(group);
			groupChoice.getItems().remove(menuItem);
			if (groupChoice.getItems().size() > 0) {
				groupChoice.getItems().get(0).fire();
			} else {
				nameField.clear();
				creatorLabel.setText("");
				nameLabel.setText("");
				memberList.clear();
				memberListView.getItems().clear();

				nameLabel.setVisible(true);
				nameField.setVisible(false);
				nameField.setDisable(true);
				addMembersButton.setDisable(true);
				removeMemberButton.setDisable(true);
				deleteButton.setDisable(true);

				tabs.getSelectionModel().select(1);
			}
		} catch (IOException e) {
			sceneHandler.popUpMessage("/messages/Error.fxml", 290, 140, "Something went wrong. Please try again.", this);
			return;
		}
	}

	private void setGroupMenu() {
		for (Group group : groupList) {
			MenuItem mi = new MenuItem(group.getName());
			mi.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent t) {
					setGroup(group);
					menuItem = mi;
				}
			});
			groupChoice.getItems().add(mi);
		}
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
			if (! removedMembers.contains(participant)) {
				addedMembers.add(participant);
			} else {
				removedMembers.remove(participant);
			}
		}
	}

	public void addGroup(Group group) {
		groupList.add(group);
		MenuItem mi = new MenuItem(group.getName());
		mi.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				setGroup(group);
				menuItem = mi;
			}
		});
		groupChoice.getItems().add(mi);
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
		}
	}

	public TabPane getTabs() {
		return tabs;
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

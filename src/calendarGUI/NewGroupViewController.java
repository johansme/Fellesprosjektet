package calendarGUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import calendar.Participant;

public class NewGroupViewController implements ParticipantController {

	@FXML private TextField nameField;
	@FXML private Label creatorLabel;
	@FXML private ListView<Participant> memberList;
	@FXML private Button addMembersButton;
	@FXML private Button removeMemberButton;
	@FXML private Button saveButton;
	@FXML private Button cancelButton;
	
	@FXML
	private void initialize() {
		
	}
	
	@FXML
	private void addMembers() {
		
	}
	
	@FXML
	private void removeMember() {
		
	}
	
	@FXML
	private void saveButtonPressed() {
		
	}
	
	@FXML
	private void cancelButtonPressed() {
		
	}
	
	@Override
	public void addParticipant(Participant participant) {
		// TODO Auto-generated method stub
		
	}

}

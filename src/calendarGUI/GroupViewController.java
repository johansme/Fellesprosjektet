package calendarGUI;

import calendar.Appointment;
import calendar.Calendar;
import calendar.Participant;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import login.SceneHandler;

public class GroupViewController extends Application implements ControllerInterface, ParticipantController {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = (Parent)FXMLLoader.load(getClass().getResource("/calendarGUI/GroupView.fxml"));
			Scene scene = new Scene(root,600,350);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@FXML private Tab newGroup;
	@FXML private Pane newGroupPane;
	@FXML private NewGroupViewController newGroupController;
	
	@FXML private Tab groups;
	@FXML private MenuButton groupChoice;
	@FXML private Label nameLabel;
	@FXML private TextField nameField;
	@FXML private Label creatorLabel;
	@FXML private ListView<Participant> memberList;
	@FXML private Button addMembersButton;
	@FXML private Button removeMemberButton;
	@FXML private Button saveButton;
	@FXML private Button cancelButton;
	
	private Calendar calendar;
	
	private SceneHandler sceneHandler = new SceneHandler();
	
	@FXML
	private void initialize() {
		
	}
	
	@FXML
	private void addMembers() {
		sceneHandler.popUpParticipants("/newAppointment/AddParticipants.fxml", 500, 300, getData(), this);
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
		if (c != null) {
			calendar = c;
		}
	}

	@Override
	public void setFeedback() {
		
	}

	@Override
	public void addParticipant(Participant participant) {
		// TODO Auto-generated method stub
		
	}

}

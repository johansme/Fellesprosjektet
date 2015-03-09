package calendarGUI;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import calendar.Appointment;
import calendar.Calendar;
import calendar.User;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import login.SceneHandler;

public class AppointmentViewController extends Application implements ControllerInterface{

	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("AppointmentView.fxml/"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private SceneHandler sceneHandler;
	private Calendar calendar;
	private Appointment appointment;
	//TODO SCALING
	@FXML
	private Label purpose;
	
	@FXML
	private Label startDate;
	
	@FXML
	private Label endDate;
	
	@FXML
	private Label from;
	
	@FXML
	private Label until;
	
	@FXML
	private Label room;
	
	@FXML
	private RadioButton yes;
	
	@FXML
	private RadioButton no;
	
	@FXML
	private Button confirmButton;
	
	@FXML
	private ListView<HBox> participants;
	
	@FXML
	private Button close;
	
	@FXML
	private Button edit;
	
	@FXML
	private ToggleGroup toggleAnswer;
	
	@FXML
	private HBox attendBox;
	
	@FXML
	private HBox participantsBox;
	
	@FXML
	private VBox screen;
	
	@FXML
	public void keyPressed(KeyEvent e) {
		if (e.getCode()==KeyCode.ESCAPE) {
			closeAction();
		}
		else if (e.getCode()==KeyCode.ENTER) {
			editAction();
		}
		else if (e.getCode()==KeyCode.DELETE) {
			deleteAction();
		}
	}
	
	@FXML
	public void confirmAnswer() {
		if (confirmButton.getText()=="Change") {
			appointment.setAttending("notAnswered");
		}
		else {
			User u = new User();
			u.setUsername("TestUser");

			if (toggleAnswer.getSelectedToggle()==yes) {
				appointment.setAttending("Y");
				if (!appointment.getUsers().isEmpty()) {
					appointment.addUser(u);
					appointment.setUserAttending(u, true);
				}
			}
			else if (toggleAnswer.getSelectedToggle()==no) {
				appointment.setAttending("N");
				if (appointment.getUsers().contains(u)) {
					appointment.setUserAttending(u, false);
				}
			}
		}
		setView(appointment);
	}
	
	@FXML
	public void closeAction() {
	    // get a handle to the stage
	    Stage stage = (Stage) close.getScene().getWindow();
	    // do what you have to do
	    stage.close();

	}
	
	@FXML
	public void editAction() {
		sceneHandler = new SceneHandler();
		closeAction();
		sceneHandler.popUpScene("/newAppointment/NewAppointment.fxml", 600, 480, getData(), appointment);
		
	}
	
	@FXML
	private Button delete;
	
	@FXML
	public void deleteAction() {
		sceneHandler = new SceneHandler();
		sceneHandler.popUpMessage("/messages/Confirm.fxml", 300, 150, "Are you sure you want to delete?", this);
	}
	
	public void setView(Appointment a) {
		appointment=a;
		appointment.setOpened(true);
		purpose.setText(appointment.getDescription());
		startDate.setText(dateToString(appointment.getStartDate()));
		endDate.setText(dateToString(appointment.getEndDate()));
		from.setText(appointment.getStartStartTime().toString());
		until.setText(appointment.getEndEndTime().toString());
		room.setText(appointment.getLocation());
		edit.setDisable(!a.getAdmin());
		if (a.getAttending()=="Y" || a.getAttending()=="N") {
			if (a.getAttending()=="Y") {
				toggleAnswer.selectToggle(yes);
			}
			else {
				toggleAnswer.selectToggle(no);
			}
			yes.setDisable(true);
			no.setDisable(true);
			confirmButton.setText("Change");
		}
		else if (a.getAttending()=="None") {
			attendBox.getChildren().clear();
		}
		else if (a.getAttending()=="notAnswered"){
			yes.setDisable(false);
			no.setDisable(false);
			confirmButton.setText("Confirm");
		}
		else {
			toggleAnswer.selectToggle(yes);

		}
		if (a.getUsers().isEmpty()) {
			participantsBox.getChildren().clear();
		}
		else {
			participants.getItems().clear();
			List<User> partpts = appointment.getUsers();
			for (User p : partpts) {
				//TODO
				HBox line = new HBox();
				line.setPrefWidth(participants.getPrefWidth()-35);
				Label userLabel = new Label();
				userLabel.setText(p.getUsername());
				CheckBox checkBox = new CheckBox();
				checkBox.setSelected(appointment.getUserAttending(p));
				userLabel.setPrefWidth(line.getPrefWidth()-checkBox.getWidth());
				line.getChildren().addAll(userLabel, checkBox);
				attendMap.put(checkBox, p);
				participants.getItems().add(line);
				checkBox.setOnAction(new EventHandler<ActionEvent>() {
					@Override public void handle(ActionEvent e) {
						if (checkBox.isSelected()) {
							appointment.setUserAttending(attendMap.get(checkBox), true);
						}
						else {
							appointment.setUserAttending(attendMap.get(checkBox), false);
						}
					}
				});

				if (!appointment.getAdmin()) {
					checkBox.setDisable(true);
				}
				
			}

		}
	}
	
	private HashMap<CheckBox, User> attendMap = new HashMap<CheckBox, User>();
	
	private String dateToString(LocalDate date) {
		int day = date.getDayOfMonth();
		int month = date.getMonthValue();
		if (month==1) {
			return day+". January";
		}
		if (month==2) {
			return day+". February";
		}
		if (month==3) {
			return day+". March";
		}
		if (month==4) {
			return day+". April";
		}
		if (month==5) {
			return day+". May";
		}
		if (month==6) {
			return day+". June";
		}
		if (month==7) {
			return day+". July";
		}
		if (month==8) {
			return day+". August";
		}
		if (month==9) {
			return day+". September";
		}
		if (month==10) {
			return day+". October";
		}
		if (month==11) {
			return day+". November";
		}
		if (month==12) {
			return day+". December";
		}
		else {
			return null;
		}
	}
		
	

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void setData(Calendar c, Appointment a) {
		calendar=c;
		setView(a);
		
	}
	
	@Override
	public void setData(Calendar c) {
		
	}

	@Override
	public Calendar getData() {
		// TODO Auto-generated method stub
		return this.calendar;
	}

	@Override
	public void setFeedback() {
		appointment.delete();
	    // get a handle to the stage
	    Stage stage = (Stage) close.getScene().getWindow();
	    // do what you have to do
	    stage.close();
		
	}
}

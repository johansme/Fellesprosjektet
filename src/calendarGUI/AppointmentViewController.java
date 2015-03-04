package calendarGUI;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import calendar.Appointment;
import calendar.Calendar;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
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
	private ListView<String> participants;
	
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
	public void confirmAnswer() {
		if (confirmButton.getText()=="Change") {
			appointment.setAttending("None");
		}
		else {
			if (toggleAnswer.getSelectedToggle()==yes) {
				appointment.setAttending("Y");
			}
			else if (toggleAnswer.getSelectedToggle()==no) {
				appointment.setAttending("N");
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
		sceneHandler.popUpScene("/newAppointment/NewAppointment.fxml", 600, 480);
		
	}
	
	@FXML
	private Button delete;
	
	@FXML
	public void deleteAction() {
		
	}
	
	public void setView(Appointment a) {
		appointment=a;
		purpose.setText(appointment.getDescription());
		startDate.setText(dateToString(appointment.getStartDate()));
		endDate.setText(dateToString(appointment.getEndDate()));
		from.setText(appointment.getStartTime().toString());
		until.setText(appointment.getEndTime().toString());
		room.setText(appointment.getLocation());
		edit.setDisable(!a.getAdmin());
		if (a.getAttending()=="Y" || a.getAttending()=="N") {
			yes.setDisable(true);
			no.setDisable(true);
			confirmButton.setText("Change");
		}
		else if (a.getAttending()=="None") {
			attendBox.getChildren().removeAll();
		}
		else {
			toggleAnswer.selectToggle(yes);
		}
		if (a.getParticipants().isEmpty()) {
			participantsBox.getChildren().removeAll();
		}
		else {
			List<String> partpts = appointment.getParticipants();
			for (String p : partpts) {
				participants.getItems().add(p);
			}

		}
	}
	
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
}

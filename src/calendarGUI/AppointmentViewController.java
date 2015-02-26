package calendarGUI;

import java.time.LocalDate;
import java.util.ArrayList;

import calendar.Appointment;
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
import javafx.stage.Stage;

public class AppointmentViewController extends Application {

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
	private Button back;
	
	@FXML
	private Button edit;
	
	@FXML
	private ToggleGroup toggleAnswer;
	
	@FXML
	public void confirmAnswer() {
		
	}
	
	@FXML
	public void backAction() {
		
	}
	
	@FXML
	public void editAction() {
		
	}
	
	public void setView() {
		purpose.setText(appointment.getDescription());
		startDate.setText(dateToString(appointment.getStartDate()));
		endDate.setText(dateToString(appointment.getEndDate()));
		from.setText(appointment.getStartTime().toString());
		until.setText(appointment.getEndTime().toString());
		room.setText(appointment.getLocation());
		ArrayList<String> partpts = appointment.getParticipants();
		for (String p : partpts) {
			participants.getItems().add(p);
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
}

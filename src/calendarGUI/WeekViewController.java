package calendarGUI;

import java.time.LocalDate;
import java.util.ArrayList;

import calendar.Appointment;
import calendar.Day;
import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import login.SceneHandler;

public class WeekViewController extends Application {

	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("WeekView.fxml/"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	ArrayList<Day> days;
	private SceneHandler sceneHandler = new SceneHandler();

	
	public void setView() {
		setWeek(days.get(0).getWeekNumber());
		setDate(days);
		setAppointments(days);
	}
	
	@FXML
	private Label weekNum;
	
	private void setWeek(int week) {
		weekNum.setText("Week "+week);
		int prevWeek = week-1;
		int nextWeek = week+1;
		prev.setText("Week "+prevWeek);
		next.setText("Week "+nextWeek);
		if (week==1) {
			prev.setText("Week 52");
		}
		if (week==52) {
			next.setText("Week 1");
		}
	}
	
	@FXML
	private Button prev;
	
	@FXML
	public void prevAction() {
		//init weekview x--
	}
	
	@FXML
	private Button next;
	
	@FXML
	public void nextAction() {
		//init weekview x++
	}
	
	@FXML
	private Button month;
	
	@FXML
	public void monthClicked(Event e) {
		sceneHandler.changeScene("/calendarGUI/MonthView.fxml", e);
	}
	
	@FXML
	private Button newAppointment;
	
	@FXML
	public void newAction() {
		sceneHandler.popUpScene("/newAppointment/NewAppointment.fxml", 600, 480);
	}
	
	@FXML
	private AnchorPane monAppointments;
	@FXML
	private AnchorPane tuesAppointments;
	@FXML
	private AnchorPane wedAppointments;
	@FXML
	private AnchorPane thurAppointments;
	@FXML
	private AnchorPane friAppointments;
	@FXML
	private AnchorPane satAppointments;
	@FXML
	private AnchorPane sunAppointments;
	@FXML
	private Label monDate;
	@FXML
	private Label tuesDate;
	@FXML
	private Label wedDate;
	@FXML
	private Label thurDate;
	@FXML
	private Label friDate;
	@FXML
	private Label satDate;
	@FXML
	private Label sunDate;
	
	public void setDate(ArrayList<Day> dates) {
		monDate.setText(""+dates.get(0).getDay().getDayOfMonth()+". "+monthToString(dates.get(0).getDay().getMonthValue()));
		tuesDate.setText(""+dates.get(1).getDay().getDayOfMonth()+". "+monthToString(dates.get(1).getDay().getMonthValue()));
		wedDate.setText(""+dates.get(2).getDay().getDayOfMonth()+". "+monthToString(dates.get(2).getDay().getMonthValue()));
		thurDate.setText(""+dates.get(3).getDay().getDayOfMonth()+". "+monthToString(dates.get(3).getDay().getMonthValue()));
		friDate.setText(""+dates.get(4).getDay().getDayOfMonth()+". "+monthToString(dates.get(4).getDay().getMonthValue()));
		satDate.setText(""+dates.get(5).getDay().getDayOfMonth()+". "+monthToString(dates.get(5).getDay().getMonthValue()));
		sunDate.setText(""+dates.get(6).getDay().getDayOfMonth()+". "+monthToString(dates.get(6).getDay().getMonthValue()));
	}
	
	private String monthToString(int month) {
		if (month==1) {
			return "January";
		}
		if (month==2) {
			return "February";
		}
		if (month==3) {
			return "March";
		}
		if (month==4) {
			return "April";
		}
		if (month==5) {
			return "May";
		}
		if (month==6) {
			return "June";
		}
		if (month==7) {
			return "July";
		}
		if (month==8) {
			return "August";
		}
		if (month==9) {
			return "September";
		}
		if (month==10) {
			return "October";
		}
		if (month==11) {
			return "November";
		}
		if (month==12) {
			return "December";
		}
		return null;
		
	}
	

	
	public void setAppointments(ArrayList<Day> days) {
		for (Day day : days) {
			for (Appointment a : day.getAppointments()) {
				//TODO lag appointment i GUI
			}
		}
	}
	

	public static void main(String[] args) {
		launch(args);
	}
}



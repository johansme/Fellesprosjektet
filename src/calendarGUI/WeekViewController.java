package calendarGUI;

import java.time.LocalDate;
import java.util.ArrayList;

import calendar.Appointment;
import calendar.Calendar;
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
	
	private Calendar calendar = new Calendar();
	private SceneHandler sceneHandler = new SceneHandler();

	
	@FXML
	private void initialize() {
		setWeek(calendar.getCurrentWeekNumber());
		setYear(calendar.getCurrentDate().getYear());
		setDates(calendar.getCurrentDate());
		
	}
	
	@FXML
	private Label weekNum;
	
	@FXML
	private Label year;
	
	
	private void setWeek(int week) {
		if (week<1) {
			weekNum.setText("Week 52");
			week=52;
		}
		else if (week>52) {
			weekNum.setText("Week 1");
			week=1;
		}
		else {
			weekNum.setText("Week "+week);
		}
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
	
	private void setYear(int y) {
		year.setText(" - "+y);
	}
	
	private void setDates(LocalDate d) {
		int i = (d.getDayOfWeek().getValue()-1);
		LocalDate day = d.minusDays(i);
		monDate.setText(day.getDayOfMonth()+". "+monthToString(day.getMonthValue()));
		day.plusDays(1);
		tuesDate.setText(day.getDayOfMonth()+". "+monthToString(day.getMonthValue()));
		day.plusDays(1);
		wedDate.setText(day.getDayOfMonth()+". "+monthToString(day.getMonthValue()));
		day.plusDays(1);
		thurDate.setText(day.getDayOfMonth()+". "+monthToString(day.getMonthValue()));
		day.plusDays(1);
		friDate.setText(day.getDayOfMonth()+". "+monthToString(day.getMonthValue()));
		day.plusDays(1);
		satDate.setText(day.getDayOfMonth()+". "+monthToString(day.getMonthValue()));
		day.plusDays(1);
		sunDate.setText(day.getDayOfMonth()+". "+monthToString(day.getMonthValue()));
		day.plusDays(1);

	}
	
	@FXML
	private Button prev;
	
	@FXML
	public void prevAction() {
		setWeek(calendar.getCurrentWeekNumber()-1);
		setYear(calendar.getCurrentDate().getYear());
		calendar.changeWeek(false);
	}
	
	@FXML
	private Button next;
	
	@FXML
	public void nextAction() {
		setWeek(calendar.getCurrentWeekNumber()+1);
		setYear(calendar.getCurrentDate().getYear());
		calendar.changeWeek(true);
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



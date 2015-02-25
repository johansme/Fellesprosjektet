package calendarGUI;

import java.time.LocalDate;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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
	
	@FXML
	private Label weekNum;
	
	public void setWeek(int week) {
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
	private Button back;
	
	@FXML
	public void backAction() {
		//init monthview
	}
	
	@FXML
	private Button newAppointment;
	
	@FXML
	public void newAction() {
		//Init new appointment view
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
	
	public void setDate(LocalDate[] dates) {
		monDate.setText(""+dates[0].getDayOfMonth()+". "+monthToString(dates[0].getMonthValue()));
		tuesDate.setText(""+dates[1].getDayOfMonth()+". "+monthToString(dates[1].getMonthValue()));
		wedDate.setText(""+dates[2].getDayOfMonth()+". "+monthToString(dates[2].getMonthValue()));
		thurDate.setText(""+dates[3].getDayOfMonth()+". "+monthToString(dates[3].getMonthValue()));
		friDate.setText(""+dates[4].getDayOfMonth()+". "+monthToString(dates[4].getMonthValue()));
		satDate.setText(""+dates[5].getDayOfMonth()+". "+monthToString(dates[5].getMonthValue()));
		sunDate.setText(""+dates[6].getDayOfMonth()+". "+monthToString(dates[6].getMonthValue()));
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
	

	
//	public void setAppointments(Appointment[] appointments) {
//		if (appointments!=null) {
//			for (Appointment appointment : appointments) {
//				int time = appointment.getTime();
//				String day = appointment.getDay();
//			}
//		}
//	}
	

	public static void main(String[] args) {
		launch(args);
	}
}



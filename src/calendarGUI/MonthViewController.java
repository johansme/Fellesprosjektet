package calendarGUI;

import java.util.ArrayList;
import java.util.List;

import calendar.Calendar;
import calendar.Month;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import login.SceneHandler;

public class MonthViewController {
	
	private Month month;
	
	private SceneHandler sceneHandler = new SceneHandler();
	
	@FXML private Label monthName;
	
	@FXML private Button previous;
	@FXML private Button next;
	
	@FXML private Label week1;
	@FXML private Label week2;
	@FXML private Label week3;
	@FXML private Label week4;
	@FXML private Label week5;
	@FXML private Label week6;
	
	
	
	@FXML private AnchorPane week1Day1;
	@FXML private MonthDayViewController week1Day1Controller;
	
	@FXML private AnchorPane week1Day2;
	@FXML private MonthDayViewController week1Day2Controller;
	
	@FXML private AnchorPane week1Day3;
	@FXML private MonthDayViewController week1Day3Controller;
	
	@FXML private AnchorPane week1Day4;
	@FXML private MonthDayViewController week1Day4Controller;
	
	@FXML private AnchorPane week1Day5;
	@FXML private MonthDayViewController week1Day5Controller;
	
	@FXML private AnchorPane week1Day6;
	@FXML private MonthDayViewController week1Day6Controller;
	
	@FXML private AnchorPane week1Day7;
	@FXML private MonthDayViewController week1Day7Controller;
	
	
	@FXML private AnchorPane week2Day1;
	@FXML private MonthDayViewController week2Day1Controller;
	
	@FXML private AnchorPane week2Day2;
	@FXML private MonthDayViewController week2Day2Controller;
	
	@FXML private AnchorPane week2Day3;
	@FXML private MonthDayViewController week2Day3Controller;
	
	@FXML private AnchorPane week2Day4;
	@FXML private MonthDayViewController week2Day4Controller;
	
	@FXML private AnchorPane week2Day5;
	@FXML private MonthDayViewController week2Day5Controller;
	
	@FXML private AnchorPane week2Day6;
	@FXML private MonthDayViewController week2Day6Controller;
	
	@FXML private AnchorPane week2Day7;
	@FXML private MonthDayViewController week2Day7Controller;
	
	
	@FXML private AnchorPane week3Day1;
	@FXML private MonthDayViewController week3Day1Controller;
	
	@FXML private AnchorPane week3Day2;
	@FXML private MonthDayViewController week3Day2Controller;
	
	@FXML private AnchorPane week3Day3;
	@FXML private MonthDayViewController week3Day3Controller;
	
	@FXML private AnchorPane week3Day4;
	@FXML private MonthDayViewController week3Day4Controller;
	
	@FXML private AnchorPane week3Day5;
	@FXML private MonthDayViewController week3Day5Controller;
	
	@FXML private AnchorPane week3Day6;
	@FXML private MonthDayViewController week3Day6Controller;
	
	@FXML private AnchorPane week3Day7;
	@FXML private MonthDayViewController week3Day7Controller;
	
	
	@FXML private AnchorPane week4Day1;
	@FXML private MonthDayViewController week4Day1Controller;
	
	@FXML private AnchorPane week4Day2;
	@FXML private MonthDayViewController week4Day2Controller;
	
	@FXML private AnchorPane week4Day3;
	@FXML private MonthDayViewController week4Day3Controller;
	
	@FXML private AnchorPane week4Day4;
	@FXML private MonthDayViewController week4Day4Controller;
	
	@FXML private AnchorPane week4Day5;
	@FXML private MonthDayViewController week4Day5Controller;
	
	@FXML private AnchorPane week4Day6;
	@FXML private MonthDayViewController week4Day6Controller;
	
	@FXML private AnchorPane week4Day7;
	@FXML private MonthDayViewController week4Day7Controller;
	
	
	@FXML private AnchorPane week5Day1;
	@FXML private MonthDayViewController week5Day1Controller;
	
	@FXML private AnchorPane week5Day2;
	@FXML private MonthDayViewController week5Day2Controller;
	
	@FXML private AnchorPane week5Day3;
	@FXML private MonthDayViewController week5Day3Controller;
	
	@FXML private AnchorPane week5Day4;
	@FXML private MonthDayViewController week5Day4Controller;
	
	@FXML private AnchorPane week5Day5;
	@FXML private MonthDayViewController week5Day5Controller;
	
	@FXML private AnchorPane week5Day6;
	@FXML private MonthDayViewController week5Day6Controller;
	
	@FXML private AnchorPane week5Day7;
	@FXML private MonthDayViewController week5Day7Controller;
	
	
	@FXML private AnchorPane week6Day1;
	@FXML private MonthDayViewController week6Day1Controller;
	
	@FXML private AnchorPane week6Day2;
	@FXML private MonthDayViewController week6Day2Controller;
	
	@FXML private AnchorPane week6Day3;
	@FXML private MonthDayViewController week6Day3Controller;
	
	@FXML private AnchorPane week6Day4;
	@FXML private MonthDayViewController week6Day4Controller;
	
	@FXML private AnchorPane week6Day5;
	@FXML private MonthDayViewController week6Day5Controller;
	
	@FXML private AnchorPane week6Day6;
	@FXML private MonthDayViewController week6Day6Controller;
	
	@FXML private AnchorPane week6Day7;
	@FXML private MonthDayViewController week6Day7Controller;
	
	private List<MonthDayViewController> weekList1;
	private List<MonthDayViewController> weekList2;
	private List<MonthDayViewController> weekList3;
	private List<MonthDayViewController> weekList4;
	private List<MonthDayViewController> weekList5;
	private List<MonthDayViewController> weekList6;
	
	@FXML
	private void initialize() {
//		month = Calendar.getCurrentMonth();
		weekList1 = new ArrayList<>();
		weekList1.add(week1Day1Controller);
		weekList1.add(week1Day2Controller);
		weekList1.add(week1Day3Controller);
		weekList1.add(week1Day4Controller);
		weekList1.add(week1Day5Controller);
		weekList1.add(week1Day6Controller);
		weekList1.add(week1Day7Controller);
		
		weekList2 = new ArrayList<>();
		weekList2.add(week2Day1Controller);
		weekList2.add(week2Day2Controller);
		weekList2.add(week2Day3Controller);
		weekList2.add(week2Day4Controller);
		weekList2.add(week2Day5Controller);
		weekList2.add(week2Day6Controller);
		weekList2.add(week2Day7Controller);
		
		weekList3 = new ArrayList<>();
		weekList3.add(week3Day1Controller);
		weekList3.add(week3Day2Controller);
		weekList3.add(week3Day3Controller);
		weekList3.add(week3Day4Controller);
		weekList3.add(week3Day5Controller);
		weekList3.add(week3Day6Controller);
		weekList3.add(week3Day7Controller);
		
		weekList4 = new ArrayList<>();
		weekList4.add(week4Day1Controller);
		weekList4.add(week4Day2Controller);
		weekList4.add(week4Day3Controller);
		weekList4.add(week4Day4Controller);
		weekList4.add(week4Day5Controller);
		weekList4.add(week4Day6Controller);
		weekList4.add(week4Day7Controller);
		
		weekList5 = new ArrayList<>();
		weekList5.add(week5Day1Controller);
		weekList5.add(week5Day2Controller);
		weekList5.add(week5Day3Controller);
		weekList5.add(week5Day4Controller);
		weekList5.add(week5Day5Controller);
		weekList5.add(week5Day6Controller);
		weekList5.add(week5Day7Controller);
		
		weekList6 = new ArrayList<>();
		weekList6.add(week6Day1Controller);
		weekList6.add(week6Day2Controller);
		weekList6.add(week6Day3Controller);
		weekList6.add(week6Day4Controller);
		weekList6.add(week6Day5Controller);
		weekList6.add(week6Day6Controller);
		weekList6.add(week6Day7Controller);
		
		monthChanged();
	}
	
	private void monthChanged() {
//		monthName.setText(month.getMonth());
		//TODO Complete month update
	}
	
	@FXML
	private void week1Clicked(Event e) {
		for (MonthDayViewController day : weekList1) {
			day.changeDiscovered();
		}
		sceneHandler.changeScene("/calendarGUI/WeekView.fxml", e);
	}
	
	@FXML
	private void week2Clicked(Event e) {
		for (MonthDayViewController day : weekList2) {
			day.changeDiscovered();
		}
		sceneHandler.changeScene("/calendarGUI/WeekView.fxml", e);
	}
	
	@FXML
	private void week3Clicked(Event e) {
		for (MonthDayViewController day : weekList3) {
			day.changeDiscovered();
		}
		sceneHandler.changeScene("/calendarGUI/WeekView.fxml", e);
	}
	
	@FXML
	private void week4Clicked(Event e) {
		for (MonthDayViewController day : weekList4) {
			day.changeDiscovered();
		}
		sceneHandler.changeScene("/calendarGUI/WeekView.fxml", e);
	}
	
	@FXML
	private void week5Clicked(Event e) {
		for (MonthDayViewController day : weekList5) {
			day.changeDiscovered();
		}
		sceneHandler.changeScene("/calendarGUI/WeekView.fxml", e);
	}
	
	@FXML
	private void week6Clicked(Event e) {
		for (MonthDayViewController day : weekList6) {
			day.changeDiscovered();
		}
		sceneHandler.changeScene("/calendarGUI/WeekView.fxml", e);
	}
	
}

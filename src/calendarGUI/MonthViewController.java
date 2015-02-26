package calendarGUI;

import calendar.Calendar;
import calendar.Month;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class MonthViewController {
	
	private Month month;
	
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
	
	
	@FXML
	private void initialize() {
//		month = Calendar.getCurrentMonth();
		monthChanged();
	}
	
	private void monthChanged() {
//		monthName.setText(month.getMonth());
		//TODO Complete month update
	}
	
	
	
}

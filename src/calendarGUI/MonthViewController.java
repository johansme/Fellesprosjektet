package calendarGUI;

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
	
	
	private void initialize() {
		
	}
	
}

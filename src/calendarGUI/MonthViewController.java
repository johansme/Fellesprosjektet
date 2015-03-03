package calendarGUI;

import java.util.ArrayList;
import java.util.List;

import calendar.Calendar;
import calendar.Day;
import calendar.Month;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import login.SceneHandler;

public class MonthViewController implements ControllerInterface {
	
	private Calendar calendar;
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
	
	
	@FXML private Button newAppointment;
	
	private List<MonthDayViewController> weekList1;
	private List<MonthDayViewController> weekList2;
	private List<MonthDayViewController> weekList3;
	private List<MonthDayViewController> weekList4;
	private List<MonthDayViewController> weekList5;
	private List<MonthDayViewController> weekList6;
	
	@FXML
	private void initialize() {
		weekList1 = new ArrayList<MonthDayViewController>();
		weekList1.add(week1Day1Controller);
		weekList1.add(week1Day2Controller);
		weekList1.add(week1Day3Controller);
		weekList1.add(week1Day4Controller);
		weekList1.add(week1Day5Controller);
		weekList1.add(week1Day6Controller);
		weekList1.add(week1Day7Controller);
		
		weekList2 = new ArrayList<MonthDayViewController>();
		weekList2.add(week2Day1Controller);
		weekList2.add(week2Day2Controller);
		weekList2.add(week2Day3Controller);
		weekList2.add(week2Day4Controller);
		weekList2.add(week2Day5Controller);
		weekList2.add(week2Day6Controller);
		weekList2.add(week2Day7Controller);
		
		weekList3 = new ArrayList<MonthDayViewController>();
		weekList3.add(week3Day1Controller);
		weekList3.add(week3Day2Controller);
		weekList3.add(week3Day3Controller);
		weekList3.add(week3Day4Controller);
		weekList3.add(week3Day5Controller);
		weekList3.add(week3Day6Controller);
		weekList3.add(week3Day7Controller);
		
		weekList4 = new ArrayList<MonthDayViewController>();
		weekList4.add(week4Day1Controller);
		weekList4.add(week4Day2Controller);
		weekList4.add(week4Day3Controller);
		weekList4.add(week4Day4Controller);
		weekList4.add(week4Day5Controller);
		weekList4.add(week4Day6Controller);
		weekList4.add(week4Day7Controller);
		
		weekList5 = new ArrayList<MonthDayViewController>();
		weekList5.add(week5Day1Controller);
		weekList5.add(week5Day2Controller);
		weekList5.add(week5Day3Controller);
		weekList5.add(week5Day4Controller);
		weekList5.add(week5Day5Controller);
		weekList5.add(week5Day6Controller);
		weekList5.add(week5Day7Controller);
		
		weekList6 = new ArrayList<MonthDayViewController>();
		weekList6.add(week6Day1Controller);
		weekList6.add(week6Day2Controller);
		weekList6.add(week6Day3Controller);
	}
	
	private void monthChanged() {
		monthName.setText(month.getMonth() + " " + month.getYear());
		Day[] days = month.getDays();
		// the first weekday of the month as an int from 1 to 7
		int firstDayStarts = days[0].getDay().getDayOfWeek().getValue();
		int dayNo = 0;
		for (int i = 0; i < firstDayStarts-1; i++) {
			weekList1.get(i).setBlank();
		}
		for (int i = firstDayStarts-1; i < weekList1.size(); i++) {
			weekList1.get(i).monthChange(days[dayNo]);
			dayNo++;
		}
		for (int i = 0; i < weekList2.size(); i++) {
			weekList2.get(i).monthChange(days[dayNo]);
			dayNo++;
		}
		for (int i = 0; i < weekList3.size(); i++) {
			weekList3.get(i).monthChange(days[dayNo]);
			dayNo++;
		}
		for (int i = 0; i < weekList4.size(); i++) {
			weekList4.get(i).monthChange(days[dayNo]);
			dayNo++;
		}
		for (int i = 0; i < weekList5.size(); i++) {
			if (dayNo < days.length) {
				weekList5.get(i).monthChange(days[dayNo]);
				dayNo++;				
			} else {
				weekList5.get(i).setBlank();
			}
		}
		for (int i = 0; i < weekList6.size(); i++) {
			if (dayNo < days.length) {
				weekList6.get(i).monthChange(days[dayNo]);
				dayNo++;				
			} else {
				weekList6.get(i).setBlank();
			}
		}
		week1.setText("" + Calendar.getWeekNumber(week1Day7Controller.getDay().getDay()));
		week2.setText("" + Calendar.getWeekNumber(week2Day1Controller.getDay().getDay()));
		week3.setText("" + Calendar.getWeekNumber(week3Day1Controller.getDay().getDay()));
		week4.setText("" + Calendar.getWeekNumber(week4Day1Controller.getDay().getDay()));
		week5.setText("" + Calendar.getWeekNumber(week5Day1Controller.getDay().getDay()));
		if (week6Day1Controller.getDay().getDay().isEqual(week4Day1Controller.getDay().getDay().plusDays(14))) {
			week6.setVisible(true);
			week6.setText("" + Calendar.getWeekNumber(week6Day1Controller.getDay().getDay()));
		} else {
			week6.setVisible(false);
		}
	}
	
	
	// element in 1st week is mouse clicked. Equivalent for the below
	@FXML
	private void week1Clicked(Event e) {
		calendar.setCurrentDate(weekList1.get(7).getDay().getDay());
		for (MonthDayViewController day : weekList1) {
			day.changeDiscovered();
		}
		sceneHandler.changeMonthRelatedScene(e, "/calendarGUI/WeekView.fxml", 800, 600, getData());
	}
	
	@FXML
	private void week2Clicked(Event e) {
		calendar.setCurrentDate(weekList2.get(0).getDay().getDay());
		for (MonthDayViewController day : weekList2) {
			day.changeDiscovered();
		}
		sceneHandler.changeMonthRelatedScene(e, "/calendarGUI/WeekView.fxml", 800, 600, getData());
	}
	
	@FXML
	private void week3Clicked(Event e) {
		calendar.setCurrentDate(weekList3.get(0).getDay().getDay());
		for (MonthDayViewController day : weekList3) {
			day.changeDiscovered();
		}
		sceneHandler.changeMonthRelatedScene(e, "/calendarGUI/WeekView.fxml", 800, 600, getData());
	}
	
	@FXML
	private void week4Clicked(Event e) {
		calendar.setCurrentDate(weekList4.get(0).getDay().getDay());
		for (MonthDayViewController day : weekList4) {
			day.changeDiscovered();
		}
		sceneHandler.changeMonthRelatedScene(e, "/calendarGUI/WeekView.fxml", 800, 600, getData());
	}
	
	@FXML
	private void week5Clicked(Event e) {
		calendar.setCurrentDate(weekList5.get(0).getDay().getDay());
		for (MonthDayViewController day : weekList5) {
			day.changeDiscovered();
		}
		sceneHandler.changeMonthRelatedScene(e, "/calendarGUI/WeekView.fxml", 800, 600, getData());
	}
	
	@FXML
	private void week6Clicked(Event e) {
		int i = weekList1.get(0).getDay().getDay().getDayOfWeek().getValue() - 1;
		i += month.getDays().length;
		if (i/7 <= 5) {
			calendar.setCurrentDate(weekList6.get(0).getDay().getDay());
			for (MonthDayViewController day : weekList6) {
				day.changeDiscovered();
			}
			sceneHandler.changeMonthRelatedScene(e, "/calendarGUI/WeekView.fxml", 800, 600, getData());			
		}
	}
	
	// when "Previous"-button is pressed
	@FXML
	private void previousClicked(Event e) {
		try {
			month = calendar.getPreviousMonth();
		} catch (IllegalStateException isl) {
			//TODO create pop-up with out of bounds-info
		}
		monthChanged();
	}
	
	// when "Next"-button is pressed
	@FXML
	private void nextClicked(Event e) {
		month = calendar.getNextMonth();
		monthChanged();
	}
	
	// when "New appointment"-button is pressed
	@FXML
	private void newAppointmentAction() {
		sceneHandler.popUpScene("/newAppointment/NewAppointment.fxml", 600, 480);
		sceneHandler.popUpConfirmation("/messages/confirm.fxml", 300, 150, "Du har no trykket pa ny avtale!:D");
	}

	@Override
	public void setData(Calendar calendar) {
		if (calendar != null) {
			this.calendar = calendar;
		} else {
			this.calendar = new Calendar();
		}
		month = this.calendar.getCurrentMonth();
		monthChanged();
	}

	@Override
	public Calendar getData() {
		return this.calendar;
	}
	
}

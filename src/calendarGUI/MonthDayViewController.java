package calendarGUI;

import calendar.Day;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.shape.Arc;

public class MonthDayViewController {
	
	@FXML private Arc dayArc;
	@FXML private Label dayApp;
	@FXML private Label dayNo;
	
	private Day day;
	
	private void initialize() {
		dayNo.setText(String.valueOf(day.getDay().getDayOfMonth()));
		dayApp.setText(String.valueOf(day.getAppointments().size()));
	}

}

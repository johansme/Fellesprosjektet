package calendarGUI;

import java.time.LocalDate;
import java.util.List;

import calendar.Appointment;
import calendar.Day;
import calendar.DayChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;

public class MonthDayViewController implements DayChangeListener {
	
	@FXML private Arc dayArc;
	@FXML private Label dayApp;
	@FXML private Label dayNo;
	
	private Day day;
	
	@FXML
	private void initialize() {
		day = new Day(LocalDate.now());
		dayNo.setText(String.valueOf(day.getDay().getDayOfMonth()));
		dayApp.setText(String.valueOf(day.getAppointments().size()));
	}

	@Override
	public void dayChanged(Day day, List<Appointment> oldAppointments,
			List<Appointment> newAppointment) {
		dayArc.setFill(Color.RED);
		updateView();
	}

	private void updateView() {
		dayNo.setText(String.valueOf(day.getDay().getDayOfMonth()));
		dayApp.setText(String.valueOf(day.getAppointments().size()));
	}
	
	public void changeDiscovered() {
		dayArc.setFill(Color.AZURE);
	}

}

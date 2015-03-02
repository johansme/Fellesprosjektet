package calendarGUI;

import java.time.LocalDate;
import java.util.List;

import calendar.Appointment;
import calendar.Day;
import calendar.DayChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;

public class MonthDayViewController implements DayChangeListener {
	
	@FXML private Arc dayArc;
	@FXML private Label dayApp;
	@FXML private Label dayNo;
	@FXML private AnchorPane background;
	
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
		dayApp.visibleProperty().set(true);
		dayArc.visibleProperty().set(true);
		dayNo.visibleProperty().set(true);
		dayNo.setText(String.valueOf(day.getDay().getDayOfMonth()));
		dayApp.setText(String.valueOf(day.getAppointments().size()));
		if (day.getDay().isEqual(LocalDate.now())) {
			background.setStyle("-fx-background-color:#7FFFD4;");
		} else {
			background.setStyle("-fx-background-color:#FFFFFF;");			
		}
	}
	
	public void monthChange(Day day) {
		this.day = day;
		updateView();
	}
	
	public void setBlank() {
		dayApp.visibleProperty().set(false);
		dayArc.visibleProperty().set(false);
		dayNo.visibleProperty().set(false);
	}
	
	public Day getDay() {
		return day;
	}
	
	public void changeDiscovered() {
		dayArc.setFill(Color.AZURE);
	}

}

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
		dayNo.setText(String.valueOf(day.getDate().getDayOfMonth()));
		dayApp.setText(String.valueOf(day.getAppointments().size()));
	}

	@Override
	public void dayChanged(Day day, List<Appointment> oldAppointments,
			List<Appointment> newAppointment) {
		dayArc.setFill(Color.RED);
		updateView();
	}

	private void updateView() {
		dayApp.opacityProperty().set(1.0);
		dayArc.opacityProperty().set(1.0);
		dayNo.opacityProperty().set(1.0);
		dayNo.setText(String.valueOf(day.getDate().getDayOfMonth()));
		dayApp.setText(String.valueOf(day.getAppointments().size()));
		if (day.getDate().isEqual(LocalDate.now())) {
			background.setStyle("-fx-background-color:#7FFFD4;");
		} else {
			background.setStyle("-fx-background-color:#FFFFFF;");			
		}
	}
	
	public void monthChange(Day day) {
		this.day = day;
		updateView();
	}
	
	public void setTransparent() {
		dayApp.opacityProperty().set(0.5);
		dayArc.opacityProperty().set(0.5);
		dayNo.opacityProperty().set(0.5);
	}
	
	public Day getDay() {
		return day;
	}
	
	public void changeDiscovered() {
		dayArc.setFill(Color.AZURE);
	}

}

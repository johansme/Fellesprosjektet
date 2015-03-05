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
		updateView();
	}

	private void updateView() {
		setVisible();
		dayApp.opacityProperty().set(1.0);
		dayArc.opacityProperty().set(1.0);
		dayNo.opacityProperty().set(1.0);
		dayNo.setText(String.valueOf(day.getDate().getDayOfMonth()));
		dayApp.setText(String.valueOf(day.getAppointments().size()));
		if (day.getDirty()) {
			dayArc.setFill(Color.RED);
		} else {
			changeDiscovered();
		}
		if (day.getDate().isEqual(LocalDate.now())) {
			background.setStyle("-fx-background-color:#7FFFD4;");
		} else {
			background.setStyle("-fx-background-color:#FFFFFF;");			
		}
	}
	
	public void monthChange(Day day) {
		if (getDay() != null) {
			getDay().removeChangeListener(this);
		}
		this.day = day;
		getDay().addChangeListener(this);
		updateView();
		
	}
	
	public void setBlank() {
		dayApp.setVisible(false);
		dayArc.setVisible(false);
		dayNo.setVisible(false);
	}
	
	public void setVisible() {
		dayApp.setVisible(true);
		dayArc.setVisible(true);
		dayNo.setVisible(true);
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

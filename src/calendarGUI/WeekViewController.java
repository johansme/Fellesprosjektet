package calendarGUI;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import calendar.Appointment;
import calendar.Calendar;
import calendar.Day;
import calendar.DayChangeListener;
import calendar.Month;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import login.SceneHandler;

public class WeekViewController implements ControllerInterface, DayChangeListener {
	
	private Calendar calendar;
	private SceneHandler sceneHandler = new SceneHandler();
	private List<Day> days = new ArrayList<Day>();
	private HashMap<Integer, Appointment> appointments = new HashMap<Integer, Appointment>();
	private HashMap<Polygon, Integer> polygons = new HashMap<Polygon, Integer>();
	private HashMap<Label, Integer> labels = new HashMap<Label, Integer>();

	
	
	public void setView(Calendar c) {
		calendar=c;
		if (calendar.getLoggedInUser() != null) {
			if (calendar.getLoggedInUser().isAdmin()) {
				newAppointment.setPrefWidth(300);
				admin.setVisible(true);
				admin.setDisable(false);
			} else {
				newAppointment.setPrefWidth(400);
				admin.setVisible(false);
				admin.setDisable(true);
			}
		}
		setDates(calendar.getCurrentDate());
		setWeek(calendar.getCurrentDate());
		setYear(calendar.getCurrentDate());
		setAppointments(calendar.getCurrentDate());

		}
	
	@FXML
	private Label weekNum;
	
	@FXML
	private Label year;
	
	
	private void setWeek(LocalDate d) {
		int thisWeek = Calendar.getWeekNumber(d);
		int prevWeek = Calendar.getWeekNumber(d.minusWeeks(1));
		int nextWeek = Calendar.getWeekNumber(d.plusWeeks(1));
		weekNum.setText("Week "+thisWeek);
		prev.setText("Week "+prevWeek);
		next.setText("Week "+nextWeek);
	}
	
	private void setYear(LocalDate d) {
		if (d.getDayOfWeek().getValue()<4) {
			int i=4-d.getDayOfWeek().getValue();
			d=d.plusDays(i);
		}
		else if (d.getDayOfWeek().getValue()>4) {
			int i=d.getDayOfWeek().getValue()-4;
			d=d.minusDays(i);
		}
		int y=d.getYear();
		year.setText(" - "+y);
	}
	
	private void setDates(LocalDate d) {
		if (days.size() != 0) {
			for (Day day : days) {
				day.removeChangeListener(this);
			}
			days.clear();
		}
		int i = (d.getDayOfWeek().getValue()-1);
		LocalDate date = d.minusDays(i);
		//To get real-time updates of the view, regarding appointments
		Month month = calendar.getCurrentMonth();
		for (Month mon : calendar.getMonths()) {
			if (date.getYear() == mon.getYear() && date.getMonth().toString().equals(mon.getMonth())) {
				month = mon;
			}
		}
		int daysAdded = 0;
		for (int j = 0; j < 7; j++) {
			if (date.getDayOfMonth() + j <= month.getDays().length) {
				days.add(month.getDay(date.getDayOfMonth() + j));
				daysAdded++;
			}
		}
		if (calendar.getMonths().indexOf(month) == calendar.getMonths().size()-1) {
			calendar.addFutureMonths(3);
		}
		month = calendar.getMonths().get(calendar.getMonths().indexOf(month)+1);
		for (int j = 0; j < 7-daysAdded; j++) {
			days.add(month.getDay(j+1));
		}
		
		monDate.setText(date.getDayOfMonth()+". "+monthToString(date.getMonthValue()));
		date=date.plusDays(1);
		tuesDate.setText(date.getDayOfMonth()+". "+monthToString(date.getMonthValue()));
		date=date.plusDays(1);
		wedDate.setText(date.getDayOfMonth()+". "+monthToString(date.getMonthValue()));
		date=date.plusDays(1);
		thurDate.setText(date.getDayOfMonth()+". "+monthToString(date.getMonthValue()));
		date=date.plusDays(1);
		friDate.setText(date.getDayOfMonth()+". "+monthToString(date.getMonthValue()));
		date=date.plusDays(1);
		satDate.setText(date.getDayOfMonth()+". "+monthToString(date.getMonthValue()));
		date=date.plusDays(1);
		sunDate.setText(date.getDayOfMonth()+". "+monthToString(date.getMonthValue()));
		date=date.plusDays(1);
		
		for (Day day : days) {
			day.addChangeListener(this);
		}
	}
	
	@FXML
	private Button prev;
	
	@FXML
	public void prevAction() {
		calendar.setCurrentDate(calendar.getCurrentDate().minusWeeks(1));
		setView(calendar);
	}
	
	@FXML
	private Button next;
	
	@FXML
	public void nextAction() {
		calendar.setCurrentDate(calendar.getCurrentDate().plusWeeks(1));
		setView(calendar);
	}
	
	@FXML
	private AnchorPane screen;
	
	@FXML
	public void keyPressed(KeyEvent e) {
		if (e.getCode()==KeyCode.LEFT) {
			prevAction();
		}
		else if (e.getCode()==KeyCode.RIGHT) {
			nextAction();
		}
		else if (e.getCode()==KeyCode.PLUS) {
			newAction();
		}
		else if (e.getCode()==KeyCode.ESCAPE) {
			monthClicked((Event) e);
		}
		else if (e.getCode()==KeyCode.F5) {
			calendar.refresh(true);
		}
		else {
			return;
		}
	}
	
	@FXML
	private Button month;
	
	@FXML
	public void monthClicked(Event e) {
		sceneHandler.changeMonthRelatedScene(e, "/calendarGUI/MonthView.fxml", 940, 590, calendar);
	}
	
	@FXML
	private Button newAppointment;
	
	@FXML
	public void newAction() {
		sceneHandler.popUpScene("/newAppointment/NewAppointment.fxml", 590, 470, getData(), null);
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
	

	
	private void setAppointments(LocalDate d) {
		monAppointments.getChildren().clear();
		tuesAppointments.getChildren().clear();
		wedAppointments.getChildren().clear();
		thurAppointments.getChildren().clear();
		friAppointments.getChildren().clear();
		satAppointments.getChildren().clear();
		sunAppointments.getChildren().clear();
		int i = (d.getDayOfWeek().getValue()-1);
		LocalDate day = d.minusDays(i);
		calendar.setCurrentDate(day);
		for (int j=1; j<8; j++) {
			List<Appointment> appointments = calendar.getCurrentMonth().getDay(day.getDayOfMonth()).getActiveAppointments();
			if (!appointments.isEmpty()) {
				appointments.get(0).calculateOverlap();
			}
			for (int x = appointments.size()-1; x>=0; x--) {
				Appointment a = appointments.get(x);
				this.appointments.put(a.getID(), a);
				AnchorPane aView = drawAppointment(a, a.getOverlap());
				if (day.getDayOfWeek().getValue()==1) {
					monAppointments.getChildren().add(aView);
				}
				else if (day.getDayOfWeek().getValue()==2) {
					tuesAppointments.getChildren().add(aView);
				}
				else if (day.getDayOfWeek().getValue()==3) {
					wedAppointments.getChildren().add(aView);
				}
				else if (day.getDayOfWeek().getValue()==4) {
					thurAppointments.getChildren().add(aView);
				}
				else if (day.getDayOfWeek().getValue()==5) {
					friAppointments.getChildren().add(aView);
				}
				else if (day.getDayOfWeek().getValue()==6) {
					satAppointments.getChildren().add(aView);
				}
				else if (day.getDayOfWeek().getValue()==7) {
					sunAppointments.getChildren().add(aView);
				}
				
			}
			day=day.plusDays(1);
			calendar.setCurrentDate(day);
		}
		calendar.setCurrentDate(calendar.getCurrentDate().minusDays(6));
	}
	
	private AnchorPane drawAppointment(Appointment a, int[] overlapNum) {
		int start = (((a.getStartTime().getHour())-6)*28)+(((a.getStartTime().getMinute())/2));
		int end = (((a.getEndTime().getHour())-6)*28)+(((a.getEndTime().getMinute())/2));

		AnchorPane appointment = new AnchorPane();
		appointment.setLayoutX(0);
		appointment.setPrefWidth(95);
		appointment.setLayoutY(start);
		appointment.setPrefHeight(Math.max(14, end-start));
		
		Polygon box = new Polygon(
				0, 0, 
				79, 0, 
				Math.min(95, 80+(overlapNum[1]*5)), (0+(overlapNum[0]*14)), 
				Math.min(100, 85+(overlapNum[1]*5)), (2+(overlapNum[0]*14)), 
				Math.min(100, 85+(overlapNum[1]*5)), (12+(overlapNum[0]*14)), 
				Math.min(95, 80+(overlapNum[1]*5)), (14+(overlapNum[0]*14)), 
				Math.min(95, 80+(overlapNum[1]*5)), Math.max(14+(overlapNum[0]*14), end-start), 
				0, Math.max(14+(overlapNum[0]*14), end-start));
		box.setStroke(Color.BLACK);
		if (a.getAdmin()) {
			box.setFill(Color.AQUAMARINE);
		}
		else {
			box.setFill(Color.PALEGREEN);
		}
		if (!a.getOpened()) {
			box.setFill(Color.RED);
		}
		
		Label description = new Label();
		description.setPrefWidth(90);
		description.setLayoutY(overlapNum[0]*14);
		description.setAlignment(Pos.TOP_CENTER);
		description.setText(a.getDescription());	
		description.setFont(Font.font(10));
		appointment.getChildren().addAll(box, description);
		polygons.put(box, a.getID());
		labels.put(description, a.getID());
		appointment.setPickOnBounds(false);
		box.setOnMouseClicked(this::appointmentBoxClicked);
		description.setOnMouseClicked(this::appointmentLabelClicked);
		
		return appointment;
	}
	
	private void appointmentBoxClicked(MouseEvent e) {
		int id = polygons.get(e.getSource());
		appointments.get(id).setOpened(true);
		setView(calendar);
		sceneHandler.changeAppointmentRelatedScene("/calendarGUI/AppointmentView.fxml", 590, 470, calendar, appointments.get(id));
	}
	
	private void appointmentLabelClicked(MouseEvent e) {
		int id = labels.get(e.getSource());
		appointments.get(id).setOpened(true);
		setView(calendar);
		sceneHandler.changeAppointmentRelatedScene("/calendarGUI/AppointmentView.fxml", 590, 470, calendar, appointments.get(id));
	}
	
				
	public void setData(Calendar calendar) {
		if (calendar != null) {
			this.calendar = calendar;
		} else {
			this.calendar = new Calendar(null, null);
		}
		filterController.setData(calendar);
		filterController.setParent(this);
		setView(this.calendar);
	}
	
	@Override
	public void setData(Calendar c, Appointment a) {
		setView(c);
	}

	@Override
	public Calendar getData() {
		return this.calendar;
	}
	
	

	@Override
	public void setFeedback() {
		setView(calendar);
		
	}

	@Override
	public void dayChanged(Day day, List<Appointment> oldAppointments,
			List<Appointment> newAppointment) {
		calendar.setCurrentDate(day.getDate());
		setView(calendar);
	}
	
	@FXML private Pane filter;
	@FXML private FilterViewController filterController;
	
	@FXML private Button admin;
	
	@FXML
	private void adminPressed() {
		sceneHandler.popUpScene("/calendarGUI/AdminUserView.fxml", 345, 590, getData(), null);
	}
	
}

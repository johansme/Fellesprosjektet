package calendarGUI;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;

import calendar.Appointment;
import calendar.Calendar;
import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import login.SceneHandler;

public class WeekViewController extends Application implements ControllerInterface{

	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("WeekView.fxml/"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private Calendar calendar = new Calendar();
	private SceneHandler sceneHandler;
	private HashMap<Integer, Appointment> appointments = new HashMap<Integer, Appointment>();
	private HashMap<Polygon, Integer> polygons = new HashMap<Polygon, Integer>();
	private HashMap<Label, Integer> labels = new HashMap<Label, Integer>();

	
	@FXML
	private void initialize() {
		setView(calendar);
	}
	
	public void setView(Calendar c) {
		calendar=c;
		setWeek(calendar.getCurrentDate());
		setYear(calendar.getCurrentDate());
		setDates(calendar.getCurrentDate());
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
		int i = (d.getDayOfWeek().getValue()-1);
		LocalDate day = d.minusDays(i);
		monDate.setText(day.getDayOfMonth()+". "+monthToString(day.getMonthValue()));
		day=day.plusDays(1);
		tuesDate.setText(day.getDayOfMonth()+". "+monthToString(day.getMonthValue()));
		day=day.plusDays(1);
		wedDate.setText(day.getDayOfMonth()+". "+monthToString(day.getMonthValue()));
		day=day.plusDays(1);
		thurDate.setText(day.getDayOfMonth()+". "+monthToString(day.getMonthValue()));
		day=day.plusDays(1);
		friDate.setText(day.getDayOfMonth()+". "+monthToString(day.getMonthValue()));
		day=day.plusDays(1);
		satDate.setText(day.getDayOfMonth()+". "+monthToString(day.getMonthValue()));
		day=day.plusDays(1);
		sunDate.setText(day.getDayOfMonth()+". "+monthToString(day.getMonthValue()));
		day=day.plusDays(1);

	}
	
	@FXML
	private Button prev;
	
	@FXML
	public void prevAction() {
		setWeek(calendar.getCurrentDate().minusWeeks(1));
		calendar.changeWeek(false);
		setYear(calendar.getCurrentDate());
		setDates(calendar.getCurrentDate());
		setAppointments(calendar.getCurrentDate());

	}
	
	@FXML
	private Button next;
	
	@FXML
	public void nextAction() {
		setWeek(calendar.getCurrentDate().plusWeeks(1));
		calendar.changeWeek(true);
		setYear(calendar.getCurrentDate());
		setDates(calendar.getCurrentDate());
		setAppointments(calendar.getCurrentDate());
	}
	
	@FXML
	private Button month;
	
	@FXML
	public void monthClicked(Event e) {
		sceneHandler = new SceneHandler();
		sceneHandler.changeMonthRelatedScene(e, "/calendarGUI/MonthView.fxml", 800, 600, calendar);
	}
	
	@FXML
	private Button newAppointment;
	
	@FXML
	public void newAction() {
		sceneHandler = new SceneHandler();
		sceneHandler.popUpScene("/newAppointment/NewAppointment.fxml", 600, 480, getData());
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
		wedAppointments.getChildren().add(drawAppointment(example("13:00", "15:00", 0, true, true), 0));
		wedAppointments.getChildren().add(drawAppointment(example("12:00", "15:00", 1, true, false), 0));
		wedAppointments.getChildren().add(drawAppointment(example("11:00", "15:00", 2, false, false), 0));

//		int i = (d.getDayOfWeek().getValue()-1);
//		LocalDate day = d.minusDays(i);
//		for (int j=1; j<8; j++) {
//			List<Appointment> appointments = calendar.getCurrentMonth().getDay(day.getDayOfMonth()).getAppointments();
//			for (int x = appointments.size()-1; x>=0; x--) {
//				this.appointments.put(appointments.get(x).getID(), appointments.get(x));
//				AnchorPane aView = drawAppointment(appointments.get(x), appointments.get(x).getOverlap());
//				if (day.getDayOfWeek().getValue()==1) {
//					monAppointments.getChildren().add(aView);
//				}
//				else if (day.getDayOfWeek().getValue()==2) {
//					tuesAppointments.getChildren().add(aView);
//				}
//				else if (day.getDayOfWeek().getValue()==3) {
//					wedAppointments.getChildren().add(aView);
//				}
//				else if (day.getDayOfWeek().getValue()==4) {
//					thurAppointments.getChildren().add(aView);
//				}
//				else if (day.getDayOfWeek().getValue()==5) {
//					friAppointments.getChildren().add(aView);
//				}
//				else if (day.getDayOfWeek().getValue()==6) {
//					satAppointments.getChildren().add(aView);
//				}
//				else if (day.getDayOfWeek().getValue()==7) {
//					sunAppointments.getChildren().add(aView);
//				}
//				
//			}
//			day=day.plusDays(1);			
//		}
	}
	
	private AnchorPane drawAppointment(Appointment a, int overlapNum) {
		int start = (((a.getStartTime().getHour())-6)*28)+(((a.getStartTime().getMinute())/2));
		int end = (((a.getEndTime().getHour())-6)*28)+(((a.getEndTime().getMinute())/2));

		AnchorPane appointment = new AnchorPane();
		appointment.setLayoutX(0);
		appointment.setPrefWidth(95);
		appointment.setLayoutY(start);
		appointment.setPrefHeight(Math.max(14, end-start));
		
		Polygon box = new Polygon(
				0, 0, 
				89, 0, 
				90, (0+(overlapNum*14)), 
				100, (2+(overlapNum*14)), 
				100, (12+(overlapNum*14)), 
				90, (14+(overlapNum*14)), 
				90, Math.max(14+(overlapNum*14), end-start), 
				0, Math.max(14+(overlapNum*14), end-start));
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
		description.setLayoutY(0+(overlapNum*14));
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
		sceneHandler = new SceneHandler();
		sceneHandler.changeAppointmentRelatedScene("/calendarGUI/AppointmentView.fxml", 600, 480, calendar, appointments.get(id));
	}
	
	private void appointmentLabelClicked(MouseEvent e) {
		int id = labels.get(e.getSource());
		sceneHandler = new SceneHandler();
		sceneHandler.changeAppointmentRelatedScene("/calendarGUI/AppointmentView.fxml", 600, 480, calendar, appointments.get(id));
	}
	
	public static void main(String[] args) {
		launch(args);
	}
				
	public void setData(Calendar calendar) {
		if (calendar != null) {
			this.calendar = calendar;
		} else {
			this.calendar = new Calendar();
		}
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
	
	private Appointment example(String start, String end, int id, boolean opened, boolean admin) {
		Appointment a= new Appointment();
		a.setID(id);
		a.setDescription("Voksen-TV");
		a.setStartDate(calendar.getCurrentDate());
		a.setEndDate(calendar.getCurrentDate());
		a.setStartTime(LocalTime.parse(start));
		a.setEndTime(LocalTime.parse(end));
		a.setLocation("Færøyene");
		a.setAdmin(admin);
		a.setOpened(opened);
		appointments.put(a.getID(), a);
		return a;
	}
}



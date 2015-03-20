package calendarGUI;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import newAppointment.Room;

import org.json.JSONArray;
import org.json.JSONObject;
import api.API;
import calendar.Appointment;
import calendar.Calendar;
import calendar.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import login.SceneHandler;

public class AppointmentViewController implements ControllerInterface{

	private SceneHandler sceneHandler = new SceneHandler();
	private Calendar calendar;
	private Appointment appointment;

	@FXML
	private Label purpose;
	
	@FXML
	private Label startDate;
	
	@FXML
	private Label endDate;
	
	@FXML
	private Label from;
	
	@FXML
	private Label until;
	
	@FXML
	private Label room;
	
	@FXML
	private RadioButton yes;
	
	@FXML
	private RadioButton no;
	
	@FXML
	private Button confirmButton;
	
	@FXML
	private ListView<HBox> participants;
	
	@FXML
	private Button close;
	
	@FXML
	private Button edit;
	
	@FXML
	private ToggleGroup toggleAnswer;
	
	@FXML
	private HBox attendBox;
	
	@FXML
	private HBox participantsBox;
	
	@FXML
	private VBox screen;
	
	@FXML
	public void keyPressed(KeyEvent e) {
		if (e.getCode()==KeyCode.ESCAPE) {
			closeAction();
		}
		else if (e.getCode()==KeyCode.ENTER) {
			editAction();
		}
		else if (e.getCode()==KeyCode.DELETE) {
			deleteAction();
		}
	}
	
	@FXML
	public void confirmAnswer() {
		if (confirmButton.getText()=="Change") {
			appointment.setAttending("notAnswered");
		}
		else {
			User u = calendar.getLoggedInUser();

			if (toggleAnswer.getSelectedToggle()==yes) {
				appointment.setAttending("Y");
				if (!appointment.getUsers().isEmpty()) {
					boolean added = false;
					for (User user : appointment.getUsers()) {
						if (u.getId() == user.getId()) {
							appointment.setUserAttending(user, 1);
							added = true;
						}
					}
					if (! added) {
						appointment.addUser(u);
						appointment.setUserAttending(u, 0);
					}
				}
			}
			else if (toggleAnswer.getSelectedToggle()==no) {
				appointment.setAttending("N");
				for (User user : appointment.getUsers()) {
					if (u.getId() == user.getId()) {
						appointment.setUserAttending(user, 0);
					}
				}
			}
			sendConfirmationToServer(calendar.getLoggedInUser());
		}
		setView(appointment);
	}
	
	@FXML
	public void closeAction() {
		if (appointment.getRoom() != null) {
			appointment.setLocation("");
		}
	    // get a handle to the stage
	    Stage stage = (Stage) close.getScene().getWindow();
	    // do what you have to do
	    stage.close();

	}
	
	@FXML
	public void editAction() {
		closeAction();
		sceneHandler.popUpScene("/newAppointment/NewAppointment.fxml", 590, 470, getData(), appointment);
		
	}
	
	@FXML
	private Button delete;
	
	@FXML
	public void deleteAction() {
		sceneHandler.popUpMessage("/messages/Confirm.fxml", 290, 140, "Are you sure you want to delete?", this);
	}
	
	public void setView(Appointment a) {
		appointment=a;
		appointment.setOpened(true);
		purpose.setText(appointment.getDescription());
		startDate.setText(dateToString(appointment.getStartDate()));
		endDate.setText(dateToString(appointment.getEndDate()));
		from.setText(appointment.getStartStartTime().toString());
		until.setText(appointment.getEndEndTime().toString());
		if (appointment.getLocation().equals("")) {
			getRoomFromServer();
		}
	
		if (appointment.getRoom()==null) {
			room.setText(appointment.getLocation());			
		}
		else {
			room.setText(appointment.getRoom().getName());
		}
		edit.setDisable(!a.getAdmin());
		if (a.getAttending()=="Y" || a.getAttending()=="N") {
			if (a.getAttending()=="Y") {
				toggleAnswer.selectToggle(yes);
			}
			else {
				toggleAnswer.selectToggle(no);
			}
			yes.setDisable(true);
			no.setDisable(true);
			confirmButton.setText("Change");
		}
		else if (a.getAttending()=="None") {
			attendBox.getChildren().clear();
		}
		else if (a.getAttending()=="notAnswered"){
			yes.setDisable(false);
			no.setDisable(false);
			confirmButton.setText("Confirm");
		}
		else {
			toggleAnswer.selectToggle(yes);

		}
		if (a.getUsers().isEmpty()) {
			participantsBox.getChildren().clear();
		}
		else {
			participants.getItems().clear();
			List<User> partpts = appointment.getUsers();
			for (User p : partpts) {
				HBox line = new HBox();
				line.setPrefWidth(participants.getPrefWidth()-35);
				Label userLabel = new Label();
				userLabel.setText(p.getUsername());
				CheckBox checkBox = new CheckBox();
				if (appointment.getUserAttending(p)==1) {
					checkBox.setSelected(true);
				}
				else {
					checkBox.setSelected(false);
				}
				userLabel.setPrefWidth(line.getPrefWidth()-checkBox.getWidth());
				line.getChildren().addAll(userLabel, checkBox);
				attendMap.put(checkBox, p);
				participants.getItems().add(line);
				checkBox.setOnAction(new EventHandler<ActionEvent>() {
					@Override public void handle(ActionEvent e) {
						if (checkBox.isSelected()) {
							appointment.setUserAttending(attendMap.get(checkBox), 1);
						}
						else {
							appointment.setUserAttending(attendMap.get(checkBox), 0);
						}
						sendConfirmationToServer(attendMap.get(checkBox));
					}
				});

				if (!appointment.getAdmin()) {
					checkBox.setDisable(true);
				}
				
			}

		}
	}

	private void getRoomFromServer() {
		JSONObject obj = new JSONObject();
		obj.put("command", "get_from_app");
		obj.put("aid", appointment.getId());
		try {
			JSONObject res = API.call("/rooms", obj, calendar.getSession());
			int rid = res.getInt("rid");
			obj = new JSONObject();
			obj.put("command", "get");
			JSONArray arr = new JSONArray();
			arr.put(rid);
			obj.put("rid", arr);
			res = API.call("/rooms", obj, calendar.getSession());
			Room rm = new Room();
			rm.fromJSON(res.getJSONArray("rooms").getJSONObject(0));
			appointment.setRoom(rm);
		} catch (IOException e) {
		}
	}
	
	private HashMap<CheckBox, User> attendMap = new HashMap<CheckBox, User>();
	
	private String dateToString(LocalDate date) {
		int day = date.getDayOfMonth();
		int month = date.getMonthValue();
		if (month==1) {
			return day+". January";
		}
		if (month==2) {
			return day+". February";
		}
		if (month==3) {
			return day+". March";
		}
		if (month==4) {
			return day+". April";
		}
		if (month==5) {
			return day+". May";
		}
		if (month==6) {
			return day+". June";
		}
		if (month==7) {
			return day+". July";
		}
		if (month==8) {
			return day+". August";
		}
		if (month==9) {
			return day+". September";
		}
		if (month==10) {
			return day+". October";
		}
		if (month==11) {
			return day+". November";
		}
		if (month==12) {
			return day+". December";
		}
		else {
			return null;
		}
	}

	@Override
	public void setData(Calendar c, Appointment a) {
		calendar=c;
		setView(a);
		
	}
	
	@Override
	public void setData(Calendar c) {
		
	}

	@Override
	public Calendar getData() {
		return this.calendar;
	}

	@Override
	public void setFeedback() {
		try {
			if (appointment.getAdmin()) {
				appointment.delete();
			} else {
				JSONObject obj = new JSONObject();
				obj.put("command", "update_hidden");
				obj.put("uid", calendar.getLoggedInUser().getId());
				obj.put("aid", appointment.getID());
				obj.put("hidden", true);
				API.call("/invitation", obj, calendar.getSession());
			}
		} catch (IOException e) {
			sceneHandler.popUpMessage("/messages/Error.fxml", 290, 140, "WTF", this);
		}
	    // get a handle to the stage
	    Stage stage = (Stage) close.getScene().getWindow();
	    // do what you have to do
	    stage.close();
		
	}
	
	private void sendConfirmationToServer(User user) {
		int attends = -1;
		if (user.equals(calendar.getLoggedInUser())) {
			if (appointment.getAttending().equals("Y")) {
				attends = 1;
			} else if (appointment.getAttending().equals("N")) {
				attends = 0;
			}
		} else {
			if (appointment.getUserAttending(user) == 1) {
				attends = 1;
			} else if (appointment.getUserAttending(user) == 0) {
				attends = 0;
			}
		}
		JSONObject obj = new JSONObject();
		obj.put("command", "update_attending");
		obj.put("uid", user.getId());
		obj.put("aid", appointment.getID());
		obj.put("attending", attends==1? true : false);
		try {
			API.call("/invitation", obj, calendar.getSession());
		} catch (IOException e) {
		}
	}
	
}

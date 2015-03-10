package newAppointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import calendar.Appointment;
import calendar.Calendar;
import calendar.User;
import calendarGUI.ControllerInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import login.SceneHandler;

public class NewAppointmentController implements ControllerInterface {

	private Appointment appointment;

	private Calendar calendar;
	private SceneHandler sceneHandler = new SceneHandler();

	// rigging up fxml variables

	@FXML private TextField descriptionField;
	@FXML private TextField fromField;
	@FXML private TextField toField;
	@FXML private TextField capasityField;
	@FXML private DatePicker fromDate;
	@FXML private DatePicker toDate;
	@FXML private TextField otherField;
	@FXML private Button saveButton;
	@FXML private Button cancelButton;
	@FXML private Pane screen;
	@FXML private MenuButton room; 
	@FXML private ListView<String> listView; 
	@FXML private Button removeUserButton; 
	@FXML private Button addButton; 
	private ObservableList<String> listViewData = FXCollections.observableArrayList();
	private List<User> participantList = new ArrayList<User>();
	ObservableList<MenuItem> roomValueList;
	

	ReceiveRoom rr = new ReceiveRoom();
	
	
	@FXML
	private void description(){
		/* System.out.println("YOLO"); */
		//SMEGMABRO
		//BiiiiiiRkyy
	}
	
	@FXML
	private void keyPressed(KeyEvent e) {
		if (e.getCode() == KeyCode.ENTER) {
			saveButtonPressed((Event) e);
		}
		else if (e.getCode() == KeyCode.ESCAPE) {
			cancelButtonPressed();
		}
		else {
			return;
		}
	}

	@FXML
	private void cancelButtonPressed(){
		// canselbutton pressed: close the stage

		// get a handle to the stage
		Stage stage = (Stage)cancelButton.getScene().getWindow();
		// do what you have to do
		stage.close();


	}

	@FXML
	private void saveButtonPressed(Event e){
		
		//saveButton pressed check if fields are filled and save data
		if(checkFieldsFill()){

			if (header.getText()=="Edit appointment") {
				appointment.delete();
			}
			Appointment a = new Appointment();
			LocalDate startDate = fromDate.getValue();
			LocalDate endDate = toDate.getValue();
			List<Appointment> days = new ArrayList<Appointment>();
			int diff = endDate.getDayOfYear()-startDate.getDayOfYear();
			if (endDate.getYear()>startDate.getYear()) {
				diff = diff+365;
				if (startDate.isLeapYear()) {
					diff++;
				}
			}
			for (int i=0; i<diff+1; i++) {
				if (i==0) {
					a.setStartTime(LocalTime.parse(fromField.getText()));
					a.setPrev(null);
				}
				else {
					a.setPrev(days.get(i-1));
					days.get(i-1).setNext(a);
					a.setStartTime(LocalTime.parse("06:00"));
				}
				if (i==diff) {
					a.setEndTime(LocalTime.parse(toField.getText()));
					a.setNext(null);
				}
				else {
					a.setEndTime(LocalTime.parse("23:00"));
				}
				a.setDate(startDate.plusDays(i));
				calendar.setCurrentDate(startDate.plusDays(i));
				a.setData(calendar);
				days.add(a);
				a = new Appointment();
			}
			a = days.get(0);
			a.setDescription(descriptionField.getText());
			if(room.textProperty().getValue() == "Other"){
				a.setLocation(otherField.textProperty().getValue());
				}else{
				a.setLocation(room.textProperty().getValue()); 
				}

			User gruppe2 = new User();
			a.setUsers(Arrays.asList(gruppe2));
			a.setAdmin(true);
			a.setOpened(true);
			for (Appointment ap : days) 
			{
				ap.addAppointmentToDay();
			}
			sceneHandler.popUpMessage("/messages/Info.fxml", 300, 150, "Your appointment has been saved", this);
			// get a handle to the stage
			Stage stage = (Stage) saveButton.getScene().getWindow();
			// do what you got to do :)
			stage.close();
		}
		else {
			sceneHandler.popUpMessage("/messages/Error.fxml", 300, 150, "Check your fields for valid input.", this);
		}
	}

	@FXML
	private void checkCapasity(){
		String value = capasityField.textProperty().getValue();
		if( !value.matches("\\d+") || value.length() >5 ){
			capasityField.setPromptText("Invalid");
			capasityField.setText("1");
		}else{
		Tuple rm = bestRoom(Integer.valueOf(capasityField.textProperty().getValue()));
		
		
		if(rm.room == "Other"){
			room.setText("Other");
			otherField.setDisable(false);
		}else{
			room.setText(rm.room +" (" + rm.capasity +")");
			otherField.setDisable(true);
			otherField.textProperty().setValue("");
		}
	}
	}
	
	public Tuple bestRoom(int cap){
		List<Tuple> rooms = rr.getRoomList();
		int min = 100000;
		Tuple room = new Tuple("Other",0);
		for (Tuple tup: rooms){
			int as = tup.capasity - cap;
			if( as < min && as >=0){
				min = as;
				room = tup;
			}
		}
		
		return room;
		
	}

	// methods for handling input in time fields
	@FXML
	private void fromTime(){
		if(!fromField.textProperty().getValue().matches("\\d\\d:\\d\\d") && !fromField.textProperty().getValue().matches("\\d\\d") ){
			fromField.setText(toTwoDigits(LocalTime.now().getHour()) +":" + toTwoDigits(LocalTime.now().getMinute()));
		}else if(fromField.textProperty().getValue().matches("\\d\\d")){

			fromField.setText(fromField.getText() + ":00");
		}
		if(!validFromTime()){
			fromField.setText(toTwoDigits(LocalTime.now().getHour()) +":" + toTwoDigits(LocalTime.now().getMinute()));

		}
	}
	
	@FXML
	private void toTime(){
		String value = toField.textProperty().getValue();
		if( !value.matches("\\d\\d:\\d\\d") && !toField.textProperty().getValue().matches("\\d\\d") ){
			toField.setText(fromField.textProperty().getValue());
			toField.setPromptText("Ugyldig");
		}else if(value.matches("\\d\\d")){

			toField.setText(toField.getText() + ":00");
		}
		if(!validToTime()){
			toField.setText(fromField.textProperty().getValue());

		}

	}

	private String toTwoDigits(int timeElement) {
		if (timeElement < 10) {
			return 0 + "" + timeElement;
		}
		return timeElement + "";
	}
	
	//validating of time
	private boolean validToTime(){

		//casting to more approtiate data for handling time

		String tilTid = toField.textProperty().getValue();
		String fraTid = fromField.textProperty().getValue();
		int tilTidTime = Integer.parseInt(tilTid.split(":")[0]);
		int fraTidTime = Integer.parseInt(fraTid.split(":")[0]);
		int tilTidMin = Integer.parseInt(tilTid.split(":")[1]);
		int fraTidMin = Integer.parseInt(fraTid.split(":")[1]);

		// arithmetics for checking correct time

		if(tilTidTime>fraTidTime){
			//	appoint.setFra(LocalTime.of(tilTidTime,tilTidMin));
			return true;

		}else if(tilTidTime == fraTidTime && tilTidMin>fraTidMin){
			//	appoint.setFra(LocalTime.of(tilTidTime,tilTidMin));
			return true;
		}
		else{
			return false;

		}
	}


	private boolean validFromTime(){

		//casting to more approtiate data for handling time

		String fraTid = fromField.textProperty().getValue();
		int fraTidTime = Integer.parseInt(fraTid.split(":")[0]);
		int fraTidMin = Integer.parseInt(fraTid.split(":")[1]);

		// arithmetics for checking correct time

		if(fromDate.getValue().toString().equals( LocalDate.now().toString())){



			if(LocalTime.now().getHour()<fraTidTime){
				//appoint.setFra(LocalTime.of(fraTidTime,fraTidMin));
				return true;


			}else if(LocalTime.now().getHour() == fraTidTime && LocalTime.now().getMinute()<fraTidMin){
				//appoint.setFra(LocalTime.of(fraTidTime,fraTidMin));
				return true;
			}
			else{
				return false;

			}
		}
		//appoint.setFra(LocalTime.of(fraTidTime,fraTidMin));
		return true;
	}


	//date handling
	@FXML
	private void fromDate(){
		disableDates(toDate, fromDate.getValue());
		if (toDate.getValue().isBefore(fromDate.getValue())) {
			toDate.setValue(fromDate.getValue());
		}
		//appoint.setDato(fromDate.getValue());
	}


	//method for disabling invalid dates

	private void disableDates(DatePicker datepicker, final LocalDate now) {
		final Callback<DatePicker, DateCell> dayCellFactory = 
				new Callback<DatePicker, DateCell>() {
			@Override
			public DateCell call(final DatePicker datePicker) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);

						if (item.isBefore(now)) {
							setDisable(true);
							setStyle("-fx-background-color: #ffc0cb;");
						}   
					}
				};
			}
		};
		datepicker.setDayCellFactory(dayCellFactory);

	}


	// set default data in fields to help user

	@FXML
	public void initialize(){
		
		getRoomFromDB();
		descriptionField.setPromptText("Appointment Description...");
		fromField.setText(LocalTime.now().getHour() + 1+":00" );
		toField.setText(LocalTime.now().getHour() + 2 + ":00" );
		
		addUsers();
		capasityField.textProperty().setValue("1");

		fromDate.setValue(LocalDate.now());
		disableDates(fromDate, LocalDate.now());
		toDate.setValue(LocalDate.now());
		disableDates(toDate, LocalDate.now());
		

	}

	// method for checking if necessary fields are filled out
	public boolean checkFieldsFill(){

		if(!descriptionField.textProperty().getValue().isEmpty() &&
				toField.textProperty().getValue() != "" &&
				fromField.textProperty().getValue() != "" &&
				capasityField.textProperty().getValue() != "" &&
				!room.textProperty().getValue().equals("Choose room")
				){
			return true;
		}
		return false;
	}

	@Override
	public void setData(Calendar calendar) {
		if (calendar != null) {
			this.calendar = calendar;
		}
		header.setText("New appointment");
	}

	@Override
	public Calendar getData() {
		return this.calendar;
	}

	@Override
	public void setData(Calendar c, Appointment a) {
		if (c != null) {
			this.calendar = c;
			if (a != null) {
				this.appointment = a;
				header.setText("Edit appointment");
				descriptionField.setText(a.getDescription());

				fromField.setText(a.getStartTime().toString());
				toField.setText(a.getEndTime().toString());

				if (!a.getUsers().isEmpty()) {
					capasityField.textProperty().setValue("" + a.getUsers().size());
				}

				fromDate.setValue(a.getStartDate());
				disableDates(fromDate, LocalDate.now());
				toDate.setValue(a.getEndDate());
				disableDates(toDate, LocalDate.now());
			}
			else {
				this.appointment = new Appointment();
				if (calendar.getCurrentDate().isBefore(LocalDate.now())) {
					fromDate.setValue(LocalDate.now());
					toDate.setValue(LocalDate.now());
				} else {
					fromDate.setValue(calendar.getCurrentDate());
					toDate.setValue(calendar.getCurrentDate());
				}
				disableDates(fromDate, LocalDate.now());
				disableDates(toDate, LocalDate.now());
			}
		}
	}

	@Override
	public void setFeedback() {
		
	}
	
	@FXML
	public void menuButton(Event e){
		room.setText(e.getSource().getClass().getName());
	}
	
	@FXML
	private Label header;

	
	public void getRoomFromDB(){
		
		
		rr.makeRoomQuery();
		for(int i = 0; i < rr.getRoomList().size(); i++)
		{
			MenuItem it = new MenuItem(rr.getRoomList().get(i).room +" (" + rr.getRoomList().get(i).capasity+")");
			it.setOnAction(new EventHandler<ActionEvent>() {
			    public void handle(ActionEvent t) {
			        room.setText(it.getText());
			        otherField.setDisable(true);
			        otherField.textProperty().setValue("");
			    }
			});
			
			room.getItems().add(it);
		}
		MenuItem other = new MenuItem("Other");
		other.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent t) {
		        room.setText(other.getText());
		        otherField.setDisable(false);
		    }
		});
		room.getItems().add(other);
		
	}
	
	@FXML
	public void addUsers()
	{
		
		listViewData.add(new String("Steve Jobs"));
		listViewData.add(new String("Mark Zuckerberg"));
		listViewData.add(new String("Bill Gates"));
		listViewData.add(new String("Edward Snowden"));
		listViewData.add(new String("Steve Wozniak"));
		listViewData.add(new String("Linus Torvalds"));
		listViewData.add(new String("Sean Parker"));
		listViewData.add(new String("Charles Babbage"));
		listViewData.add(new String("Alan Turing"));
		
		
		listView.setItems(listViewData);
		
	}
	
	@FXML
	public void removeUser()
	{
		String user = listView.getSelectionModel().getSelectedItem();
		if(user != null){
			listViewData.remove(user);
			String msg = user + " removed";
			sceneHandler.popUpMessage("/messages/Info.fxml", 300, 150, msg, this);
			
		}
		else sceneHandler.popUpMessage("/messages/Info.fxml", 300, 150, "No participant selected.", this);
		
		
	}
	
	@FXML
	public void addPerson(){
		
	}
	
	public void addParticipant(User user) {
		if (user != null && ! participantList.contains(user)) {
			participantList.add(user);
			listViewData.add(user.toString());
			listView.getItems().add(user.toString());
		}
	}

}

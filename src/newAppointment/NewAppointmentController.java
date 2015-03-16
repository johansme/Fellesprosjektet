package newAppointment;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import calendar.Appointment;
import calendar.Calendar;
import calendar.Participant;
import calendarGUI.ControllerInterface;
import calendarGUI.ParticipantController;
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

public class NewAppointmentController implements ControllerInterface, ParticipantController {

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
	private List<Participant> participantList = new ArrayList<Participant>();
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
				try {
					appointment.delete();
				} catch (IOException e1) {
					sceneHandler = new SceneHandler();
					sceneHandler.popUpMessage("/messages/Error.fxml", 300, 150, "WTF", this);
				}
			}
			Appointment a = new Appointment(calendar);
			LocalDate startDate = fromDate.getValue();
			LocalDate endDate = toDate.getValue();
			List<Appointment> days = new ArrayList<Appointment>();
			int diff = endDate.getDayOfYear()-startDate.getDayOfYear();
			if (endDate.getYear()>startDate.getYear()) {
				for (int i=0; i<endDate.getYear()-startDate.getYear(); i++) {
					diff+=365;
					if (startDate.plusYears(i).isLeapYear()) {
						diff++;
					}
				}
			}
			for (int i=0; i < diff+1; i++) {
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
				a.setData(calendar);
				days.add(a);
				a = new Appointment(calendar);
			}
			a = days.get(0);
			a.setDescription(descriptionField.getText());
			if(room.textProperty().getValue().equals("Other")){
				a.setLocation(otherField.textProperty().getValue());
			}else{
				a.setLocation(room.textProperty().getValue()); 
			}

			a.setAdmin(true);
			a.setOpened(true);
			a.setPersonal(true);
			for (Appointment ap : days) 
			{
				ap.addAppointmentToDay();
			}
			a.createInServer();
			sceneHandler.popUpMessage("/messages/Info.fxml", 300, 150, "Your appointment has been saved", this);
			// get a handle to the stage
			Stage stage = (Stage) saveButton.getScene().getWindow();
			// do what you got to do :)
			stage.close();
		}
		else 
		{
			sceneHandler.popUpMessage("/messages/Error.fxml", 300, 150, "Check your fields for valid input.", this);
		}
		
	}
	
	// method for checking if necessary fields are filled out @Birk
		public boolean checkFieldsFill()
		{
			
			if(!descriptionField.textProperty().getValue().isEmpty() &&
				!toField.textProperty().getValue().equals("") &&
				!fromField.textProperty().getValue().equals("") &&
				!otherField.textProperty().getValue().equals("") &&
				capasityField.textProperty().getValue() != "" &&
				!room.textProperty().getValue().equals("Choose room"))
			{
				
				return true;
			}
			return false;
		}

	@FXML
	private void checkCapasity(){
		String value = capasityField.textProperty().getValue();
		if( !value.matches("\\d+") || value.length() >5 ){
			capasityField.setPromptText("Invalid");
			capasityField.setText("1");
		}else{
			Tuple rm = bestRoom(Integer.valueOf(capasityField.textProperty().getValue()));


			if(rm.room.equals("Other")) {
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
	
	private void printErrorMessage(String errorMsg){
		
		sceneHandler.popUpMessage("/messages/Error.fxml", 300, 150, errorMsg, this);
	}
	
	private boolean checkValidFromField(){

		
		if(!fromField.textProperty().getValue().matches("\\d\\d:\\d\\d") && !fromField.textProperty().getValue().matches("\\d\\d") && !fromField.textProperty().getValue().matches("\\d") ){
			
			fromField.setText(toTwoDigits(LocalTime.now().getHour()) + ":" + toTwoDigits(LocalTime.now().getMinute()));
			return true;
			
		}
		else if(fromField.textProperty().getValue().matches("\\d\\d:\\d")){
			fromField.setText(fromField.getText() + "0");
			return true;
		}
		else if(fromField.textProperty().getValue().matches("\\d\\d")){

			fromField.setText(fromField.getText() + ":00");
			return true;
		}
		 if(fromField.textProperty().getValue().matches("\\d")){
			fromField.setText("0" + fromField.getText() + ":00");
			return true;
		}
		return true; 
		
	}
	
	private boolean checkValidToField(){

		
		if(!toField.textProperty().getValue().matches("\\d\\d:\\d\\d") && !toField.textProperty().getValue().matches("\\d\\d") && !toField.textProperty().getValue().matches("\\d") )
		{
			
			toField.setText(toTwoDigits(LocalTime.now().getHour()) + ":" + toTwoDigits(LocalTime.now().getMinute()));
			return true;
			
		}
		else if(toField.textProperty().getValue().matches("\\d\\d:\\d")){
			toField.setText(toField.getText() + "0");
			return true;
		}
		else if(toField.textProperty().getValue().matches("\\d\\d")){

			toField.setText(toField.getText() + ":00");
			return true;
		}
		 if(toField.textProperty().getValue().matches("\\d")){
			toField.setText("0" + toField.getText() + ":00");
			return true;
		}
		return true; 
		
	}
	
	// methods for handling input in time fields
	@FXML
	private void fromTime(){
		
		if(checkValidFromField()){
			
			String fromTime = fromField.textProperty().getValue();
			int fromTimeHour = Integer.parseInt(fromTime.split(":")[0]);
			int fromTimeMin = Integer.parseInt(fromTime.split(":")[1]);
			
			String toTime = toField.textProperty().getValue();
			int toTimeHour = Integer.parseInt(toTime.split(":")[0]);
			int toTimeMin = Integer.parseInt(toTime.split(":")[1]);
			
			int fromTimeMinutes = (fromTimeHour * 60) + fromTimeMin;
			int toTimeMinutes = (toTimeHour * 60) + toTimeMin; 
			int timeDiffInMin;
			if (toTimeMinutes > fromTimeMinutes)
				timeDiffInMin = toTimeMinutes - fromTimeMinutes;
			else 
				timeDiffInMin = fromTimeMinutes - toTimeMinutes;
			
			if(!validToTime()){
				if(timeDiffInMin < 30){
					toField.setText(getRoundHalfHour(fromTimeHour, fromTimeMin));
					printErrorMessage("The appointment must last for at least 30 minutes.");
				}
				
			}
			
			if(!validFromTime())
			{
				fromField.setText(toTwoDigits(LocalTime.now().getHour()) + ":" + toTwoDigits(LocalTime.now().getMinute()));
				if(timeDiffInMin < 30) {
					toField.setText(getRoundHalfHour(fromTimeHour, fromTimeMin));
					printErrorMessage("The appointment must last for at least 30 minutes.");
				}
			}
			
			
			if(minAndMaxAllowedStartTime())
			{
				if(timeDiffInMin < 30) {
					toField.setText(getRoundHalfHour(fromTimeHour, fromTimeMin));
				}
			}
			else 
			{
					
					if(fromTimeHour >= 22 && fromTimeMin > 30 || toTimeHour >= 22 && toTimeMin >= 0 )
					{
						fromField.setText("22:30");
						toField.setText("23:00");
						printErrorMessage("The appointment must be between 06:00 and 23:00 and must last for at least 30 minutes.");
					}
					else if(fromTimeHour > 22 && fromTimeMin >= 0 || toTimeHour > 22 && toTimeMin >= 0){
						fromField.setText("22:30");
						toField.setText("23:00");
						printErrorMessage("The appointment must be between 06:00 and 23:00 and must last for at least 30 minutes.");
					}
					else if(fromTimeHour < 6 && fromTimeHour >= 0)
					{
						fromField.setText("06:00");
						toField.setText("06:30");
						printErrorMessage("The appointment must be between 06:00 and 23:00 and must last for at least 30 minutes.");
					}
					
					
			}
		}
	
	}
	
	@FXML
	private void toTime(){
		
		if(checkValidToField()){
			
			String fromTime = fromField.textProperty().getValue();
			int fromTimeHour = Integer.parseInt(fromTime.split(":")[0]);
			int fromTimeMin = Integer.parseInt(fromTime.split(":")[1]);
			
			String toTime = toField.textProperty().getValue();
			int toTimeHour = Integer.parseInt(toTime.split(":")[0]);
			int toTimeMin = Integer.parseInt(toTime.split(":")[1]);

			int fromTimeMinutes = (fromTimeHour * 60) + fromTimeMin;
			int toTimeMinutes = (toTimeHour * 60) + toTimeMin; 
			int timeDiffInMin;
			if (toTimeMinutes > fromTimeMinutes)
				timeDiffInMin = toTimeMinutes - fromTimeMinutes;
			else 
				timeDiffInMin = fromTimeMinutes - toTimeMinutes;
			
			if(!validToTime()){
				if(timeDiffInMin < 30) toField.setText(getRoundHalfHour(fromTimeHour, fromTimeMin));
				//else toField.setText(getRoundHalfHour(fromTimeHour, fromTimeMin));
				
			}
			
			if(fromTimeHour > toTimeHour){
				toField.setText(getRoundHalfHour(fromTimeHour, fromTimeMin));
			}
			if(!minAndMaxAllowedToTime())
			{
				
				if(fromTimeHour >= 22 && fromTimeMin > 30 || toTimeHour >= 22 && toTimeMin >= 0 ){
					fromField.setText("22:30");
					toField.setText("23:00");
					printErrorMessage("The appointment must be between 06:00 and 23:00 and must last for at least 30 minutes.");
				}
				else if(fromTimeHour > 22 && fromTimeMin >= 0 || toTimeHour > 22 && toTimeMin >= 0 ){
					fromField.setText("22:30");
					toField.setText("23:00");
					printErrorMessage("The appointment must be between 06:00 and 23:00 and must last for at least 30 minutes.");
				}
				else if(timeDiffInMin < 30) 
				{
					toField.setText(getRoundHalfHour(fromTimeHour, fromTimeMin));
					printErrorMessage("The appointment must last for at least 30 minutes.");
				}
				//else toField.setText(getRoundHalfHour(fromTimeHour, fromTimeMin));
				
			}
		}

	}
	private boolean minAndMaxAllowedStartTime(){

		String fromTime = fromField.textProperty().getValue();
		int fromTimeHour = Integer.parseInt(fromTime.split(":")[0]);
		int fromTimeMin = Integer.parseInt(fromTime.split(":")[1]);

		String toTime = toField.textProperty().getValue();
		int toTimeHour = Integer.parseInt(toTime.split(":")[0]);
		int toTimeMin = Integer.parseInt(toTime.split(":")[1]);

		int timeHourDiff = toTimeHour - fromTimeHour;
		int timeMinDiff = toTimeMin - fromTimeMin; 

		int minAllowedStartHour = 06; 
		int maxAllowedStartHour = 22;
		//from 22:35 - to 23:00 
		
		if(fromTimeHour >= minAllowedStartHour && fromTimeHour <= maxAllowedStartHour)
		{
			if(timeHourDiff == 0 && timeMinDiff < 30) return false;
			else return true;
		}
		else return false;

	}
	
	private boolean minAndMaxAllowedToTime(){

		String fromTime = fromField.textProperty().getValue();
		int fromTimeHour = Integer.parseInt(fromTime.split(":")[0]);
		int fromTimeMin = Integer.parseInt(fromTime.split(":")[1]);

		String toTime = toField.textProperty().getValue();
		int toTimeHour = Integer.parseInt(toTime.split(":")[0]);
		int toTimeMin = Integer.parseInt(toTime.split(":")[1]);

		int timeHourDiff = toTimeHour - fromTimeHour;
		int timeMinDiff = toTimeMin - fromTimeMin; 

		int minAllowedToHour = 06; 
		int maxAllowedToHour = 23; 

		if(timeHourDiff < 0) return false; 
		if(toTimeHour == 23 && toTimeMin > 0) return false; 
		if(toTimeHour >= minAllowedToHour && toTimeHour <= maxAllowedToHour){
			if(timeHourDiff == 0 && timeMinDiff < 30) return false;
			else return true;
		}
		else return false; 
		
		
		
	}
	//adding 30 minutes to fromTimeHours:fromTimeMin
	//checking if hours or mins are >= 0 and <= 9, if yes add "0"
	private String getRoundHalfHour(int fromTimeHour, int fromTimeMin){
		
		if(fromTimeMin == 0){
			return (fromTimeHour >= 1 && fromTimeHour <= 9) ?  "0" + fromTimeHour + ":" + (fromTimeMin + 30) : fromTimeHour + ":" + (fromTimeMin + 30); 
		}
		else if(fromTimeMin < 30){
			return (fromTimeHour >= 1 && fromTimeHour <= 9) ?  "0" + fromTimeHour + ":" + (fromTimeMin + 30) : fromTimeHour + ":" + (fromTimeMin + 30);
		}
		else if(fromTimeMin == 30){
			return (fromTimeHour >= 1 && fromTimeHour <= 9) ?  "0" + (fromTimeHour + 1) + ":" + "00" : (fromTimeHour + 1) + ":" + "00";
		}
		else if(fromTimeMin > 30){
			
			int minDiff = fromTimeMin - 30;
			if(minDiff >= 0 && minDiff <= 9)
				return (fromTimeHour >= 1 && fromTimeHour <= 9) ?  "0" + (fromTimeHour + 1) + ":" + "0" + minDiff : (fromTimeHour + 1) + ":" + "0" + minDiff;
			else
				return (fromTimeHour >= 1 && fromTimeHour <= 9) ?  "0" + (fromTimeHour + 1) + ":" + minDiff : (fromTimeHour + 1) + ":" + minDiff;
		}
		
		else return fromTimeHour + ":" + fromTimeMin;  
		
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

		if(tilTidTime > fraTidTime){
			//	appoint.setFra(LocalTime.of(tilTidTime,tilTidMin));
			return true;

		}else if(tilTidTime == fraTidTime && tilTidMin > fraTidMin){
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

		if(fromDate.getValue().toString().equals(LocalDate.now().toString()))
		{


			// arithmetics for checking if fromTime is >= 06:00 and <= 22:30 @Alex
			if(LocalTime.now().getHour() < fraTidTime){
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
	
	//On initialize, set from- and toField. add "0" if hour/minute is btwn 0 and 9 @Alex
	
	private void checkTodaysHourAndMin(){
		
		int todayHour = LocalTime.now().getHour();
		int todayMinute = LocalTime.now().getMinute();
		
		//add "0" to hour and/or min
		if(todayHour >= 0 && todayHour <= 9 && todayMinute >= 0 && todayMinute <= 9) fromField.setText("0" + (todayHour + 1) + ":" + "0" + todayMinute);
		else if(todayHour >= 0 && todayHour <= 9) fromField.setText("0" + (todayHour + 1) + ":" + todayMinute);
		else if(todayMinute >= 0 && todayMinute <= 9) fromField.setText((todayHour + 1) + ":" + "0" + todayMinute);
		else fromField.setText((todayHour + 1) + ":" + todayMinute);
		toField.setText(getRoundHalfHour(LocalTime.now().getHour() + 1, LocalTime.now().getMinute()));
	}
	
	// set default data in fields to help user @Birk
	@FXML
	public void initialize(){

		
		descriptionField.setPromptText("Appointment Description...");
		checkTodaysHourAndMin();
		addUsers();
		capasityField.textProperty().setValue("1");
		fromDate.setValue(LocalDate.now());
		disableDates(fromDate, LocalDate.now());
		toDate.setValue(LocalDate.now());
		disableDates(toDate, LocalDate.now());


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
				this.appointment = new Appointment(calendar);
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

		sceneHandler.popUpParticipants("/newAppointment/AddParticipants.fxml", 500, 300, getData(), this);
	}
	
	public void addParticipant(Participant participant) {
		if (participant != null && ! participantList.contains(participant)) {
			participantList.add(participant);
			listViewData.add(participant.toString());
			listView.getItems().add(participant.toString());
		}
	}

}

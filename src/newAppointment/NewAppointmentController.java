package newAppointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

import calendar.Appointment;
import calendar.Calendar;
import calendarGUI.ControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;
import login.SceneHandler;

public class NewAppointmentController implements ControllerInterface {

	private Appointment appoint = new Appointment();

	private Calendar calendar;

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


	@FXML
	private void description(){
		/* System.out.println("YOLO"); */
		//SMEGMABRO
		//BiiiiiiRkyy
	}

	@FXML
	private void cancelButtonPressed(){
		// canselbutton pressed: close the stage

		// get a handle to the stage
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		// do what you have to do
		stage.close();


	}

	@FXML
	private void saveButtonPressed(){
		//saveButton pressed check if fields are filled and save data
		if(checkFieldsFill()){
			appoint.setDescription(descriptionField.textProperty().getValue());
			appoint.setStartDate(fromDate.getValue());
			appoint.setEndDate(toDate.getValue());
			String[] fromTime = fromField.textProperty().getValue().split(":");
			appoint.setStartTime(LocalTime.of(Integer.valueOf(fromTime[0]), Integer.valueOf(fromTime[1])));
			String[] toTime = toField.textProperty().getValue().split(":");
			appoint.setEndTime(LocalTime.of(Integer.valueOf(toTime[0]), Integer.valueOf(toTime[1])));
			appoint.setLocation("NTNU"); // this needs fixing:)
<<<<<<< HEAD
			appoint.setParticipants((ArrayList<String>) Arrays.asList("birk","terje","johannes","alex","einar"));
=======
			appoint.setParticipants(Arrays.asList("birk","terje","johannes","alex","einar"));
			appoint.setData(calendar);
			appoint.addAppointmentToDay();
			SceneHandler sh = new SceneHandler();
			sh.popUpMessage("/messages/Info.fxml", 300, 150, "Your appointment has been saved");
>>>>>>> d4e7547dfaf8cd72a33c5368c1d0686930daafb9
			// get a handle to the stage
			Stage stage = (Stage) saveButton.getScene().getWindow();
			// do what you got to do :)
			stage.close();
		}
	}

	@FXML
	private void checkCapasity(){
		String value = capasityField.textProperty().getValue();
		if( !value.matches("\\d+") ){
			capasityField.setPromptText("Invalid");
			capasityField.setText("1");
		}

	}

	// methods for handling input in time fields
	@FXML
	private void fromTime(){
		if(!fromField.textProperty().getValue().matches("\\d\\d:\\d\\d") && !fromField.textProperty().getValue().matches("\\d\\d") ){
			fromField.setText(LocalTime.now().getHour() +":" + LocalTime.now().getMinute());
		}else if(fromField.textProperty().getValue().matches("\\d\\d")){

			fromField.setText(fromField.getText() + ":00");
		}
		if(!validFromTime()){
			fromField.setText(LocalTime.now().getHour() +":" + LocalTime.now().getMinute());

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
		toDate.setValue(fromDate.getValue());
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
		descriptionField.setPromptText("Appointment Description...");

		fromField.setText(LocalTime.now().getHour() + 1+":00" );
		toField.setText(LocalTime.now().getHour() + 2 + ":00" );


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
				capasityField.textProperty().getValue() != ""
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
	}

	@Override
	public Calendar getData() {
		return this.calendar;
	}

	@Override
	public void setData(Calendar c, Appointment a) {
		if (c != null) {
			if (a != null) {
				this.appoint = a;
				descriptionField.setPromptText(a.getDescription());

				fromField.setText(a.getStartTime().toString());
				toField.setText(a.getEndTime().toString());

				capasityField.textProperty().setValue("" + a.getParticipants().size());

				fromDate.setValue(a.getStartDate());
				disableDates(fromDate, LocalDate.now());
				toDate.setValue(a.getEndDate());
				disableDates(toDate, LocalDate.now());
			}
			this.calendar = c;
		}
	}



<<<<<<< HEAD
}
=======
}
>>>>>>> d4e7547dfaf8cd72a33c5368c1d0686930daafb9

package newAppointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Calendar;

import newAppointment.NewAppointmentManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;

public class NewAppointmentController {
	
	NewAppointmentManager appoint = new NewAppointmentManager();

	Calendar currentDate;
	
	// rigging up fxml variables
	
	@FXML TextField descriptionField;
	@FXML TextField fromField;
	@FXML TextField toField;
	@FXML TextField capasityField;
	@FXML DatePicker fromDate;
	@FXML DatePicker toDate;
	@FXML TextField otherField;
	@FXML Button cancelButton;
	
	
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
			appoint.setEndDate(toDate.getValue());
			appoint.setStartDate(fromDate.getValue());
			appoint.setRoom("NTNU"); // this needs fixing:)
			appoint.setStartTime(fromField.textProperty().getValue());
			appoint.setEndTime(toField.textProperty().getValue());
			appoint.setParticipants(Arrays.asList("birk","terje","johannes","alex","einar"));
			appoint.printData();
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
			fromField.setText(currentDate.getTime().getHours() +":" + currentDate.getTime().getMinutes());
		}else if(fromField.textProperty().getValue().matches("\\d\\d")){
			
			fromField.setText(fromField.getText() + ":00");
		}
		if(!validFromTime()){
			fromField.setText(currentDate.getTime().getHours() +":" + currentDate.getTime().getMinutes());
			
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
		
		
		
		if(currentDate.getTime().getHours()<fraTidTime){
			//appoint.setFra(LocalTime.of(fraTidTime,fraTidMin));
			return true;
			
			
		}else if(currentDate.getTime().getHours() == fraTidTime && currentDate.getTime().getMinutes()<fraTidMin){
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
		currentDate = Calendar.getInstance();
		descriptionField.setPromptText("Appointment Description...");
		
		fromField.setText(currentDate.getTime().getHours() + 1+":00" );
		toField.setText(currentDate.getTime().getHours() + 2 + ":00" );
		
		
		
		
		fromDate.setValue(LocalDate.now());
		disableDates(fromDate, LocalDate.now());
		toDate.setValue(LocalDate.now());
		disableDates(toDate, LocalDate.now());
		
		
	}
	
	// method for checking if necesary fields are filled out
	public boolean checkFieldsFill(){
		System.out.println(capasityField.textProperty().getValue() );
		if(descriptionField.textProperty().getName() != "" &&
				toField.textProperty().getValue() != "" &&
				fromField.textProperty().getValue() != "" &&
				capasityField.textProperty().getValue() != ""
				){
			return true;
		}
		return false;
	}
	
	
	
}

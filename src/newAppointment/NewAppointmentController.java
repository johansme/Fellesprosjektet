package newAppointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;

import newAppointment.NewAppointmentManager;
import javafx.fxml.FXML;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.Callback;

public class NewAppointmentController {
	
	NewAppointmentManager appoint = new NewAppointmentManager();

	Calendar currentDate;
	
	// Sette opp variabler for fxml elementer:
	
	@FXML TextField formalField;
	@FXML TextField fraField;
	@FXML TextField tilField;
	@FXML TextField antallPersField;
	@FXML DatePicker fraDato;
	@FXML DatePicker tilDato;
	@FXML TextField capasityField;
	@FXML TextField otherField;
	
	
	@FXML
	private void description(){
		/* System.out.println("YOLO"); */
		//SMEGMABRO
		//BiiiiiiRkyy
	}
	
	@FXML
	private void checkCapasity(){
		String value = capasityField.textProperty().getValue();
		if( !value.matches("\\d+") ){
			capasityField.setPromptText("Invalid");
			capasityField.setText("1");
		}else{
			//lagre kapasitet:D
			//bruk kapasitet til a foresla rom
			
		}
		
	}
	
	// handtering av til og fra tidfeltet
	@FXML
	private void fraTid(){
		if(!fraField.textProperty().getValue().matches("\\d\\d:\\d\\d") && !fraField.textProperty().getValue().matches("\\d\\d") ){
			fraField.setText(currentDate.getTime().getHours() +":" + currentDate.getTime().getMinutes());
		}else if(fraField.textProperty().getValue().matches("\\d\\d")){
			
			fraField.setText(fraField.getText() + ":00");
		}
		if(!validFraTime()){
			fraField.setText(currentDate.getTime().getHours() +":" + currentDate.getTime().getMinutes());
			
		}
		
	}
	@FXML
	private void tilTid(){
		String value = tilField.textProperty().getValue();
		if( !value.matches("\\d\\d:\\d\\d") && !tilField.textProperty().getValue().matches("\\d\\d") ){
			tilField.setText(fraField.textProperty().getValue());
			tilField.setPromptText("Ugyldig");
		}else if(value.matches("\\d\\d")){
			
			tilField.setText(tilField.getText() + ":00");
		}
		if(!validTilTime()){
			tilField.setText(fraField.textProperty().getValue());
			
		}
		
	}
	
	//validering av tid
	
private boolean validTilTime(){
		
		//konvertere string til egnet format til tid handtering
	
		String tilTid = tilField.textProperty().getValue();
		String fraTid = fraField.textProperty().getValue();
		int tilTidTime = Integer.parseInt(tilTid.split(":")[0]);
		int fraTidTime = Integer.parseInt(fraTid.split(":")[0]);
		int tilTidMin = Integer.parseInt(tilTid.split(":")[1]);
		int fraTidMin = Integer.parseInt(fraTid.split(":")[1]);
		
		// logiske tester for a sjekke om tiltid er gyldig
		
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
		
	
	private boolean validFraTime(){
		
		//konvertere string til egnet format til tid handtering

		String fraTid = fraField.textProperty().getValue();
		int fraTidTime = Integer.parseInt(fraTid.split(":")[0]);
		int fraTidMin = Integer.parseInt(fraTid.split(":")[1]);

		// logiske tester for a sjekke om fratid er gyldig
		
		if(fraDato.getValue().toString().equals( LocalDate.now().toString())){
		
		
		
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
	
	
	//dato handtering:
	@FXML
	private void fraDato(){
		
		disableDates(tilDato, fraDato.getValue());
		tilDato.setValue(fraDato.getValue());
		//appoint.setDato(FraDato.getValue());
	}
	
	
	//Insane kode for a disable datoer som ikkje sees pa som gyldige :D
	
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
	
	
	// seette start verdier slik at brukeren letter forstar hva han skal gjore:
	// samt gjore innfylling meir effektivt
	
	@FXML
	public void initialize(){
		currentDate = Calendar.getInstance();
		formalField.setPromptText("Appointment Description...");
		
		fraField.setPromptText(currentDate.getTime().getHours() + 1+":00" );
		tilField.setPromptText(currentDate.getTime().getHours() + 2 + ":00" );
		
		
		
		
		fraDato.setValue(LocalDate.now());
		disableDates(fraDato, LocalDate.now());
		tilDato.setValue(LocalDate.now());
		disableDates(tilDato, LocalDate.now());
		
		
	}
	
	
	
}

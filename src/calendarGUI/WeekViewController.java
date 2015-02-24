package calendarGUI;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class WeekViewController extends Application {

	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(this.getClass().getResource("WeekView.fxml/"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private Button prev;
	
	@FXML
	public void prevAction() {
		
	}
	
	@FXML
	private Button next;
	
	@FXML
	public void nextAction() {
		
	}
	
	@FXML
	private Button back;
	
	@FXML
	public void backAction() {
		
	}
	
	@FXML
	private Button newAppointment;
	
	@FXML
	public void newAction() {
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}



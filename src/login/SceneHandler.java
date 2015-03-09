package login;

import calendar.Appointment;
import calendar.Calendar;
import calendarGUI.ControllerInterface;
import messages.MessageController;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneHandler {

	
	public void changeScene(String fxmlPath , Event e){
		
		try{
			Node node=(Node) e.getSource();
			  Stage stage=(Stage) node.getScene().getWindow();
			  Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
			  Scene scene = new Scene(root);
			  stage.setScene(scene);
			  stage.centerOnScreen();
			  stage.show();
			 
			} catch(Exception er) {
				er.printStackTrace();
			}
		
	}
	
	public void changeMonthRelatedScene(Event e, String fxmlPath, int width, int height, Calendar calendar){
		try{
			Node node=(Node) e.getSource();
			Stage stage=(Stage) node.getScene().getWindow();
			FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
			Parent root = loader.load();
			Scene scene = new Scene(root,width,height);
			ControllerInterface monthController = (ControllerInterface) loader.getController();
			monthController.setData(calendar);
			  stage.setScene(scene);
			  stage.centerOnScreen();
			  stage.show();
			  
			} catch(Exception er) {
				er.printStackTrace();
			}
		
		
	}
	
	public void changeAppointmentRelatedScene(String fxmlPath, int width, int height, Calendar calendar, Appointment appointment){
		Stage primaryStage = new Stage();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
			Parent root = loader.load();
			Scene scene = new Scene(root,width,height);
			ControllerInterface monthController = (ControllerInterface) loader.getController();
			monthController.setData(calendar, appointment);
			primaryStage.setScene(scene);
			primaryStage.centerOnScreen();
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	
	public void popUpScene(String fxmlPath, int width,int height, Calendar cal, Appointment a){
		Stage primaryStage = new Stage();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
			Parent root = loader.load();
			Scene scene = new Scene(root,width,height);
			ControllerInterface controll = loader.getController();
			controll.setData(cal, a);
			primaryStage.setScene(scene);
			primaryStage.centerOnScreen();
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void popUpMessage(String fxmlPath, int width,int height,String message, ControllerInterface contrInt ){
		Stage primaryStage = new Stage();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
			Parent root = loader.load();
			Scene scene = new Scene(root,width,height);
			MessageController messageController = (MessageController) loader.getController();
			messageController.setMessage(message);
			messageController.setReferenceController(contrInt);
			primaryStage.setScene(scene);
			primaryStage.centerOnScreen();
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
		
	
	
}

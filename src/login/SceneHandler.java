package login;

import newAppointment.AddParticipantsController;
import calendar.Appointment;
import calendar.Calendar;
import calendar.User;
import calendarGUI.ControllerInterface;
import calendarGUI.EditUserPasswordController;
import calendarGUI.ParticipantController;
import messages.MessageController;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SceneHandler {

	LoginController login;  
	public void changeScene(String fxmlPath , Event e){
		
		try{
			Node node=(Node) e.getSource();
			  Stage stage=(Stage) node.getScene().getWindow();
			  Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
			  Scene scene = new Scene(root);
			  stage.getIcons().add(new Image(this.getClass().getResourceAsStream("appointment_icon.png")));
			  stage.setScene(scene);
			  stage.centerOnScreen();
			  stage.titleProperty().setValue("BETJA Systems");
			  stage.setResizable(false);
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
			stage.getIcons().add(new Image(this.getClass().getResourceAsStream("appointment_icon.png")));
			stage.hide();
			stage.setScene(scene);
			stage.centerOnScreen();
			stage.titleProperty().setValue("BETJA Systems | Logged in as: " + calendar.getLoggedInUser().getName() + " " +calendar.getLoggedInUser().getSurname());
			stage.setResizable(false);
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
			primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("appointment_icon.png")));
			primaryStage.initModality(Modality.APPLICATION_MODAL);
			primaryStage.setScene(scene);
			primaryStage.centerOnScreen();
			primaryStage.titleProperty().setValue("BETJA Systems");
			primaryStage.setResizable(false);
			primaryStage.show();
			
		} catch(Exception e) 
		{
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
			primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("appointment_icon.png")));
			primaryStage.initModality(Modality.APPLICATION_MODAL);
			primaryStage.setScene(scene);
			primaryStage.centerOnScreen();
			primaryStage.titleProperty().setValue("BETJA Systems");
			primaryStage.setResizable(false);
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
			primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("appointment_icon.png")));
			primaryStage.initModality(Modality.APPLICATION_MODAL);
			primaryStage.setScene(scene);
			primaryStage.centerOnScreen();
			primaryStage.titleProperty().setValue("BETJA Systems");
			primaryStage.setResizable(false);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	// for use when admin requests to change a users password:
	public void popUpChangePassword(String fxmlPath, int width,int height, User usr, Calendar calendar){
		
		Stage primaryStage = new Stage();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
			Parent root = loader.load();
			Scene scene = new Scene(root,width,height);
			EditUserPasswordController passController = (EditUserPasswordController) loader.getController();
			passController.setUser(usr);
			passController.setData(calendar);
			primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("appointment_icon.png")));
			primaryStage.initModality(Modality.APPLICATION_MODAL);
			primaryStage.setScene(scene);
			primaryStage.centerOnScreen();
			primaryStage.titleProperty().setValue("BETJA Systems");
			primaryStage.setResizable(false);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	// for use when adding participants in NewAppointmentController
	public void popUpParticipants(String fxmlPath, int width,int height,Calendar calendar, ParticipantController controller ){
		Stage primaryStage = new Stage();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
			Parent root = loader.load();
			Scene scene = new Scene(root,width,height);
			AddParticipantsController addPartController = (AddParticipantsController) loader.getController();
			addPartController.setParticipantController(controller);
			addPartController.setData(calendar);
			primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("appointment_icon.png")));
			primaryStage.initModality(Modality.APPLICATION_MODAL);
			primaryStage.setScene(scene);
			primaryStage.centerOnScreen();
			primaryStage.titleProperty().setValue("BETJA Systems");
			primaryStage.setResizable(false);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}

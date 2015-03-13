package login;

import newAppointment.ReceiveRoom;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;


public class LoginMain extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = (Parent)FXMLLoader.load(getClass().getResource("/login/loginScreen.fxml"));
			Scene scene = new Scene(root,307,258);
			primaryStage.setScene(scene);
			primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("appointment_icon.png")));
			primaryStage.titleProperty().setValue("BETJA Systems");
			primaryStage.show();
		} catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new ReceiveRoom();
		launch(args);
	}
}

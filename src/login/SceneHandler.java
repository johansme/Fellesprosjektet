package login;

import messages.ConfirmController;
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
			  stage.show();
			} catch(Exception er) {
				er.printStackTrace();
			}
		
	}
	
	public void popUpScene(String fxmlPath, int width,int height){
		Stage primaryStage = new Stage();
		try {
			Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
			Scene scene = new Scene(root,width,height);
		
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void popUpConfirmation(String fxmlPath, int width,int height,String message){
		Stage primaryStage = new Stage();
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			Parent root = fxmlLoader.load(getClass().getResource(fxmlPath));
			Scene scene = new Scene(root,width,height);
			ConfirmController confirmController = (ConfirmController) fxmlLoader.getController();
			confirmController.setMessage(message);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
		
	
	
}

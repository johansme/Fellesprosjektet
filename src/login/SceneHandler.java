package login;

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
	
	public void popUpScene(String fxmlPath){
		Stage primaryStage = new Stage();
		try {
			Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
			Scene scene = new Scene(root,800,600);
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
		
	
	
}

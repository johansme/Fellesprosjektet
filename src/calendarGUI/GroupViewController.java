package calendarGUI;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

public class GroupViewController {

	@FXML
	private ListView<HBox> groupList;
	
	@FXML
	private void initialize(){
		
	List<String> groupies = new ArrayList<String>();
	for( int i = 0; i <100;i++){
		groupies.add("Abakus linjeforening" + i + "  ");
		
	}
	
	
	//List<User> partpts = appointment.getUsers();
	for (String g : groupies) {
		//TODO
		HBox line = new HBox();
		line.setPrefWidth(100);
		Label groupLabel = new Label();
		
		groupLabel.wrapTextProperty().set(true);
		
		groupLabel.setText(g);
		CheckBox checkBox = new CheckBox();
		checkBox.setSelected(false);
		groupLabel.setPrefWidth(line.getPrefWidth()-checkBox.getWidth());
		line.getChildren().addAll(groupLabel, checkBox);
		
		checkBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				if (checkBox.isSelected()) {
					//show group appointments
				}
				else {
					// do not show group appointments
				}
			}
		});
		groupList.getItems().add(line);
	
	}
	}
	
}

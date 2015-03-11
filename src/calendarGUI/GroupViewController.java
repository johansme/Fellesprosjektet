package calendarGUI;

import java.util.ArrayList;
import java.util.List;


import calendar.Group;
import calendar.User;

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
	
	
	private List<Integer> groupIDs = new ArrayList<Integer>(); 
	
	private void refreshGroups(List<Integer> groups){
		//flush current AppointmentList
		//get new List;
		for (Integer i : groupIDs) {
			System.out.println(i);
		}
		
	}
	
	@FXML
	private void initialize(){
		
	List<Group> groupies = new ArrayList<Group>();
	for( int i = 0; i <10;i++){
		Group g = new Group(i,null, null, "group: " +i);
		
		groupies.add( g);
		
	}
	
	
	//List<User> partpts = appointment.getUsers();
	for (Group g : groupies) {
		//TODO
		groupIDs.add(g.getId());
		HBox line = new HBox();
		line.setPrefWidth(100);
		Label groupLabel = new Label();
		
		groupLabel.wrapTextProperty().set(true);
		
		groupLabel.setText(g.getName());
		CheckBox checkBox = new CheckBox();
		
		checkBox.setSelected(true);
		
		groupLabel.setPrefWidth(line.getPrefWidth()-checkBox.getWidth());
		line.getChildren().addAll(groupLabel, checkBox);
		
		checkBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				if (checkBox.isSelected()) {
					//Request group Appointments
					groupIDs.add(g.getId());
					refreshGroups(groupIDs);
					
				}
				else {
					// remove focused groupID
					groupIDs.remove(g.getId());
					refreshGroups(groupIDs);
					
				}
			}
		});
		groupList.getItems().add(line);
	
	}
	}
	
}

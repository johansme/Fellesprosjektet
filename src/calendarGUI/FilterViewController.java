package calendarGUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import api.SyncFromServer;
import calendar.Calendar;
import calendar.Group;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class FilterViewController {

	@FXML
	private ListView<HBox> groupList;

	@FXML
	private Pane screen;
	
	private Calendar calendar;
	private HashMap<CheckBox, Group> groups = new HashMap<CheckBox, Group>(); 
	private ControllerInterface parentController;

	public void setData(Calendar c){
		calendar = c;
		createGroupPanel();
		
	}

	public void setParent(ControllerInterface p) {
		parentController=p;
	}

	private void createGroupPanel(){
		
		try {
			SyncFromServer.getGroups(calendar);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		groupList.getItems().clear();
		
		List<Group> groupies = new ArrayList<Group>();
		
		if(calendar.getLoggedInUser().getGroups() != null){
		groupies = calendar.getLoggedInUser().getGroups();
		
		}



		if (groupies!=null && !groupies.isEmpty()) {
			for (Group g : groupies) {
				HBox line = new HBox();
				line.setPrefWidth(100);
				Label groupLabel = new Label();

				groupLabel.wrapTextProperty().set(true);
				groupLabel.setFocusTraversable(false);

				groupLabel.setText(g.getName());
				CheckBox checkBox = new CheckBox();
				groups.put(checkBox, g);
				checkBox.setSelected(true);
				
				checkBox.setFocusTraversable(false);

				groupLabel.setPrefWidth(line.getPrefWidth()-checkBox.getWidth());
				line.getChildren().addAll(groupLabel, checkBox);
				line.setFocusTraversable(false);

				checkBox.setOnAction(new EventHandler<ActionEvent>() {
					@Override public void handle(ActionEvent e) {
						
						if (checkBox.isSelected()) {
							
							//groups.get(checkBox).setActive(true);
							//parentController.setData(calendar);
						}
						else {
							
							
							//groups.get(checkBox).setActive(false);
							//parentController.setData(calendar);
						}
					}
				});
				groupList.getItems().add(line);
			}

		}
		else {
			groupList.getItems().clear();
			Label label = new Label("No groups");
			label.setFocusTraversable(false);
			HBox line = new HBox();
			line.setFocusTraversable(false);
			line.setPrefWidth(100);
			line.getChildren().add(label);
			groupList.getItems().add(line);
		}
	}

	@FXML
	private void initialize(){
		//enable when ready:D	

		

	}

}

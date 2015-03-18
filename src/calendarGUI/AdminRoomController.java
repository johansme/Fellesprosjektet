package calendarGUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import api.API;
import newAppointment.Room;
import calendar.Appointment;
import calendar.Calendar;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import login.SceneHandler;

public class AdminRoomController implements ControllerInterface {

	@FXML private TextField roomNameField;
	@FXML private TextField capacityField;
	@FXML private Button addRoomButton;
	@FXML private Button closeButton;

	@FXML private ListView<HBox> roomListView;
	private List<Room> roomList = new ArrayList<Room>();

	private Calendar calendar;
	private SceneHandler sceneHandler = new SceneHandler();

	private AdminRoomController thisController = this;

	@FXML
	private void addRoom(){
		if (isValidRoom()) {
			Room room = new Room();
			room.setName(roomNameField.getText());
			room.setCapacity(Integer.valueOf(capacityField.getText()));
			JSONObject obj = new JSONObject();
			obj.put("command", "create");
			obj.put("room", room.toJSON());
			try {
				API.call("/room", obj, calendar.getSession());
				//room.setId(res.getInt("rid"));
				roomList.add(room);
				createRoomElement(room);
			} catch (IOException e) {
				sceneHandler.popUpMessage("/messages/Error.fxml", 290, 140, "Something went wrong. Please try again.", this);
			}
		} else {
			sceneHandler.popUpMessage("/messages/Error.fxml", 290, 140, "Invalid room data", this);
		}
	}

	@FXML
	private void closeButtonPressed() {
		// get a handle to the stage
		Stage stage = (Stage)closeButton.getScene().getWindow();
		stage.close();
	}

	private boolean isValidRoom() {
		if (capacityField.getText().length() < 1 || roomNameField.getText().length() < 2) {
			return false;
		}
		if (! capacityField.getText().matches("[0-9]+") || capacityField.getText().length() > 5) {
			return false;
		}
		if (! roomNameField.getText().matches("[a-zA-Z0-9 _\\-]+") || roomNameField.getText().length() > 17) {
			return false;
		}
		return true;
	}

	private void createRoomListView(){
		if (roomList!=null && !roomList.isEmpty()) {
			for (Room room : roomList) {
				createRoomElement(room);
			}
		}
	}
	
	private void createRoomElement(Room room) {
		HBox line = new HBox();
		line.setPrefWidth((int)(roomListView.getPrefWidth()*0.90));
		
		
		//label for the line:
		Label userLabel = new Label();
		userLabel.wrapTextProperty().set(true);
		userLabel.setFocusTraversable(false);
		userLabel.setText(room.getName() +" Capasity: " + room.getCapacity());
		
		//button to delete room:
		Button deleteRoom = new Button();
		deleteRoom.setPrefWidth((int)(roomListView.getPrefWidth()*0.1));
		deleteRoom.setText("X");
		deleteRoom.setTextFill(new Color(1, 0, 0, 1));
		
		
		deleteRoom.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				delLine = line;
				sceneHandler.popUpMessage("/messages/Confirm.fxml", 290, 140, "Are you sure you want to delete?", thisController);
			}
		});
		
		userLabel.setPrefWidth(line.getPrefWidth()-deleteRoom.getPrefWidth());
		line.getChildren().addAll(userLabel, deleteRoom);
		line.setFocusTraversable(false);
		
		
		
		roomListView.getItems().add(line);
		
	}

	private void setRoomList() {
		JSONObject obj = new JSONObject();
		obj.put("command", "get_all");
		try {

			JSONObject res = API.call("/rooms", obj, calendar.getSession());
			JSONArray resArray = res.getJSONArray("rooms");
			for (int i = 0; i < resArray.length(); i++) {
				Room room = new Room();
				JSONObject userObj = (JSONObject) resArray.get(i);
				room.fromJSON(userObj);
				roomList.add(room);
			}
			createRoomListView();
		} catch (IOException e) {
			System.out.println(e.toString());
		}
	}

	@Override
	public void setData(Calendar calendar) {
		if (calendar != null) {
			this.calendar = calendar;
			setRoomList();
		}
	}

	@Override
	public Calendar getData() {
		return calendar;
	}

	@Override
	public void setData(Calendar c, Appointment a) {
		if (c != null) {
			calendar = c;
			setRoomList();
		}
	}

	@Override
	public void setFeedback() {
		delRoom();
	}
	
	private HBox delLine;
	
	private void delRoom() {
		int index =roomListView.getItems().indexOf(delLine);
		Room delRoom = roomList.get(index);
		JSONObject obj = new JSONObject();
		obj.put("command", "remove");
		obj.put("rid", delRoom.getId());
		try {
			API.call("/room", obj, calendar.getSession());
			roomList.remove(index);
			roomListView.getItems().remove(delLine);
		} catch (IOException e1) {
			sceneHandler.popUpMessage("/messages/Error.fxml", 290, 140, "Something went wrong. Please try again.", thisController);
		}
	}
	
}

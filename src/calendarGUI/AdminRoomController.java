package calendarGUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import shared.Room;
import api.API;
import newAppointment.Tuple;
import calendar.Appointment;
import calendar.Calendar;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import login.SceneHandler;

public class AdminRoomController implements ControllerInterface {

	@FXML private TextField roomNameField;
	@FXML private TextField capacityField;
	@FXML private Button addRoomButton;
	@FXML private Button closeButton;

	@FXML private ListView<Tuple> roomListView;
	private List<Tuple> roomList = new ArrayList<Tuple>();

	private Calendar calendar;
	private SceneHandler sceneHandler = new SceneHandler();

	@FXML
	private void addRoom(){
		if (isValidRoom()) {
//			JSONObject obj = new JSONObject();
//			obj.put("command", "create");
//			obj.put("room", room);
//			try {
//				JSONObject res = API.call("/room", obj, calendar.getSession());
//			} catch (IOException e) {
//			}
			//TODO
		} else {
			sceneHandler.popUpMessage("/messages/Error.fxml", 300, 150, "Invalid room data", this);
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
		if (! roomNameField.getText().matches("[a-zA-Z0-9 -_]+") || roomNameField.getText().length() > 50) {
			return false;
		}
		return true;
	}

	private void setRoomList() {
		JSONObject obj = new JSONObject();
		obj.put("command", "get_all");
		//		try {
		//			JSONObject res = API.call("/room", obj, calendar.getSession());
		//			JSONArray resArray = res.getJSONArray("rooms");
		//			for (int i = 0; i < resArray.length(); i++) {
		//				 room = new ;
		//				JSONObject userObj = (JSONObject) resArray.get(i);
		//				room.fromJSON(userObj);
		//				roomList.add(room);
		//				//TODO add to listView, incl. delete
		//			}
		//		} catch (IOException e) {
		//		}
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

	}
}

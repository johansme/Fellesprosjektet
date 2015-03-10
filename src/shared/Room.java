package shared;

import org.json.JSONException;
import org.json.JSONObject;

public class Room {
	protected int id = 0;
	protected String name = null;
	protected int capacity = 0;
	
	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		try {
		obj.put("id", id);
		obj.put("name", name);
		obj.put("capacity", capacity);
		} catch(JSONException e) { return null; }
		return obj;
	}
	
	public static Room fromJSON(JSONObject obj) {
		Room room = new Room();
		try {
			room.id = obj.getInt("id");
			room.name = obj.getString("name");
			room.capacity = obj.getInt("capacity");
		} catch(JSONException e) { return null; }
		return room;
	}
}

package shared;

import org.json.JSONException;
import org.json.JSONObject;

public class Room {
	protected int id = 0;
	protected String name = null;
	protected int capacity = 0;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		try {
		obj.put("id", id);
		obj.put("name", name);
		obj.put("capacity", capacity);
		} catch(JSONException e) { return null; }
		return obj;
	}
	
	public boolean fromJSON(JSONObject obj) {
		try {
			id = obj.getInt("id");
			name = obj.getString("name");
			capacity = obj.getInt("capacity");
		} catch(JSONException e) { return false; }
		return true;
	}
}

package shared;

import org.json.JSONException;
import org.json.JSONObject;

public class Group {
	private int id = 0;
	private int parent = 0;
	private int createdBy = 0;
	private String name = null;
	
	public Group() {
		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("id", id);
			obj.put("name", name);
			obj.put("parent", parent);
			obj.put("createdby", createdBy);
		} catch(JSONException e) { return null; }
		return obj;
	}

	public boolean fromJSON(JSONObject obj) {
		try {
			id = obj.getInt("id");
			name = obj.getString("name");
			parent = obj.getInt("parent");
			createdBy = obj.getInt("createdby");
		} catch(JSONException e) { return false; }
		return true;
	}
}

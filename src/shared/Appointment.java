package shared;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class Appointment {
	protected int id = 0;
	protected int creator = 0;
	protected String description = null;
	protected String location = null;
	protected Date start = null;
	protected Date end = null;
	protected Date modified = null;

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getCreator() {
		return creator;
	}
	
	public void setCreator(int creator) {
		this.creator = creator; 
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public Date getModified() {
		return modified;
	}
	
	public void setModified(Date modified) {
		this.modified = modified;
	}
	
	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("id", id);
			obj.put("creator", creator);
			obj.put("description", description);
			obj.put("location", location);
			obj.put("start", start.getTime());
			obj.put("end", end.getTime());
			obj.put("modified", modified.getTime());
		} catch(JSONException e) { return null; }
		return obj;
	}
	
	public boolean fromJSON(JSONObject obj) {
		try {
			id = obj.getInt("id");
			creator = obj.getInt("creator");
			description = obj.getString("description");
			location = obj.getString("location");
			start = new Date(obj.getLong("start"));
			end = new Date(obj.getLong("end"));
			modified = new Date(obj.getLong("modified"));
			
		} catch(JSONException e) { return false; }
		return true;
	}
}

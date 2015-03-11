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
	
	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("id", id);
			obj.put("description", description);
			obj.put("location", location);
			obj.put("start", start.getTime());
			obj.put("end", end.getTime());
			obj.put("modified", modified.getTime());
		} catch(JSONException e) { return null; }
		return obj;
	}
	
	public static Appointment fromJSON(JSONObject obj) {
		Appointment app = new Appointment();
		try {
			app.id = obj.getInt("id");
			app.description = obj.getString("description");
			app.location = obj.getString("location");
			app.start = new Date(obj.getInt("start"));
			app.end = new Date(obj.getInt("end"));
			app.modified = new Date(obj.getInt("modified"));
			
		} catch(JSONException e) { return null; }
		return app;
	}
}

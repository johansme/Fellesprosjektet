package shared;

import java.util.Date;

import org.json.JSONObject;

public class Appointment {
	private int id = 0;
	private User creator = null;
	private String description = null;
	private String location = null;
	private Date start = null;
	private Date end = null;
	private Date modified = null;

	public int getId() {
		return id;
	}
	
	public User getCreator() {
		return creator;
	}
	
	public void setCreator(User creator) {
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
		obj.put("description", description);
		obj.put("location", location);
		obj.put("start", start.toString());
		obj.put("end", end.toString());
		obj.put("modified", modified.toString());
		return obj;
	}
}
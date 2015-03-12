package shared;

import org.json.JSONException;
import org.json.JSONObject;

public class Invitation {
	protected int aid = 0;
	protected boolean accepted = false;
	protected boolean hidden = false;
	protected boolean dirty = false;
	
	public int getAid() {
		return aid;
	}
	public void setAid(int aid) {
		this.aid = aid;
	}
	public boolean isAccepted() {
		return accepted;
	}
	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}
	public boolean isHidden() {
		return hidden;
	}
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	public boolean isDirty() {
		return dirty;
	}
	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}
	
	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("aid", aid);
			obj.put("accepted", accepted);
			obj.put("hidden", hidden);
			obj.put("dirty", dirty);
		} catch(JSONException e) { return null; }
		return obj;
	}
	
	public boolean fromJSON(JSONObject obj) {
		try {
			aid = obj.getInt("aid");
			accepted = obj.getBoolean("accepted");
			hidden = obj.getBoolean("hidden");
			dirty = obj.getBoolean("dirty");
			
		} catch(JSONException e) { return false; }
		return true;
	}
}

package calendar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import api.API;

public class Group extends shared.Group implements Participant {
	
	private List<Participant> members;
	private User admin;
	private boolean active;
	private Calendar calendar;
	
	public Group(int id, List<Participant> memberList, User a, String n) {
		super();
		members = new ArrayList<Participant>();
		super.setId(id);
		setMembers(memberList);
		admin = a;
		super.setName(n);
	}

	public Group() {
		super();
	}

	public List<Participant> getMembers() {
		return members;
	}

	public void setMembers(List<Participant> memberList) {
		if(memberList != null){
		for (Participant participant: memberList) {
			if (participant != null && ! this.members.contains(participant)) {
				this.members.add(participant);
			}
		}
		}
	}
	
	private void setAdmin(int uid) {
		JSONObject obj = new JSONObject();
		obj.put("command", "get_by_id");
		obj.put("uid", uid);
		try {
			JSONObject res = API.call("/user", obj, calendar.getSession());
			User adm = new User();
			adm.fromJSON(res);
			this.admin = adm;
		} catch (IOException e) {
		}
	}

	public User getAdmin() {
		return admin;
	}

	public void addMember(Participant participant) {
		if (participant != null && ! members.contains(participant)) {
			members.add(participant);
		}
	}
	
	public void removeMember(Participant participant) {
		if (members.contains(participant)) {
			members.remove(participant);
		}
	}
	
	public void setData(Calendar calendar) {
		if (calendar != null) {
			this.calendar = calendar;
		}
	}
	
	@Override
	public String toString() {
		return getName();
	}
	
	public void setActive(boolean b) {
		active = b;
	}
	
	public boolean getActive() {
		return active;
	}
	
	@Override
	public boolean fromJSON(JSONObject obj) {
		boolean res = super.fromJSON(obj);
		setAdmin(getCreatedBy());
		return res;
	}

}

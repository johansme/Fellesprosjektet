package calendar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
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
		members = new ArrayList<Participant>();
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
	
	private boolean setAdmin(int uid) {
		JSONObject obj = new JSONObject();
		obj.put("command", "get_by_id");
		obj.put("uid", uid);
		try {
			JSONObject res = API.call("/user", obj, calendar.getSession());
			User adm = new User();
			adm.fromJSON(res.getJSONObject("user"));
			this.admin = adm;
			return true;
		} catch (IOException e) {
		}
		return false;
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
	
	public void setMembers() {
		JSONObject obj = new JSONObject();
		obj.put("command", "get_users");
		obj.put("gid", super.getId());
		try {
			JSONObject res = API.call("/group", obj, calendar.getSession());
			JSONArray arr = res.getJSONArray("gids"); //TODO check if this is correct
			for (int i = 0; i < arr.length(); i++) {
				int uid = arr.getInt(i);
				obj = new JSONObject();
				obj.put("command", "get_by_id");
				obj.put("uid", uid);
				res = API.call("/user", obj, calendar.getSession());
				User user = new User();
				user.fromJSON(res.getJSONObject("user"));
				members.add(user);
			}
		} catch (IOException e) {
		}
		obj = new JSONObject();
		obj.put("command", "get_children");
		obj.put("gid", super.getId());
		try {
			JSONObject res = API.call("/group", obj, calendar.getSession());
			JSONArray arr = res.getJSONArray("gids");
			obj = new JSONObject();
			obj.put("command", "get");
			obj.put("gid", arr);
			res = API.call("/group", obj, calendar.getSession());
			arr = res.getJSONArray("groups");
			for (int i = 0; i < arr.length(); i++) {
				Group group = new Group();
				group.setData(calendar);
				JSONObject gr = arr.getJSONObject(i);
				group.fromJSON(gr);
				members.add(group);
			}
		} catch (IOException e) {
		}
	}
	
	@Override
	public boolean fromJSON(JSONObject obj) {
		boolean res = super.fromJSON(obj);
		res = res && setAdmin(getCreatedBy());
		return res;
	}

}

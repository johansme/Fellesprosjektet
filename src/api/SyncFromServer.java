package api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import shared.Invitation;
import calendar.Appointment;
import calendar.Calendar;
import calendar.Group;
import calendar.Participant;

public class SyncFromServer {
	
	
	public static void sync(Calendar c) throws IOException {
		getGroups(c);
//		getAppointments(c);
	}
	
	public static void getGroups(Calendar c) throws IOException {
		JSONObject o = new JSONObject();
		if (c.getLoggedInUser().isAdmin()) {
			o.put("command", "get_all");
		} else {
			o.put("command", "get_from_user");
			o.put("uid", c.getLoggedInUser().getId());
		}
		
		JSONObject res = API.call("/group", o, c.getSession());
		if (! c.getLoggedInUser().isAdmin()) {
			JSONArray gidArray = res.getJSONArray("gid");
			o = new JSONObject();
			o.put("command", "get");
			o.put("gid", gidArray);
			
			res = API.call("/group", o, c.getSession());
		}
		JSONArray groupArray = res.getJSONArray("groups");
		List<Group> g = new ArrayList<Group>();
		for (int i=0; i<groupArray.length(); i++) {
			Group gr = new Group();
			gr.setData(c);
			gr.fromJSON((JSONObject) groupArray.get(i));
			gr.setMembers();
			g.add(gr);
		}
		c.getLoggedInUser().setGroups(g);		
	}
	
	public static void getAppointments(Calendar c) throws IOException {
		JSONObject o;
		List<Group> g = c.getLoggedInUser().getGroups();
		JSONObject res;
		List<Appointment> a = new ArrayList<Appointment>();
		List<Invitation> in = new ArrayList<Invitation>();
		for (Group gr : g) {
			o = new JSONObject();
			o.put("command", "get_all_for_group");
			o.put("gid", gr.getId());
			res = new JSONObject();
			res = API.call("/invitation", o, c.getSession());
			JSONArray invArray1= res.getJSONArray("invitations");
			for (int i=0; i<invArray1.length(); i++) {
				JSONObject obj = new JSONObject();
				obj = invArray1.getJSONObject(i);
			}
			for (int i=0; i<invArray1.length(); i++) {
				JSONObject obj = new JSONObject();
				obj = invArray1.getJSONObject(i);
				Appointment ap = new Appointment(c);
				ap.fromJSON(obj.getJSONObject("app"));
				Invitation inv = new Invitation();
				inv.fromJSON(obj.getJSONObject("invitation"));
				if (!a.contains(ap)) {
					a.add(ap);
				}
				if (!in.contains(inv)) {
					in.add(inv);
				}
				ap.setPersonal(false);
				ap.addGroup(gr);
//				TODO ap.setAttending(inv.isAccepted());
//				TODO ap.setUsers();
				if (ap.getCreator()==c.getLoggedInUser().getId()) {
					ap.setAdmin(true);
				}
				else {
					ap.setAdmin(false);
				}
			}
		}
		o = new JSONObject();
		o.put("command", "get_all_for_user");
		o.put("uid", c.getLoggedInUser().getId());
		res = new JSONObject();
		res = API.call("/invitation", o, c.getSession());
		JSONArray invArray2 = res.getJSONArray("invitations");
		for (int i=0; i<invArray2.length(); i++) {
			JSONObject obj = new JSONObject();
			obj = invArray2.getJSONObject(i);
			Appointment ap = new Appointment(c);
			ap.fromJSON(obj.getJSONObject("app"));
			Invitation inv = new Invitation();
			inv.fromJSON(obj.getJSONObject("invitation"));
			if (!a.contains(ap)) {
				a.add(ap);
			}
			if (!in.contains(inv)) {
				in.add(inv);
			}
			ap.setPersonal(true);
//			TODO ap.setAttending(inv.isAccepted());
//			TODO setUsers
			if (ap.getCreator()==c.getLoggedInUser().getId()) {
				ap.setAdmin(true);
			}
			else {
				ap.setAdmin(false);
			}
		}
		c.getLoggedInUser().setAppointments(a);	
	}

}

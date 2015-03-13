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

public class SyncFromServer {
	
	
	public static void sync(Calendar c) throws IOException {
		getGroups(c);
		getAppointments(c);
	}
	
	public static void getGroups(Calendar c) throws IOException {
		JSONObject o = new JSONObject();
		o.put("command", "get_from_user");
		o.put("uid", c.getLoggedInUser().getId());
		
		JSONObject res = API.call("/group", o, c.getSession());
		JSONArray gidArray = res.getJSONArray("gid");
		o = new JSONObject();
		o.put("command", "get");
		o.put("gid", gidArray);
		
		res = API.call("/group", o, c.getSession());
		JSONArray groupArray = res.getJSONArray("groups");
		List<Group> g = new ArrayList<Group>();
		for (int i=0; i<groupArray.length(); i++) {
			Group gr = new Group();
			gr.fromJSON((JSONObject) groupArray.get(i));
			g.add(gr);
		}
		c.getLoggedInUser().setGroups(g);		
	}
	
	public static void getAppointments(Calendar c) throws IOException {
		JSONObject o;
		List<Group> g = c.getLoggedInUser().getGroups();
		JSONObject res;
		List<Appointment> a = new ArrayList<Appointment>();
		for (Group gr : g) {
			o = new JSONObject();
			o.put("command", "get_all_for_group");
			o.put("gid", gr.getId());
			res = new JSONObject();
			res = API.call("/invitation", o, c.getSession());
			JSONArray appArray = res.getJSONArray("app");
			for (int i=0; i<appArray.length(); i++) {
				Appointment ap = new Appointment(c);
				ap.fromJSON(appArray.getJSONObject(i));
				if (!a.contains(ap)) {
					a.add(ap);
				}
				//TODO Move logic from newappointment to appointment
			}			
		}
		o = new JSONObject();
		o.put("command", "get_all_for_user");
		o.put("uid", c.getLoggedInUser().getId());
		res = new JSONObject();
		res = API.call("/invitation", o, c.getSession());
		JSONArray invArray = res.getJSONArray("invitations");
		List<Invitation> in = new ArrayList<Invitation>();
		for (int i=0; i<invArray.length(); i++) {
			JSONObject obj = new JSONObject();
			obj = invArray.getJSONObject(i);
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
		}
//		TODO c.getLoggedInUser().setAppointments();	
//		TODO add refresh statements for some actions
	}

}

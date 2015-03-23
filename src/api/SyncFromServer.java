package api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import shared.Invitation;
import calendar.User;
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
		if (c.getLoggedInUser().isAdmin()) {
			o.put("command", "get_all");
		} else {
			o.put("command", "get_from_user");
			o.put("uid", c.getLoggedInUser().getId());
		}
		
		JSONObject res = API.call("/group", o, c.getSession());
		if (! c.getLoggedInUser().isAdmin()) {
			JSONArray gidArray = res.getJSONArray("gids");
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
		c.getLoggedInUser().clearAppointments();
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
			if (res!=null) {
				JSONArray invArray1= res.getJSONArray("invitations");
				for (int i=0; i<invArray1.length(); i++) {
					JSONObject obj = new JSONObject();
					obj = invArray1.getJSONObject(i);
					Appointment ap = new Appointment(c);
					ap.fromJSON(obj.getJSONObject("app"));
					ap.setValues();
					ap.setPersonal(false);
					ap.addGroup(gr);
					if (ap.getCreator()==c.getLoggedInUser().getId()) {
						ap.setAdmin(true);
						ap.setOpened(true);
					}
					else {
						ap.setAdmin(false);
						ap.setOpened(false);
					}
					
					boolean added = false;
					if (a!=null && !a.isEmpty()) {
						for (Appointment app : a) {
							if (app.getID()==ap.getID()) {
								added = true;
								break;
							}
						}
					}
					if (!added) {
						a.add(ap);
						c.getLoggedInUser().addAppointment(ap);
					}
	
				}
			}
		}
		o = new JSONObject();
		o.put("command", "get_all_for_user");
		o.put("uid", c.getLoggedInUser().getId());
		res = new JSONObject();
		res = API.call("/invitation", o, c.getSession());
		if (res!=null) {
			JSONArray invArray2 = res.getJSONArray("invitations");
			for (int i=0; i<invArray2.length(); i++) {
				JSONObject obj = new JSONObject();
				obj = invArray2.getJSONObject(i);
				Appointment ap = new Appointment(c);
				ap.fromJSON(obj.getJSONObject("app"));
				ap.setValues();
				Invitation inv = new Invitation();
				inv.fromJSON(obj.getJSONObject("invitation"));
				boolean isAdded = false;
				for (Appointment app : a) {
					if (app.getID()==ap.getID()) {
						isAdded = true;
						break;
					}
				}
				if (!isAdded) {
					a.add(ap);
					c.getLoggedInUser().addAppointment(ap);
				}
				if (!in.contains(inv)) {
					in.add(inv);
				}
				JSONObject uidReq = new JSONObject();
				uidReq.put("command", "get_all_users_for_app");
				uidReq.put("aid", ap.getId());
				JSONObject getUsers = new JSONObject();
				getUsers = API.call("/invitation", uidReq, c.getSession());
				JSONArray usersArray = getUsers.getJSONArray("users");
				ap.setInvitation(inv);
				if (usersArray.length()==1) {
					ap.setPersonal(true);
					ap.setAttending(null);
				}
				else {
					ap.setPersonal(false);
					ap.setAttending(inv.isAccepted());
					ap.setOpened(!inv.isDirty());
					for (int j=0; j<usersArray.length(); j++) {
						JSONObject object = new JSONObject();
						object = usersArray.getJSONObject(j);
						int uid = object.getInt("uid");
						int answer = object.getInt("attending");
						JSONObject userReq = new JSONObject();
						userReq.put("command", "get_by_id");
						userReq.put("uid", uid);
						JSONObject jsonUserReq = new JSONObject();
						jsonUserReq = API.call("/user", userReq, c.getSession());
						JSONObject jsonUser = new JSONObject();
						jsonUser = jsonUserReq.getJSONObject("user");
						User u = new User();
						u.fromJSON(jsonUser);
						ap.addUser(u);
						ap.setUserAttending(u, answer);
					}
				}
				if (ap.getCreator()==c.getLoggedInUser().getId()) {
					ap.setAdmin(true);
				}
				else {
					ap.setAdmin(false);
				}
			}
		}
	}
}

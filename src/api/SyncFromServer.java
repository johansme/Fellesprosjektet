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
import calendar.Participant;

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
//			if (!res.getBoolean("status")) {
//				System.out.println(res.get("error"));
//			}
			if (res!=null) {
				JSONArray invArray1= res.getJSONArray("invitations");
				for (int i=0; i<invArray1.length(); i++) {
					JSONObject obj = new JSONObject();
					obj = invArray1.getJSONObject(i);
					Appointment ap = new Appointment(c);
					ap.fromJSON(obj.getJSONObject("app"));
					ap.setPersonal(false);
					ap.addGroup(gr);
	//				TODO code for getting the response from other users, for for for
	//				JSONObject uidReq = new JSONObject();
	//				uidReq.put("command", "get_all_users_for_app");
	//				uidReq.put("aid", ap.getId());
	//				JSONObject getUids = new JSONObject();
	//				getUids = API.call("/invitation", o, c.getSession());
	//				JSONArray uidArray = getUids.getJSONArray("uids");
	//				for (int j=0; j<uidArray.length(); j++) {
	//					int uid = uidArray.getInt(j);
	//					JSONObject appsForUidReq = new JSONObject();
	//					appsForUidReq.put("command", "get_all_for_user");
	//					appsForUidReq.put("uid", uid);
	//					JSONObject getApps = new JSONObject();
	//					getApps = API.call("/invitation", appsForUidReq, c.getSession());
	//					JSONArray uidInvArray = getApps.getJSONArray("invitations");
	//					for (int x=0; x<uidInvArray.length(); x++) {
	//						JSONObject appObj = new JSONObject();
	//						appObj = uidInvArray.getJSONObject(x);
	//						Appointment app = new Appointment(c);
	//						app.fromJSON(appObj.getJSONObject("app"));
	//						Invitation inv = new Invitation();
	//						inv.fromJSON(obj.getJSONObject("invitation"));
	//					}
	//				}
					if (ap.getCreator()==c.getLoggedInUser().getId()) {
						ap.setAdmin(true);
					}
					else {
						ap.setAdmin(false);
					}
					ap.setValues();
					
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
				JSONObject getUids = new JSONObject();
				getUids = API.call("/invitation", uidReq, c.getSession());
				JSONArray uidArray = getUids.getJSONArray("uids");
				if (uidArray.length()==1) {
					ap.setPersonal(true);
					ap.setAttending(null);
				}
				else {
					ap.setPersonal(false);
					ap.setAttending(inv.isAccepted());
					for (int j=0; j<uidArray.length(); j++) {
						JSONObject userReq = new JSONObject();
						userReq.put("command", "get_by_id");
						userReq.put("uid", uidArray.getInt(j));
						JSONObject jsonUserReq = new JSONObject();
						jsonUserReq = API.call("/user", userReq, c.getSession());
						JSONObject jsonUser = new JSONObject();
						jsonUser = jsonUserReq.getJSONObject("user");
						User u = new User();
						u.fromJSON(jsonUser);
						ap.addUser(u);
					}
				}
				if (ap.getCreator()==c.getLoggedInUser().getId()) {
					ap.setAdmin(true);
				}
				else {
					ap.setAdmin(false);
				}
				ap.setValues();
			}
		}
	}
}

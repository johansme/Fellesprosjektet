package server.httpserver;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import server.Appointment;
import server.Invitation;
import server.Notification;
import server.User;

import com.sun.net.httpserver.HttpExchange;

public class HttpAppointment extends HttpAPIHandler {
	public HttpAppointment(Server server) {
		super(server);
	}
	
	public void handle(HttpExchange t) throws IOException {
		try{
			JSONObject request = get(t);
			User u = server.getUserFromExchange(t);
	
			if(u == null) {
				sendUnauthenticated(t);
				return;
			}
			
			String command = request.getString("command");
			if(command != null) {
				switch(command) {
					case "create":
						create(t, u, request);
						return;
					case "modify":
						modify(t, u, request);
						return;
					case "delete":
						delete(t, u, request);
						return;
					case "get_from_creator":
						getFromCreator(t, u, request);
						return;
					default:
						sendInvalidCommand(t);
						return;
				}
			} else {
				sendInvalidCommand(t);
			}
		} catch(JSONException e) { sendInvalidCommand(t);
		} catch(Exception e) { t.close(); e.printStackTrace(); }
	}
	
	private void modify(HttpExchange t, User u, JSONObject request) throws IOException {
		JSONObject japp;
		try {
			japp = request.getJSONObject("app");
		} catch(JSONException e) {
			sendError(t, "No appointment object");
			return;
		}
		
		Appointment new_app = new Appointment();
		Appointment old_app = null;
		if(!new_app.fromJSON(japp)) {
			sendError(t, "Error parsing appointment object)");
			return;
		}
		
		try {
			old_app = new Appointment(new_app.getId());
		} catch(Exception e) {
			sendError(t, "Could not fetch old appointment from DB");
			return;
		}
		
		new_app.setCreator(old_app.getCreator()); // Don't trust the user
		
		if(old_app.getCreator() == u.getId() || u.isAdmin()) {
			if(Appointment.changeAppointment(new_app)) {
				Invitation.dirtify(new_app.getId());
				Invitation.alertify(new_app.getId(), new_app.getStart());
				Notification.sendModifiedAppointmentNotification(new_app.getId());
				sendOK(t);
				return;
			} else {
				sendError(t, "Error updating database");
				return;
			}
		} else {
			sendUnauthorised(t);
			return;
		}
		
	}

	private void create(HttpExchange t, User u, JSONObject request) throws IOException {
		JSONObject japp;
		try {
			japp = request.getJSONObject("app");
		} catch(JSONException e) {
			sendError(t, "No appointment object");
			return;
		}
		
		Appointment app = new Appointment();
		if(!app.fromJSON(japp)) {
			sendError(t, "Error parsing appointment object");
			return;
		}
		
		app.setCreator(u.getId());
		int id = Appointment.createAppointment(app);
		if(id != 0) {
			sendOK(t, new JSONObject().put("aid", id));
			return;
		} else {
			sendError(t, "Error creating appointment");
			return;
		}
	}
	
	private void delete(HttpExchange t, User u, JSONObject request) throws IOException {
		Appointment app;
		int aid;
		
		try {
			aid = request.getInt("aid");
		} catch(Exception e) {
			sendInvalidCommand(t);
			return;
		}
		
		try {
			app = new Appointment(aid);
		} catch(Exception e) {
			sendError(t, "Error reading appointment from DB");
			return;
		}
		
		if(u.getId() == app.getCreator() || u.isAdmin()) {
			Appointment a = new Appointment(aid);
			ArrayList<Integer> users = Invitation.getInvitationsForAppointment(aid);
			if(Appointment.deleteAppointment(aid)) {
				for(int uid : users) {
					Notification.sendAppointmentDeleteNotification(a, uid);
				}
				sendOK(t);
				return;
			} else {
				sendError(t, "Error deleting appointment from database");
			}
		} else {
			sendUnauthorised(t);
		}
	}
	
	void getFromCreator(HttpExchange t, User u, JSONObject request) throws IOException {
		int uid;
		try {
			uid = request.getInt("uid");
		} catch(Exception e) {
			sendInvalidCommand(t);
			return;
		}
		
		if(!(u.getId() == uid) && !u.isAdmin()) {
			sendUnauthorised(t);
			return;
		}
		
		ArrayList<Integer> appointments = Appointment.getAppointmentsFromCreator(uid);
		JSONArray jarr = new JSONArray();
		for(int i : appointments) {
			jarr.put(i);
		}
		sendOK(t, new JSONObject().put("aids", jarr));
	}
}

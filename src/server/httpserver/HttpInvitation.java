package server.httpserver;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import server.Appointment;
import server.Group;
import server.Invitation;
import server.Notification;
import server.Room;
import server.User;

import com.sun.net.httpserver.HttpExchange;

public class HttpInvitation extends HttpAPIHandler {
	public HttpInvitation(Server server) {
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
					case "get_all_for_user":
						getAllForUser(t, u, request);
						return;
					case "get_all_for_group":
						getAllForGroup(t, u, request);
						return;
					case "get_all_users_for_app":
						getAllUsersForAppointment(t, u, request);
						return;
					case "get_all_groups_for_app":
						getAllGroupsForAppointment(t, u, request);
						return;
					case "invite_user":
						inviteUser(t, request);
						return;
					case "invite_group":
						inviteGroup(t, request);
						return;
					case "remove_user":
						removeUser(t, u, request);
						return;
					case "remove_group":
						removeGroup(t, u, request);
						return;
					case "update_attending":
						updateAttending(t, u, request);
						return;
					case "update_hidden":
						updateHidden(t, u, request);
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
	
	private void getAllForUser(HttpExchange t, User u, JSONObject request) throws IOException {
		int uid;
		try {
			uid = request.getInt("uid");
		} catch(Exception e) {
			sendInvalidCommand(t);
			return;
		}
		
		if(u.getId() != uid && !u.isAdmin()) {
			sendUnauthorised(t);
			return;
		}
		
		ArrayList<Invitation> invitations = Invitation.getInvitationsForUser(uid);
		JSONArray jarr = new JSONArray();
		for(Invitation i : invitations) {
			Appointment a;
			try {
				a = new Appointment(i.getAid());
			} catch(Exception e) {
				e.printStackTrace();
				continue;
			}
			
			int room = Room.getForAppointment(a.getId());
			try {
				if(room != 0) {
					Room r = new Room(room);
					a.setLocation(r.getName());
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			jarr.put(new JSONObject().put("invitation", i.toJSON()).put("app", a.toJSON()));
		}
		sendOK(t, new JSONObject().put("invitations", jarr));
	}
	
	private void getAllForGroup(HttpExchange t, User u, JSONObject request) throws IOException {
		int gid;
		try {
			gid = request.getInt("gid");
		} catch(Exception e) {
			sendInvalidCommand(t);
			return;
		}
		
		if(!Group.isUserInGroup(gid, u.getId()) && !u.isAdmin()) {
			sendUnauthorised(t);
			return;
		}
		
		ArrayList<Integer> invitations = Invitation.getInvitationsForGroup(gid);
		JSONArray jarr = new JSONArray();
		for(int i : invitations) {
			Appointment a;
			try {
				a = new Appointment(i);
			} catch(Exception e) {
				e.printStackTrace();
				continue;
			}
			
			int room = Room.getForAppointment(a.getId());
			try {
				if(room != 0) {
					Room r = new Room(room);
					a.setLocation(r.getName());
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			jarr.put(new JSONObject().put("aid", i).put("app", a.toJSON()));
		}
		sendOK(t, new JSONObject().put("invitations", jarr));
	}
	
	private void getAllUsersForAppointment(HttpExchange t, User u, JSONObject request) throws IOException {
		int aid;
		try {
			aid = request.getInt("aid");
		} catch(Exception e) {
			sendInvalidCommand(t);
			return;
		}
		
		Appointment a;
		try {
			a = new Appointment(aid);
		} catch(Exception e) {
			sendError(t, "Invalid appointment");
			return;
		}
		
		if(!Invitation.isUserInvited(a.getId(), u.getId()) && !u.isAdmin()) {
			sendUnauthorised(t);
			return;
		}
		
		ArrayList<Integer> users = Invitation.getInvitationsForAppointment(aid);
		JSONArray jarr = new JSONArray();
		for(int i : users) {
			jarr.put(i);
		}
		sendOK(t, new JSONObject().put("uids", jarr));
	}
	
	private void getAllGroupsForAppointment(HttpExchange t, User u, JSONObject request) throws IOException {
		int aid;
		try {
			aid = request.getInt("aid");
		} catch(Exception e) {
			sendInvalidCommand(t);
			return;
		}
		
		Appointment a;
		try {
			a = new Appointment(aid);
		} catch(Exception e) {
			sendError(t, "Invalid appointment");
			return;
		}
		
		if(!Invitation.isUserInvited(a.getId(), u.getId()) && !u.isAdmin()) {
			sendUnauthorised(t);
			return;
		}
		
		ArrayList<Integer> groups = Invitation.getGroupInvitationsForAppointment(aid);
		JSONArray jarr = new JSONArray();
		for(int i : groups) {
			jarr.put(i);
		}
		sendOK(t, new JSONObject().put("gids", jarr));
	}
	
	private void inviteUser(HttpExchange t, JSONObject request) throws IOException {
		int uid, aid;
		try {
			uid = request.getInt("uid");
			aid = request.getInt("aid");
		} catch(Exception e) {
			sendInvalidCommand(t);
			return;
		}
		
		if(Invitation.isUserInvited(aid, uid)) {
			sendError(t, "User already invited");
			return;
		}
		
		Appointment a;
		try {
			a = new Appointment(aid);
		} catch(Exception e) {
			sendError(t, "Error");
			return;
		}
		
		if(Invitation.inviteUser(aid, uid, a.getStart())) {
			Notification.sendInvitationNotification(aid, uid);
			sendOK(t);
			return;
		} else {
			sendError(t, "Error inviting user");
			return;
		}
	}
	
	private void inviteGroup(HttpExchange t, JSONObject request) throws IOException {
		int gid, aid;
		try {
			gid = request.getInt("gid");
			aid = request.getInt("aid");
		} catch(Exception e) {
			sendInvalidCommand(t);
			return;
		}
		
		Appointment a;
		try {
			a = new Appointment(aid);
		} catch(Exception e) {
			sendError(t, "Error getting appointment");
			return;
		}
		
		// TODO: Recurse into children?
		ArrayList<Integer> members = Group.getUsers(gid);
		for(int uid : members) {
			if(Invitation.inviteUser(aid, uid, a.getStart())); {
				Notification.sendInvitationNotification(aid, uid);
			}
		}
		
		if(Invitation.inviteGroup(aid, gid)) {
			sendOK(t);
			return;
		} else {
			sendError(t, "Error inviting group");
			return;
		}
	}
	
	private void removeUser(HttpExchange t, User u, JSONObject request) throws IOException {
		int aid,uid;
		
		try {
			aid = request.getInt("aid");
			uid = request.getInt("uid");
		} catch(Exception e) {
			sendInvalidCommand(t);
			return;
		}
		
		Appointment a;
		try {
			a = new Appointment(aid);
		} catch(Exception e) {
			sendError(t, "Error getting appointment from DB");
			return;
		}
		
		if(u.getId() != a.getCreator() && !u.isAdmin()) {
			sendUnauthorised(t);
			return;
		}
		
		if(Invitation.removeUser(aid, uid)) {
			sendOK(t);
			return;
		} else {
			sendError(t, "Error removing user from appointment; contact law enforcement");
			return;
		}
	}
	
	private void removeGroup(HttpExchange t, User u, JSONObject request) throws IOException {
		int aid,gid;
		
		try {
			aid = request.getInt("aid");
			gid = request.getInt("gid");
		} catch(Exception e) {
			sendInvalidCommand(t);
			return;
		}
		
		Appointment a;
		try {
			a = new Appointment(aid);
		} catch(Exception e) {
			sendError(t, "Error getting appointment from DB");
			return;
		}
		
		if(u.getId() != a.getCreator() && !u.isAdmin()) {
			sendUnauthorised(t);
			return;
		}
		
		if(Invitation.removeGroup(aid, gid)) {
			sendOK(t);
			return;
		} else {
			sendError(t, "Error removing group from appointment; contact armed law enforcement");
			return;
		}
	}
	
	private void updateAttending(HttpExchange t, User u, JSONObject request) throws IOException {
		int aid, uid;
		boolean attending;
		
		try {
			aid = request.getInt("aid");
			uid = request.getInt("uid");
			attending = request.getBoolean("attending");
		} catch(Exception e) {
			sendInvalidCommand(t);
			return;
		}
		
		Appointment a;
		try {
			a = new Appointment(aid);
		} catch(Exception e) {
			sendError(t, "Error getting appointment from DB");
			return;
		}
		
		if(u.getId() != uid && u.getId() != a.getCreator() && !u.isAdmin()) {
			sendUnauthorised(t);
			return;
		}
		
		Invitation.setAttending(aid, uid, attending);
		sendOK(t);
	}
	
	private void updateHidden(HttpExchange t, User u, JSONObject request) throws IOException {
		int aid, uid;
		boolean hidden;
		
		try {
			aid = request.getInt("aid");
			uid = request.getInt("uid");
			hidden = request.getBoolean("hidden");
		} catch(Exception e) {
			sendInvalidCommand(t);
			return;
		}
			
		if(u.getId() != uid && !u.isAdmin()) {
			sendUnauthorised(t);
			return;
		}
		
		Invitation.setHidden(aid, uid, hidden);
		sendOK(t);
	}
}

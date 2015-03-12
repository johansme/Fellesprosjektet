package server.httpserver;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import server.Appointment;
import server.Invitation;
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
					case "invite_user":
						inviteUser(t, request);
						return;
					case "invite_group":
						inviteGroup(t, request);
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
				System.out.println("Failed to fetch appointment (" + i.getAid()
						+ ") from invitation (" + i.getAid() + "," + uid + ")");
				continue;
			}
			jarr.put(new JSONObject().put("invitation", i.toJSON()).put("appointment", a.toJSON()));
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
		
		// TODO: Check that user is in group
		if(/*u.getId() != uid*/ false && !u.isAdmin()) {
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
				System.out.println("Failed to fetch appointment (" + i
						+ ") from invitation (" + i + "," + gid + ")");
				continue;
			}
			jarr.put(new JSONObject().put("aid", i).put("appointment", a.toJSON()));
		}
		sendOK(t, new JSONObject().put("invitations", jarr));
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
		
		if(Invitation.inviteUser(aid, uid)) {
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
		
		// TODO: Invite all members of group
		if(Invitation.inviteGroup(aid, gid)) {
			sendOK(t);
			return;
		} else {
			sendError(t, "Error inviting group");
			return;
		}
	}
}

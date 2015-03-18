package server.httpserver;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import server.Group;
import server.User;

import com.sun.net.httpserver.HttpExchange;

public class HttpGroup extends HttpAPIHandler {
	
	public HttpGroup(Server server) {
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
					case "delete":
						sendNotImplemented(t);
						return;
					case "get_all":
						getAll(t);
						return;
					case "get":
					case "get_from_user":
					case "get_from_creator":
						sendNotImplemented(t);
						return;
					case "add_user":
						addUser(t, u, request);
						return;
					case "remove_user":
						removeUser(t, u, request);
						return;
					default:
						sendInvalidCommand(t);
				}
			} else {
				sendInvalidCommand(t);
			}
		} catch(JSONException e) { sendInvalidCommand(t);
		} catch(Exception e) { e.printStackTrace();	}
	}
	
	private void create(HttpExchange t, User u, JSONObject request) throws IOException {
		if(!u.isAdmin()) {
			sendUnauthorised(t);
			return;
		}
		
		JSONObject japp;
		try {
			japp = request.getJSONObject("group");
		} catch(JSONException e) {
			sendError(t, "No group object");
			return;
		}
		
		Group grp = new Group();
		if(!grp.fromJSON(japp)) {
			sendError(t, "Error parsing group object");
			return;
		}
		
		grp.setCreatedBy(u.getId());
		if(Group.createGroup(grp)) {
			sendOK(t);
			return;
		} else {
			sendError(t, "Error creating group");
			return;
		}
	}
	
	private void getAll(HttpExchange t) throws IOException {
		ArrayList<Group> list = Group.getAllGroups();
		JSONArray arr = new JSONArray();
		for(Group g : list) {
			arr.put(g.toJSON());
		}
		sendOK(t, new JSONObject().put("groups", arr));
	}
	
	private void addUser(HttpExchange t, User u, JSONObject request) throws IOException {
		if(!u.isAdmin()) {
			sendUnauthorised(t);
			return;
		}
		
		int uid, gid;
		try {
			uid = request.getInt("uid");
			gid = request.getInt("gid");
		} catch(JSONException e) {
			sendInvalidCommand(t);
			return;
		}
		
		if(Group.isUserInGroup(gid, uid)) {
			sendError(t, "User already in group");
			return;
		}
		
		if(Group.addUser(gid, uid)) {
			sendOK(t);
			return;
		} else {
			sendError(t, "Error adding user to group");
			return;
		}
	}
	
	private static void removeUser(HttpExchange t, User u, JSONObject request) throws IOException {
		if(!u.isAdmin()) {
			sendUnauthorised(t);
			return;
		}
		
		int uid, gid;
		try {
			uid = request.getInt("uid");
			gid = request.getInt("gid");
		} catch(JSONException e) {
			sendInvalidCommand(t);
			return;
		}
		
		if(!Group.isUserInGroup(gid, uid)) {
			sendError(t, "User not in group");
			return;
		}
		
		if(Group.removeUser(gid, uid)) {
			sendOK(t);
			return;
		} else {
			sendError(t, "Error removing user from group");
			return;
		}
	}
}

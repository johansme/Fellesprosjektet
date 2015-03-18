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
						modify(t, u, request);
						return;
					case "delete":
						delete(t, u, request);
						return;
					case "get_all":
						getAll(t);
						return;
					case "get":
					case "get_users":
						getUsers(t, u, request);
						return;
					case "get_children":
						getChildren(t, request);
						return;
					case "get_from_user":
						getFromUser(t, u, request);
						return;
					case "get_from_creator":
						getFromCreator(t, u, request);
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
	
	private void modify(HttpExchange t, User u, JSONObject request) throws IOException {
		JSONObject jgroup;
		try {
			jgroup = request.getJSONObject("group");
		} catch(JSONException e) {
			sendInvalidCommand(t);
			return;
		}
		
		Group new_group = new Group();
		Group old_group = null;
		if(!new_group.fromJSON(jgroup)) {
			sendError(t, "Error parsing user object)");
			return;
		}
		
		try {
			old_group = new Group(new_group.getId());
		} catch(Exception e) {
			sendError(t, "Could not fetch old user from DB");
			return;
		}
		
		if(!u.isAdmin()) {
			new_group.setCreatedBy(old_group.getCreatedBy()); // Don't trust the users
		}
		
		if(old_group.getCreatedBy() == u.getId() || u.isAdmin()) {
			if(Group.modify(new_group)) {
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
	
	private void delete(HttpExchange t, User u, JSONObject request) throws IOException {
		try {
			int gid = request.getInt("gid");
			
			Group g = new Group(gid);
			
			if(g.getCreatedBy() != u.getId() && !u.isAdmin()) {
				sendUnauthorised(t);
				return;
			}
						
			if(Group.deleteGroup(gid)) {
				sendOK(t);
				return;
			} else {
				sendError(t, "Error removing group from DB");
				return;
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			sendInvalidCommand(t);
		}
	}
	
	private void getUsers(HttpExchange t, User u, JSONObject request) throws IOException {
		int gid;
		try {
			gid = request.getInt("gid");
		} catch(Exception e) {
			sendInvalidCommand(t);
			return;
		}
		
		if(!(Group.isUserInGroup(gid, u.getId())) && !u.isAdmin()) {
			sendUnauthorised(t);
			return;
		}
		
		ArrayList<Integer> users = Group.getUsers(gid);
		JSONArray jarr = new JSONArray();
		for(int i : users) {
			jarr.put(i);
		}
		sendOK(t, new JSONObject().put("gids", jarr));
	}
	
	private void getChildren(HttpExchange t, JSONObject request) throws IOException {
		int gid;
		try {
			gid = request.getInt("gid");
		} catch(Exception e) {
			sendInvalidCommand(t);
			return;
		}
		
		ArrayList<Integer> groups = Group.getChildren(gid);
		JSONArray jarr = new JSONArray();
		for(int i : groups) {
			jarr.put(i);
		}
		sendOK(t, new JSONObject().put("gids", jarr));
	}
	
	private void getAll(HttpExchange t) throws IOException {
		ArrayList<Group> list = Group.getAllGroups();
		JSONArray arr = new JSONArray();
		for(Group g : list) {
			arr.put(g.toJSON());
		}
		sendOK(t, new JSONObject().put("groups", arr));
	}
	
	private void getFromUser(HttpExchange t, User u, JSONObject request) throws IOException {
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
		
		ArrayList<Integer> groups = Group.getAllFromUser(uid);
		JSONArray jarr = new JSONArray();
		for(int i : groups) {
			jarr.put(i);
		}
		sendOK(t, new JSONObject().put("gids", jarr));
	}
	
	private void getFromCreator(HttpExchange t, User u, JSONObject request) throws IOException {
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
		
		ArrayList<Integer> groups = Group.getAllFromCreator(uid);
		JSONArray jarr = new JSONArray();
		for(int i : groups) {
			jarr.put(i);
		}
		sendOK(t, new JSONObject().put("gids", jarr));
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

package server.httpserver;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;

import server.User;

public class HttpUser extends HttpAPIHandler {
	public HttpUser(Server server) {
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
					case "get_by_id":
						getById(t, request);
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
		try {
			if(!u.isAdmin()) {
				sendUnauthorised(t);
				return;
			}
			
			User new_user = new User();
			boolean gotuser = new_user.fromJSON(request.getJSONObject("user"));
			String pwd = request.getString("password");
			System.out.println(request.getJSONObject("user").toString());
			System.out.println(gotuser);
			System.out.println(pwd);
			if(!gotuser || pwd == null) {
				sendInvalidCommand(t);
				return;
			}
			
			if(User.createUser(new_user, pwd)) {
				sendOK(t);
				return;
			} else {
				sendError(t, "Error creating user");
				return;
			}
		} catch(Exception e) {
			e.printStackTrace();
			sendInvalidCommand(t);
		}
	}
	
	private void getAll(HttpExchange t) throws IOException {
		ArrayList<User> arr;
		try {
			arr = User.getAllUsers();
		} catch(Exception e) {
			sendError(t, "Error getting users from DB");
			return;
		}
		
		JSONArray jarr = new JSONArray();
		for(User u : arr) {
			jarr.put(u.toJSON());
		}
		
		sendOK(t, new JSONObject().put("users", jarr));
	}
	
	private void getById(HttpExchange t, JSONObject request) throws IOException {
		User u;
		int uid;
		
		try {
			uid = request.getInt("uid");
		} catch(Exception e) {
			sendInvalidCommand(t);
			return;
		}
		
		try {
			u = new User(uid);
		} catch(Exception e) {
			sendError(t, "Error fetching user from DB");
			return;
		}
		
		sendOK(t, new JSONObject().put("user", u.toJSON()));
	}
}

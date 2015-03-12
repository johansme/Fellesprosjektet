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
	
			if(server.getUserFromExchange(t) == null) {
				sendUnauthenticated(t);
				return;
			}
			
			String command = request.getString("command");
			if(command != null) {
				switch(command) {
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

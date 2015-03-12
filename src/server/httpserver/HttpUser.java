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
			if(command != null && command.equals("get_all")) {
				sendAll(t);
				return;
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
	}
}

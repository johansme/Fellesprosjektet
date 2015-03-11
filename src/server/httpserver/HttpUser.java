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
	
	private void sendAll(HttpExchange t) throws IOException {
		JSONObject response = new JSONObject();
		response.put("status", true);
		ArrayList<User> arr = User.getAllUsers();
		JSONArray jarr = new JSONArray();
		for(User u : arr) {
			jarr.put(u.toJSON());
		}
		response.put("users", jarr);
		send(t, response);
	}
}

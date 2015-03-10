package server.httpserver;

import java.io.IOException;

import com.sun.net.httpserver.*;

import org.json.*;

import server.User;

public class HttpLogin extends HttpAPIHandler {
	public HttpLogin(Server server) {
		super(server);
	}

	public void handle(HttpExchange t)  throws IOException {
		JSONObject response = new JSONObject();
		JSONObject obj = get(t);

		if(obj != null) {
			String username = (String)obj.get("username");
			String password = (String)obj.get("password");
		
			if(obj != null && (username != null && password != null)) {
				if(User.checkPassword(username, password)) {
					User u = null;
					try { u = new User(username); } catch(Exception e) {}
					response.put("status", true);
					response.put("session", (String)server.getSessionManager().getNewSession(u.getId()));
				} else {
					response.put("status", false);
					response.put("error", "Invalid username or password");
				}
			} else {
				response.put("status", false);
				response.put("error", "Invalid request");
			}
		} else {
			response.put("status", false);
			response.put("error", "Not JSON");
		}
		
		send(t, response);
	}
}
package server.httpserver;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.*;

import org.json.*;

import server.User;
import server.Util;

public class HttpLogin implements HttpHandler {
	Server server;

	public HttpLogin(Server server) {
		this.server = server;
	}

	public void handle(HttpExchange t) throws IOException {
		JSONObject response = new JSONObject();
		JSONObject obj = Util.streamToJSON(t.getRequestBody());

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
		
		String response_str = response.toString();
		System.out.println(response_str);
		t.sendResponseHeaders(200, response_str.length());
		OutputStream out = t.getResponseBody();
		out.write(response_str.getBytes());
		t.close();
	}
}
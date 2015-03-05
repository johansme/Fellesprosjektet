package server.httpserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.sun.net.httpserver.*;

import org.json.*;

import server.User;

public class HttpLogin implements HttpHandler {
	Server server;

	public HttpLogin(Server server) {
		this.server = server;
	}

	public void handle(HttpExchange t) throws IOException {
		final InputStream is;
		StringBuilder sb = new StringBuilder();

		is = t.getRequestBody();

		int b;
		while((b = is.read()) != -1) {
			sb.append((char)b);
		}
		is.close();

		JSONObject obj = new JSONObject(sb.toString());
		String username = (String)obj.get("username");
		String password = (String)obj.get("password");

		String response = new String();
		if(username != null || password != null) {
			if(User.checkPassword(username, password)) {
				User u = new User(username);
				response = (String)server.getSessionManager().getNewSession(u.getId());
			} else {
				response = "Invalid username or password";
			}
		} else {
			response = "Invalid request";
		}

		t.sendResponseHeaders(200, response.length());
		OutputStream out = t.getResponseBody();
		out.write(response.getBytes());
		t.close();
	}
}
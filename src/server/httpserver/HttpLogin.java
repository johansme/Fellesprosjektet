package server.httpserver;

import java.io.IOException;
import java.sql.SQLException;

import com.sun.net.httpserver.*;

import org.json.*;

import server.User;

public class HttpLogin extends HttpAPIHandler {
	public HttpLogin(Server server) {
		super(server);
	}

	public void handle(HttpExchange t)  throws IOException {
		try {
			JSONObject request = get(t);
			String username = (String)request.get("username");
			String password = (String)request.get("password");
			
			if(User.checkPassword(username, password)) {
				User u = new User(username);
				sendOK(t, new JSONObject().put("session", (String)server.getSessionManager().getNewSession(u.getId())));
				return;
			} else {
				sendError(t, "Invalid authentication credentials");
				return;
			}
		} catch(JSONException e) { sendInvalidCommand(t);
		} catch(SQLException e) { sendError(t, "Error fetching user data from DB");
		} catch(Exception e) { t.close(); e.printStackTrace(); }
	}
}
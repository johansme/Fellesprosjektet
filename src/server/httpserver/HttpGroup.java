package server.httpserver;

import java.io.IOException;

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
					default:
						sendInvalidCommand(t);
				}
			} else {
				sendInvalidCommand(t);
			}
		} catch(JSONException e) { sendInvalidCommand(t);
		} catch(Exception e) { e.printStackTrace();	}
	}
	
	public void create(HttpExchange t, User u, JSONObject request) throws IOException {
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
}

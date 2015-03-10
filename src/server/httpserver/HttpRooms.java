package server.httpserver;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import server.User;
import server.Room;

import com.sun.net.httpserver.*;

public class HttpRooms extends HttpAPIHandler {
	public HttpRooms(Server server) {
		super(server);
	}
	
	public void handle(HttpExchange t) throws IOException {
		JSONObject response = new JSONObject();
		User u = server.getUserFromExchange(t);
		if(u == null) {
			response.put("status", false);
			response.put("error", "Not authenticated");
		} else {
			if(u.isAdmin()) {
				ArrayList<Room> rooms = Room.getAllRooms();
				response.put("status", true);
				JSONArray roomarr = new JSONArray();
				for(Room r : rooms) {
					roomarr.put(r.toJSON());
				}
				response.put("rooms", roomarr);
			} else {
				response.put("status", false);
				response.put("error", "Not authorised");
			}
		}
		send(t, response);
	}
}

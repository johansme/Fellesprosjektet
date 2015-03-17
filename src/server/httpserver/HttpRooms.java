package server.httpserver;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import server.Room;

import com.sun.net.httpserver.*;

public class HttpRooms extends HttpAPIHandler {
	public HttpRooms(Server server) {
		super(server);
	}
	
	public void handle(HttpExchange t) throws IOException {
		try {
			JSONObject request = get(t);
			
			if(server.getUserFromExchange(t) == null) {
				sendUnauthenticated(t);
				return;
			}
			
			String command = request.getString("command");
			if(command != null) {
				switch(command) {
					case "create":
					case "remove":
					case "get_available":
						sendNotImplemented(t);
						return;
					case "get_all":
						getAll(t);
						return;
					case "get":
					case "reserve": 
					case "cancel":
						sendNotImplemented(t);
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
		ArrayList<Room> rooms = Room.getAllRooms();
		if(rooms == null) {
			sendError(t, "Error getting rooms from DB");
			return;
		}
		
		JSONArray jarr = new JSONArray();
		for(Room r : rooms) {
			jarr.put(r.toJSON());
		}
		
		sendOK(t, new JSONObject().put("rooms", jarr));
		return;
	}
}

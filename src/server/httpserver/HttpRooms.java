package server.httpserver;

import java.io.IOException;
import java.util.Date;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import server.Appointment;
import server.Room;
import server.User;

import com.sun.net.httpserver.*;

public class HttpRooms extends HttpAPIHandler {
	public HttpRooms(Server server) {
		super(server);
	}
	
	public void handle(HttpExchange t) throws IOException {
		try {
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
					case "remove":
						remove(t, u, request);
						return;
					case "get_available":
						getAvailable(t, request);
						return;
					case "get_all":
						getAll(t);
						return;
					case "get":
						getRooms(t, request);
						return;
					case "reserve":
						reserve(t, u, request);
						return;
					case "cancel":
						cancel(t, u, request);
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
	
	private void create(HttpExchange t, User u, JSONObject request) throws IOException {
		try {
				if(!u.isAdmin()) {
				sendUnauthorised(t);
				return;
			}
			
			Room r = new Room();
			if(!r.fromJSON(request.getJSONObject("room"))) {
				sendInvalidCommand(t);
				return;
			}
			
			if(Room.create(r)) {
				sendOK(t);
				return;
			} else {
				sendError(t, "Error creating room");
				return;
			}
		} catch(Exception e) {
			e.printStackTrace();
			sendInvalidCommand(t);
		}
	}
	
	// Notify users with room reserved about cancellations?
	private void remove(HttpExchange t, User u, JSONObject request) throws IOException {
		try {
			if(!u.isAdmin()) {
				sendUnauthorised(t);
				return;
			}
			
			int rid = request.getInt("rid");
			if(Room.remove(rid)) {
				sendOK(t);
				return;
			} else {
				sendError(t, "Error removing room from DB");
				return;
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			sendInvalidCommand(t);
		}
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
	
	private void getAvailable(HttpExchange t, JSONObject request) throws IOException {
		Date start, end;
		try {
			start = new Date(request.getLong("start"));
			end = new Date(request.getLong("end"));
		} catch(Exception e) {
			sendInvalidCommand(t);
			return;
		}
		
		ArrayList<Room> rooms = Room.getAvailable(start, end);
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
	
	private void getRooms(HttpExchange t, JSONObject request) throws IOException {
		JSONArray rids;
		try {
			rids = request.getJSONArray("rid");
		} catch(Exception e) {
			sendInvalidCommand(t);
			return;
		}
		
		JSONArray result = new JSONArray();
		for(int i = 0; i < rids.length(); i++) {
			try {
				Room r = new Room(rids.getInt(i));
				if(r.getId() != 0) {
					result.put(r.toJSON());
				}
			} catch(Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		
		sendOK(t, new JSONObject().put("rooms", result));
		return;
		
	}

	private void reserve(HttpExchange t, User u, JSONObject request) throws IOException {
		int aid, rid;
		Date start, end;
		try {
			aid = request.getInt("aid");
			rid = request.getInt("rid");
			start = new Date(request.getLong("start"));
			end = new Date(request.getLong("end"));
		} catch(Exception e) {
			sendInvalidCommand(t);
			return;
		}
		
		Appointment a;
		try {
			a = new Appointment(aid);
		} catch(Exception e) {
			sendError(t, "Invalid appointment ID");
			return;
		}
		
		if(u.getId() != a.getCreator() && !u.isAdmin()) {
			sendUnauthorised(t);
			return;
		}
		
		if(Room.reserve(aid, rid, start, end)) {
			sendOK(t);
			return;
		} else {
			sendError(t, "Error reserving room");
			return;
		}	
	}
	
	private void cancel(HttpExchange t, User u, JSONObject request) throws IOException {		
		int aid;
		try {
			aid = request.getInt("aid");
		} catch(Exception e) {
			sendInvalidCommand(t);
			return;
		}
		
		Appointment a;
		try {
			a = new Appointment(aid);
		} catch(Exception e) {
			sendError(t, "Error getting appointment from DB");
			return;
		}
		
		if(a.getCreator() != u.getId() && !u.isAdmin()) {
			sendUnauthorised(t);
			return;
		}
			
		if(Room.cancel(aid)) {
			sendOK(t);
			return;
		} else {
			sendError(t, "Error cancelling room for appointment");
			return;
		}
	}
}

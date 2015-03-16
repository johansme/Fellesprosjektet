package server.httpserver;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

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
					case "get_available":
					case "get_all":
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
}

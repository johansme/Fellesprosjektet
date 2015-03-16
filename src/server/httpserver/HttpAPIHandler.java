package server.httpserver;

import java.io.IOException;
import java.io.OutputStream;

import org.json.JSONObject;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import shared.Util;

public abstract class HttpAPIHandler implements HttpHandler{
	protected Server server;
	
	HttpAPIHandler(Server server) {
		this.server = server;
	}
	
	static protected JSONObject get(HttpExchange t) throws IOException {
		return Util.streamToJSON(t.getRequestBody());
	}
	
	static protected void send(HttpExchange t, JSONObject response) throws IOException {
		String response_str = response.toString();
		t.sendResponseHeaders(200, response_str.length());
		OutputStream out = t.getResponseBody();
		out.write(response_str.getBytes());
		t.close();
	}
	
	static protected void sendUnauthorised(HttpExchange t) throws IOException {
		sendError(t, "Not authorised");
	}
	
	static protected void sendUnauthenticated(HttpExchange t) throws IOException {
		sendError(t, "Not authenticated");
	}
	
	static protected void sendInvalidCommand(HttpExchange t) throws IOException {
		sendError(t, "Invalid command");
	}
	
	static protected void sendNotImplemented(HttpExchange t) throws IOException {
		sendError(t, "Not implemented");
	}
	
	static protected void sendError(HttpExchange t, String error) throws IOException {
		send(t, new JSONObject().put("status", false).put("error", error));
	}
	
	static protected void sendOK(HttpExchange t) throws IOException {
		send(t, new JSONObject().put("status", "OK"));
	}
	
	static protected void sendOK(HttpExchange t, JSONObject result) throws IOException {
		send(t, result.put("status", true));
	}
}
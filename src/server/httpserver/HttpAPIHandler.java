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
	}
}

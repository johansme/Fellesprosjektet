package server.httpserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.json.JSONException;
import org.json.JSONObject;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public abstract class HttpAPIHandler implements HttpHandler{
	protected Server server;
	
	HttpAPIHandler(Server server) {
		this.server = server;
	}
	
	private static JSONObject streamToJSON(InputStream is) throws IOException {
		JSONObject obj;
		StringBuilder sb;

		int b;
		sb = new StringBuilder();
		while((b = is.read()) != -1) {
			sb.append((char)b);
		}
		is.close();
		
		try {
			obj = new JSONObject(sb.toString());
		} catch(JSONException e) {
			obj = null;
		}
		return obj;
	}

	
	static protected JSONObject get(HttpExchange t) throws IOException {
		return streamToJSON(t.getRequestBody());
	}
	
	static protected void send(HttpExchange t, JSONObject response) throws IOException {
		String response_str = response.toString();
		t.sendResponseHeaders(200, response_str.length());
		OutputStream out = t.getResponseBody();
		out.write(response_str.getBytes());
	}
}

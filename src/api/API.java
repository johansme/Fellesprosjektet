package api;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONException;
import org.json.JSONObject;

import shared.Util;

public class API {
	private final static String BASE_URL = "http://localhost:8000";
	
	public static String login(String username, String password) throws IOException {
		JSONObject obj = new JSONObject();
		obj.put("username", username);
		obj.put("password", password);
		
		JSONObject res = call("/login", obj, null);
		try {
			return res.getString("session");
		} catch(JSONException e) {
			return null;
		}
	}
	
	public static JSONObject call(String path, String session) throws IOException {
		URLConnection client = new URL(BASE_URL + path).openConnection();
		if(session != null) {
			client.addRequestProperty("X-FP-Session", session);
		}
		client.connect();
		return Util.streamToJSON(client.getInputStream());
	}
	
	public static JSONObject call(String path, JSONObject param, String session) throws IOException {
		URLConnection client = new URL(BASE_URL + path).openConnection();
		if(session != null) {
			client.addRequestProperty("X-FP-Session", session);
		}
		client.setDoOutput(true);
		client.getOutputStream().write(param.toString().getBytes());
		client.connect();
		return Util.streamToJSON(client.getInputStream());
	}
}

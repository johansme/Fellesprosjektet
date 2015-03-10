package api;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONObject;

import shared.Util;

public class API {
	public static String login(String username, String password) throws IOException {
		JSONObject obj = new JSONObject();
		obj.put("username", username);
		obj.put("password", password);
		URLConnection client = new URL("http://localhost:8000/login").openConnection();
		client.setDoOutput(true);
		OutputStream out = client.getOutputStream();
		out.write(obj.toString().getBytes());
		client.connect();
		JSONObject res = Util.streamToJSON(client.getInputStream());
		return res.getString("session");
	}
	
	public static JSONObject call(String path, String session) throws IOException {
		URLConnection client2 = new URL("http://localhost:8000" + path).openConnection();
		client2.addRequestProperty("X-FP-Session", session);
		client2.connect();
		return Util.streamToJSON(client2.getInputStream());
	}
}

package shared;

import java.io.IOException;
import java.io.InputStream;

import org.json.JSONException;
import org.json.JSONObject;

public class Util {
	public static JSONObject streamToJSON(InputStream is) throws IOException {
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
}

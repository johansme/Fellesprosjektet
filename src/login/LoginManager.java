package login;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import api.API;

public class LoginManager {
	
	private String session;
	private int userID;
	
	public boolean checkLogin(String username, String password){
		try {
			JSONObject res = new JSONObject();
			res = API.login(username, password);
			session = res.getString("session");
			userID = res.getInt("uid");
			if (session!=null) {
				return true;
			}
			return false;
		} catch (IOException e) {
			return false;
		} catch (JSONException j) {
			return false;
		} catch (NullPointerException n) {
			return false;
		}
	}

	public String getSession() {
		return session;
	}

	public int getID() {
		return userID;
	}

}

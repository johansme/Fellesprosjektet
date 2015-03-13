package login;

import java.io.IOException;

import org.json.JSONObject;

import api.API;

public class LoginManager {
	
	private SceneHandler sceneHandler = new SceneHandler();
	private String session;
	private int userID;
	
	public boolean checkLogin(String username, String password){
		try {
			JSONObject res = new JSONObject();
			res = API.login(username, password);
			session=
			if (session!=null) {
				return true;
			}
			return false;
		} catch (IOException e) {
			return false;
		}
	}

	public String getSession() {
		return session;
	}

}

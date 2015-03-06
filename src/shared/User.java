package shared;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
	private int id = 0;
	private String name = null;
	private String surname = null;
	private String username = null;
	private String email = null;
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getSurname() {
		return name;
	}

	public void setSurname(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("id", id);
			obj.put("name", name);
			obj.put("surname", surname);
			obj.put("username", username);
			obj.put("email", email);
		} catch(JSONException e) { return null; }
		return obj;
	}

	public User fromJSON(JSONObject obj) {
		User user = new User();
		try {
			user.id = obj.getInt("id");
			user.name = obj.getString("name");
			user.surname = obj.getString("surname");
			user.username = obj.getString("username");
			user.email = obj.getString("email");
		} catch(JSONException e) { return null; }
		return user;
	}
}
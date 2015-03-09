package shared;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
	protected int id = 0;
	protected String name = null;
	protected String surname = null;
	protected String username = null;
	protected String email = null;
	protected boolean admin = false;
	
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
	
	public boolean isAdmin() {
		return admin;
	}

	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("id", id);
			obj.put("name", name);
			obj.put("surname", surname);
			obj.put("username", username);
			obj.put("email", email);
			obj.put("admin", admin);
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
			user.admin = obj.getBoolean("admin");
		} catch(JSONException e) { return null; }
		return user;
	}
}

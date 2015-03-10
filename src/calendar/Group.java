package calendar;

import java.util.List;

public class Group implements Participant {
	
	private List<User> users;
	private User admin;
	private int id;
	private String name;
	
	public Group(int id, List<User> u, User a, String n) {
		this.id = id;
		setUsers(u);
		admin = a;
		name = n;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		for (User user: users) {
			if (!this.users.contains(user)) {
				this.users.add(user);
			}
		}
	}

	public User getAdmin() {
		return admin;
	}


	public int getId() {
		return id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void addUser(User user) {
		if (user != null && ! users.contains(user)) {
			users.add(user);
		}
	}
	
	public void removeUser(User user) {
		if (users.contains(user)) {
			users.remove(user);
		}
	}
	
	@Override
	public String toString() {
		return getName();
	}

}

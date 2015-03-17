package calendar;

import java.util.ArrayList;
import java.util.List;

public class User extends shared.User implements Participant {

	private List<Group> groups = new ArrayList<Group>();

	public void addGroup(Group g) {
		groups.add(g);
	}

	public void removeGroup(Group group) {
		groups.remove(group);
	}

	public void setGroups(List<Group> g) {
		groups.clear();
		for (Group gr : g) {
			groups.add(gr);
		}
	}

	public List<Group> getGroups() {
		if (groups!=null && !groups.isEmpty()) {
			return groups;
		}
		return null;
	}

	public User() {
		super();
	}

	public User(boolean admin) {
		super();
		super.admin = admin;
	}

	@Override
	public String toString() {
		return getSurname() + ", " + getName() + "; " + getEmail();
	}

}

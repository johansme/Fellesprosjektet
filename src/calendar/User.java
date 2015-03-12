package calendar;

public class User extends shared.User implements Participant {
	
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

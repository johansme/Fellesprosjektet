package calendar;

public class User extends shared.User {

	@Override
	public String toString() {
		return getSurname() + ", " + getName() + "; " + getEmail();
	}
	
}

package calendar;

public class User extends shared.User implements Participant {

	@Override
	public String toString() {
		return getSurname() + ", " + getName() + "; " + getEmail();
	}
	
}

package calendar;

public class User {

	private int userID;
	private String username;
	private String surname;
	private String firstName;
	private String email;
	
	public void setID(int ID){
		userID = ID;
	}
	public void setUsername(String usrnm){
		username = usrnm;
	}
	public void setSurname(String sur){
		surname = sur;
	}
	public void setFirstName(String frst){
		firstName = frst;
	}
	public void setEmail(String eml){
		email =eml;
	}
	
	public String getUsername(){
		return username;
	}
	public int getID(){
		return userID;
	}
	public String getEmail(){
		return email;
	}
	public String getSurname(){
		return surname;
	}
	public String getFirstName(){
		return firstName;
	}
}

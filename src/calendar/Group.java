package calendar;

import java.util.ArrayList;
import java.util.List;

public class Group extends shared.Group implements Participant {
	
	private List<Participant> members;
	private User admin;
	private boolean active;
	
	public Group(int id, List<Participant> memberList, User a, String n) {
		super();
		members = new ArrayList<Participant>();
		super.setId(id);
		setMembers(memberList);
		admin = a;
		super.setName(n);
	}

	public Group() {
		super();
	}

	public List<Participant> getMembers() {
		return members;
	}

	public void setMembers(List<Participant> memberList) {
		if(memberList != null){
		for (Participant participant: memberList) {
			if (participant != null && ! this.members.contains(participant)) {
				this.members.add(participant);
			}
		}
		}
	}

	public User getAdmin() {
		return admin;
	}

	public void addMember(Participant participant) {
		if (participant != null && ! members.contains(participant)) {
			members.add(participant);
		}
	}
	
	public void removeMember(Participant participant) {
		if (members.contains(participant)) {
			members.remove(participant);
		}
	}
	
	@Override
	public String toString() {
		return getName();
	}
	
	public void setActive(boolean b) {
		active = b;
	}
	
	public boolean getActive() {
		return active;
	}

}

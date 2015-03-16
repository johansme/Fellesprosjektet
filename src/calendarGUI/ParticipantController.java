package calendarGUI;

import java.util.List;

import calendar.Participant;

public interface ParticipantController {
	
	public void addParticipant(Participant participant);
	
	public List<Participant> getParticipants();

}

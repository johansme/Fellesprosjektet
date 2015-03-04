package messages;

import calendarGUI.ControllerInterface;

public interface MessageController {
	
	public void setMessage(String msg);
	public void setReferenceController(ControllerInterface controller);

}
